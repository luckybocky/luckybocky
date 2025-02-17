package com.project.luckybocky.sharearticle.sevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.article.repository.MyArticleRepository;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.fortune.exception.FortuneNotFoundException;
import com.project.luckybocky.fortune.repository.FortuneRepository;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.sharearticle.dto.ShareArticleDto;
import com.project.luckybocky.sharearticle.dto.WriteShareArticleDto;
import com.project.luckybocky.sharearticle.entity.ShareArticle;
import com.project.luckybocky.sharearticle.exception.ShareArticleException;
import com.project.luckybocky.sharearticle.repository.ShareArticleRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserSettingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ShareArticleServiceImpl implements ShareArticleService {
	private final UserSettingRepository userSettingRepository;
	private final FortuneRepository fortuneRepository;
	private final ShareArticleRepository shareArticleRepository;
	private final MyArticleRepository myArticleRepository;
	private final ArticleRepository articleRepository;
	private final PocketRepository pocketRepository;

	@Override
	public ShareArticleDto createShareArticle(String userKey, WriteShareArticleDto writeShareArticleDto) {
		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		Fortune fortune = fortuneRepository.findByFortuneSeq(writeShareArticleDto.getFortuneSeq())
			.orElseThrow(() -> new FortuneNotFoundException());

		User user = userOptional.get();

		UUID uuid = UUID.randomUUID();
		String address = uuid.toString();

		ShareArticle shareArticle = new ShareArticle(user, fortune, writeShareArticleDto.getContent(), address);

		//빌더로 하면 리스트 초기화가 작동하지않음
		// ShareArticle
		// .builder()
		// .user(user)
		// .fortune(fortune)
		// .shareArticleContent(writeShareArticleDto.getContent())
		// .shareArticleAddress(address)
		// .build();

		shareArticleRepository.save(shareArticle);

		log.info("{}({}) 공유게시글 생성", shareArticle.getUser().getUserKey(), shareArticle.getUser().getUserNickname());
		return shareArticle.toShareArticleDto();
	}

	@Override
	public ShareArticleDto enterShareArticle(String userKey, String shareArticleAddress) {
		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();

		Optional<ShareArticle> shareArticleOptional = shareArticleRepository.findByAddress(shareArticleAddress);
		if (shareArticleOptional.isEmpty())
			throw new ShareArticleException();

		ShareArticle shareArticle = shareArticleOptional.get();

		if (isMyShareArticle(userKey, shareArticle) || isExistsShareArticle(user, shareArticle)) {
			log.info("이미 저장되거나 본인의 공유게시글입니다.");
			return shareArticle.toShareArticleDto();
		}

		//저장시 공유게시글을 실제게시글로 변환하여 저장
		List<Pocket> pockets = user.getPockets();
		Collections.sort(pockets, (p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()));

		Pocket targetPocket = pockets.get(0);

		// Article article = new Article(user, user.getUserNickname(), shareArticle.getShareArticleContent(),
		// 		shareArticle.getFortune(), targetPocket, shareArticle);

		Article article = Article.builder()
			.user(shareArticle.getUser())
			.userNickname(shareArticle.getUser().getUserNickname())
			.articleContent(shareArticle.getShareArticleContent())
			.fortune(shareArticle.getFortune())
			.pocket(targetPocket)
			.shareArticle(shareArticle)
			.build();

		targetPocket.addArticle(article);

		articleRepository.save(article);
		pocketRepository.save(targetPocket);

		log.info("공유게시글 저장완료 : {}", userKey);

		return shareArticle.toShareArticleDto();
	}

	@Override
	public ShareArticleDto findShareArticle(String shareArticleAddress) {
		Optional<ShareArticle> shareArticleOptional = shareArticleRepository.findByAddress(shareArticleAddress);
		if (shareArticleOptional.isEmpty())
			throw new ShareArticleException();

		return shareArticleOptional.get().toShareArticleDto();
	}

	@Override
	public boolean isMyShareArticle(String userKey, ShareArticle shareArticle) {
		return userKey.equals(shareArticle.getUser().getUserKey());
	}

	@Override
	public boolean isExistsShareArticle(User user, ShareArticle shareArticle) {
		int shareArticleSeq = shareArticle.getShareArticleSeq();

		Optional<Pocket> pocket = pocketRepository.findPocketByUser(user);

		if (pocket.isEmpty())
			throw new PocketNotFoundException();

		List<Article> articles = pocket.get().getArticles();

		for (Article article : articles) {
			ShareArticle findShareArticle = article.getShareArticle();

			if (findShareArticle == null)
				continue;
			if (findShareArticle.getShareArticleSeq() == shareArticleSeq) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ShareArticleDto> getMyShareArticle(String userKey) {
		List<ShareArticleDto> result = new ArrayList<>();

		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();

		List<ShareArticle> shareArticles = user.getShareArticles();

		for (ShareArticle shareArticle : shareArticles) {
			result.add(shareArticle.toShareArticleDto());
		}

		return result;
	}
}

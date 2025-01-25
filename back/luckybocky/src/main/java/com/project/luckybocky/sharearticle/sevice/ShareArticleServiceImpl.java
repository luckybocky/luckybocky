package com.project.luckybocky.sharearticle.sevice;

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

		// ShareArticle shareArticle = ShareArticle
		// 	.builder()
		// 	.user(user)
		// 	.fortune(fortune)
		// 	.shareArticleContent(writeShareArticleDto.getContent())
		// 	.shareArticleAddress(address)
		// 	.build();

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

		log.info("{} 공유게시글 생성", shareArticle.getUser().getUserNickname());

		return shareArticle.toShareArticleDto();
	}

	@Override
	public void enterShareArticle(String userKey, int shareArticleSeq) {
		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		User user = userOptional.get();

		if(isMyShareArticle(userKey, shareArticleSeq) || isExistsShareArticle(userKey, shareArticleSeq)) return;

		//저장시 공유게시글을 실제게시글로 변환하여 저장
		List<Pocket> pockets = user.getPockets();
		Collections.sort(pockets, (p1,p2)->p1.getCreatedAt().compareTo(p2.getCreatedAt()));
		ShareArticle shareArticle = shareArticleRepository.findById(shareArticleSeq);


		Article article= new Article(user,user.getUserNickname(), shareArticle.getShareArticleContent(), shareArticle.getFortune(),pockets.get(0), shareArticle);
		articleRepository.save(article);

	}

	@Override
	public boolean isMyShareArticle(String userKey, int shareArticleSeq) {
		ShareArticle shareArticle = shareArticleRepository.findById(shareArticleSeq);
		if (shareArticle == null)
			throw new ShareArticleException();
		return userKey.equals(shareArticle.getUser().getUserKey());
	}

	@Override
	public boolean isExistsShareArticle(String userKey, int shareArticleSeq) {

		Optional<User> userOptional = userSettingRepository.findByKey(userKey);

		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}
		List<Article> articleByUserKey = myArticleRepository.findArticleByUserKey(userKey);

		for (Article article : articleByUserKey) {
			ShareArticle shareArticle = article.getShareArticle();
			if (shareArticle == null)
				continue;
			if (shareArticle.getShareArticleSeq() == shareArticleSeq)
				return true;
		}
		return false;
	}
}

package com.project.luckybocky.article.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.article.dto.ArticleResponseDto;
import com.project.luckybocky.article.dto.ArticleSummaryDto;
import com.project.luckybocky.article.dto.WriteArticleDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.fortune.entity.Fortune;
import com.project.luckybocky.fortune.exception.FortuneNotFoundException;
import com.project.luckybocky.fortune.repository.FortuneRepository;
import com.project.luckybocky.pocket.entity.Pocket;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.pocket.repository.PocketRepository;
import com.project.luckybocky.push.dto.PushDto;
import com.project.luckybocky.push.enums.PushMessage;
import com.project.luckybocky.push.service.PushService;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.ForbiddenUserException;
import com.project.luckybocky.user.repository.UserRepository;
import com.project.luckybocky.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
	private final PushService pushService;
	private final UserService userService;

	private final PocketRepository pocketRepository;
	private final UserRepository userRepository;
	private final FortuneRepository fortuneRepository;
	private final ArticleRepository articleRepository;

	public ArticleResponseDto getArticleDetails(String userKey, int articleSeq) {
		Article article = articleRepository.findByArticleSeq(articleSeq)
			.orElseThrow(() -> new ArticleNotFoundException());

		ArticleResponseDto response;
		if (article.getShareArticle() == null) {
			response = article.toArticleResponseDto();
		} else {
			response = article.shareArticleToArticleResponseDto(article.getShareArticle());
		}

		// 게시글 공개 여부 설정
		// 복주머니 주인이 아니고, 비공개 복주머니일 경우 -> 게시글 전체 비공개 설정
		// if (useSeq == null || userSeq != article.getPocket().getUser().getUserSeq()){
		if (userKey == null || !userKey.equals(article.getPocket().getUser().getUserKey())) {
			if (!article.getPocket().getUser().isFortuneVisibility()) {
				response.setArticleVisibility(false);
				response.setArticleContent("비밀글입니다.");
			}
		}
		// 복주머니 주인 -> 게시글 전체 공개 설정
		else {
			response.setArticleVisibility(true);
		}

		return response;
	}

	public void createArticle(String userKey, WriteArticleDto writeArticleDto) {
		Optional<User> user = userRepository.findByUserKey(userKey);
		// User user = userRepository.findByUserSeq(userSeq).orElseThrow(() -> new UserNotFoundException());

		Fortune fortune = fortuneRepository.findByFortuneSeq(writeArticleDto.getFortuneSeq())
			.orElseThrow(() -> new FortuneNotFoundException());

		Pocket pocket = pocketRepository.findPocketByPocketSeq(writeArticleDto.getPocketSeq())
			.orElseThrow(() -> new PocketNotFoundException());

		Article article = Article.builder()
			.user(user.orElse(null))                    // ** 기능 변경되면서 비회원 게시글 작성 못하게 됨 -> user 가져올 때 예외처리
			.userNickname(writeArticleDto.getNickname())
			.articleContent(writeArticleDto.getContent())
			.fortune(fortune)
			.pocket(pocket)
			.build();

		articleRepository.save(article);

		log.info("유저 {}, 게시글 {}", user.orElse(null).getUserSeq(), article.getArticleSeq());

		//push 알림추가(이후 비동기로 진행하면 더 좋을 듯)
		PushMessage pushMessage = PushMessage.ARTICLE;

		PushDto pushDto = PushDto.builder()
			.toUser(pocket.getUser())
			.address(pocket.getPocketAddress())
			.title(pushMessage.getTitle())
			.content(writeArticleDto.getNickname() + pushMessage.getBody())
			.build();

		pushService.sendPush(pushDto);
	}

	public void deleteArticle(String userKey, int articleSeq) {
		// 현재 로그인한 사용자가 해당 게시글의 주인(복을 받은 사용자)이 아닐 경우
		int userSeq = userService.getUserSeq(userKey);
		if (getOwnerByArticle(articleSeq) != userSeq) {
			throw new ForbiddenUserException();
		}

		Article findArticle = articleRepository.findByArticleSeq(articleSeq)
			.orElseThrow(() -> new ArticleNotFoundException());

		log.info("유저 {}, 게시글 {}", userSeq, articleSeq);
		articleRepository.delete(findArticle);
	}

	public int getOwnerByArticle(int articleSeq) {
		Article findArticle = articleRepository.findByArticleSeq(articleSeq)
			.orElseThrow(() -> new ArticleNotFoundException());
		Pocket pocket = findArticle.getPocket();
		return pocket.getUser().getUserSeq();
	}

	public List<ArticleSummaryDto> getAllArticles(int pocketSeq) {
		Pocket pocket = pocketRepository.findPocketByPocketSeq(pocketSeq)
			.orElseThrow(() -> new PocketNotFoundException());

		return pocket.getArticles().stream()
			.map(Article::summaryArticle)
			.collect(Collectors.toList());
	}

}

package com.project.luckybocky.article.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArticlePushServiceImpl implements ArticlePushService {
	private final ArticleRepository articleRepository;

	@Override
	public User findArticleOwner(int articleSeq) {
		Article article = articleRepository.findByArticleSeq(articleSeq).orElseThrow(
			() -> {
				log.info("{}의 복을 찾을 수 없습니다.", articleSeq);
				return new ArticleNotFoundException();
			}
		);

		return Optional.ofNullable(article.getUser())
			.orElseThrow(()
				-> {
				log.info("{}의 복 주인을 찾을 수 없습니다.", articleSeq);
				return new UserNotFoundException();
			});
	}
}

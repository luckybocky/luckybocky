package com.project.luckybocky.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.repository.MyArticleRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArticlePushServiceImpl implements ArticlePushService {
	private final MyArticleRepository myArticleRepository;

	@Override
	public User findArticleOwner(int articleSeq) {
		Article article = myArticleRepository.findByArticleSeq(articleSeq);

		if (article == null)
			throw new ArticleNotFoundException();

		if(article.getUser() == null)
			throw new UserNotFoundException();

		return article.getUser();
	}
}

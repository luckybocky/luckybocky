package com.project.luckybocky.article.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.luckybocky.article.dto.MyArticleDto;
import com.project.luckybocky.article.dto.MyArticlesDto;
import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyArticleServiceImpl implements MyArticleService {
	private final ArticleRepository articleRepository;

	@Override
	public MyArticlesDto findMyArticles(String userKey) {

		if (userKey == null) {
			throw new UserNotFoundException();
		}

		List<Article> articleByUserKey = articleRepository.findByUserUserKey(userKey);
		List<MyArticleDto> myArticles = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (Article article : articleByUserKey) {
			if (article.getShareArticle() == null) {
				myArticles.add(
					article.getMyArticleDto()
				);
			}
		}

		return new MyArticlesDto(myArticles);
	}
}

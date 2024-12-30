package com.project.luckybocky.article.repository;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.pocket.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Optional<Article> findByArticleSeq(int articleSeq);

    List<Article> findAllByPocket(Pocket pocket);
}

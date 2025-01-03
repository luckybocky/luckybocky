package com.project.luckybocky.article.repository;

import com.project.luckybocky.article.entity.Article;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyArticleRepositoryImpl implements MyArticleRepository{
    private  final EntityManager em;

    @Override
    public List<Article> findArticleByUserKey(String userKey){
        return em.createQuery("select a from Article a where a.user.userKey=:userKey", Article.class)
                .setParameter("userKey", userKey)
                .getResultList();
    }

    @Override
    public Article findByArticleSeq(int articleSeq) {
        return em.find(Article.class, articleSeq);
    }
}

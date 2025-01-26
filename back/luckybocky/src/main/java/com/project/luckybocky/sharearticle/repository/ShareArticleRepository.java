package com.project.luckybocky.sharearticle.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.project.luckybocky.sharearticle.entity.ShareArticle;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShareArticleRepository {
	private final EntityManager em;

	public ShareArticle save(ShareArticle shareArticle) {
		em.persist(shareArticle);
		return shareArticle;
	}

	public ShareArticle findById(int shareArticleSeq) {
		return em.find(ShareArticle.class, shareArticleSeq);
	}

	public Optional<ShareArticle> findByAddress(String shareArticleAddress) {
		return em.createQuery(
				"select s from ShareArticle s where s.shareArticleAddress=:shareArticleAddress", ShareArticle.class)
			.setParameter("shareArticleAddress", shareArticleAddress)
			.getResultList().stream().findAny();
	}
}

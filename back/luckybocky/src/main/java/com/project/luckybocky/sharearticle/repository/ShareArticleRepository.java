package com.project.luckybocky.sharearticle.repository;

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

	// public Optional<User> findByKey(String userKey) {
	// 	return em.createQuery("select u from User u where u.userKey=:userKey", User.class)
	// 		.setParameter("userKey", userKey)
	// 		.getResultList().stream().findAny();
	// }
}

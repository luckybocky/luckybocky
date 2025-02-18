package com.project.luckybocky.sharearticle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.luckybocky.sharearticle.entity.ShareArticle;

public interface ShareArticleRepository extends JpaRepository<ShareArticle, Integer> {
	Optional<ShareArticle> findByShareArticleAddress(String shareArticleAddress);
}

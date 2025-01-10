package com.project.luckybocky.feedback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.luckybocky.feedback.dto.FeedbackDto;
import com.project.luckybocky.feedback.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}

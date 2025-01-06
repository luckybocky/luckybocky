package com.project.luckybocky.feedback.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.luckybocky.common.NicknameNotFoundException;
import com.project.luckybocky.feedback.dto.FeedbackDto;
import com.project.luckybocky.feedback.dto.FeedbackReqDto;
import com.project.luckybocky.feedback.dto.FeedbackResDto;
import com.project.luckybocky.feedback.entity.Feedback;
import com.project.luckybocky.feedback.exception.FeedbackNotFoundException;
import com.project.luckybocky.common.SessionNotFoundException;
import com.project.luckybocky.feedback.repository.FeedbackRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
	private final FeedbackRepository feedbackRepository;
	private final UserRepository userRepository;

	public void saveFeedback(FeedbackReqDto feedbackReqDto, HttpSession session) {
		if(session == null) {
			throw new SessionNotFoundException("User session not found, unable to save feedback");
		} else if(session.getAttribute("nickname") == null) {
			throw new NicknameNotFoundException("User nickname is null, unable to save feedback");
		}
		String userKey = (String) session.getAttribute("user");

		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(() -> new UserNotFoundException("User not found with key"));

		feedbackRepository.save(
			Feedback.builder()
				.user(user)
				.feedbackContent(feedbackReqDto.getFeedbackContent())
				.feedbackRate(feedbackReqDto.getFeedbackRate())
				.build()
		);
	}

	public FeedbackResDto getFeedback() {
		List<Feedback> feedbacks = feedbackRepository.findAll();
		if(feedbacks.isEmpty()) {
			throw new FeedbackNotFoundException("Feedback is nothing");
		}

		List<FeedbackDto> feedbackDtoList = feedbacks.stream()
			.map(feedback -> FeedbackDto.builder()
				.feedbackSeq(feedback.getFeedbackSeq())
				.userSeq(feedback.getUser().getUserSeq())
				.feedbackContent(feedback.getFeedbackContent())
				.feedbackRate(feedback.getFeedbackRate())
				.build())
			.toList();

		return FeedbackResDto.builder()
			.feedbacks(feedbackDtoList)
			.build();
	}
}

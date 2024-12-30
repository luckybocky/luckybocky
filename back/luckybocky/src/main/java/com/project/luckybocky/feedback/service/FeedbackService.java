package com.project.luckybocky.feedback.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.luckybocky.common.BaseEntity;
import com.project.luckybocky.feedback.dto.FeedbackDto;
import com.project.luckybocky.feedback.dto.FeedbackReqDto;
import com.project.luckybocky.feedback.dto.FeedbackResDto;
import com.project.luckybocky.feedback.entity.Feedback;
import com.project.luckybocky.feedback.repository.FeedbackRepository;
import com.project.luckybocky.user.entity.User;
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

	public FeedbackDto saveFeedback(FeedbackReqDto feedbackReqDto, HttpSession session) {
		Object userKey = session.getAttribute("user");
		// TODO 추후 예외처리 확실하게 수정할 것
		log.debug("userKey: {}", userKey);
		log.debug("feedbackDto: {}", feedbackReqDto);

		if(userKey != null) {
			Optional<User> user = userRepository.findByUserKey(userKey.toString());

			if(user.isPresent()) {
				feedbackRepository.save(
					Feedback.builder()
						.user(user.get())
						.feedbackContent(feedbackReqDto.getFeedbackContent())
						.feedbackRate(feedbackReqDto.getFeedbackRate())
						.build()
				);

				LocalDateTime current_time = LocalDateTime.now();  // 논의 후, 제거 -> 이유: db 저장 시간이랑 차이 날까봐
				return FeedbackDto.builder()
					.userSeq(user.get().getUserSeq())
					.feedbackContent(feedbackReqDto.getFeedbackContent())
					.feedbackRate(feedbackReqDto.getFeedbackRate())
					.createdAt(current_time)
					.modifiedAt(current_time)
					.isDeleted(false)
					.build();
			}
		}

		return null;
	}

	public FeedbackResDto getFeedback() {
		List<Feedback> feedbacks = feedbackRepository.findAll();

		if(!feedbacks.isEmpty()) {
			List<FeedbackDto> feedbackDtoList = feedbacks.stream()
				.map(feedback -> FeedbackDto.builder()
					.feedbackSeq(feedback.getFeedbackSeq())
					.userSeq(feedback.getUser().getUserSeq())
					.feedbackContent(feedback.getFeedbackContent())
					.feedbackRate(feedback.getFeedbackRate())
					.createdAt(feedback.getCreatedAt())
					.modifiedAt(feedback.getModifiedAt())
					.isDeleted(feedback.isDeleted())
					.build())
				.toList();

			return FeedbackResDto.builder()
				.feedbacks(feedbackDtoList)
				.build();
		}

		return null;
	}
}

package com.project.luckybocky.qna.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.luckybocky.qna.dto.QnaDto;
import com.project.luckybocky.qna.dto.QnaListResDto;
import com.project.luckybocky.qna.dto.QnaUserReqDto;
import com.project.luckybocky.qna.entity.Qna;
import com.project.luckybocky.qna.exception.QnaNotFoundException;
import com.project.luckybocky.qna.exception.QnaSaveException;
import com.project.luckybocky.qna.repository.QnaRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaService {
	private final UserRepository userRepository;
	private final QnaRepository qnaRepository;

	@Transactional
	public void saveQuestion(QnaUserReqDto qnaUserReqDto, HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		try {
			qnaRepository.save(Qna.makeQuestion(user, qnaUserReqDto));
			log.info("{} 시용자가 질문을 등록했습니다.", user.getUserKey());
		} catch (Exception e) {
			throw new QnaSaveException(e.getMessage());
		}
	}

	@Transactional
	public void saveAnswer(QnaDto qnaDto) {
		Qna qna = qnaRepository.findById(qnaDto.getQnaSeq()).orElseThrow(QnaNotFoundException::new);

		try {
			qna.setAnswer(qnaDto.getAnswer());
			log.info("{}번 질문에 대한 답변 등록에 성공했습니다.", qnaDto.getQnaSeq());
		} catch (Exception e) {
			log.error("{}번 질문에 대한 답변 등록에 실패했습니다.", qnaDto.getQnaSeq());
			throw new QnaSaveException(e.getMessage());
		}
	}

	public QnaListResDto getQuestions(Pageable pageable) {
		Page<Qna> qnaList = qnaRepository.findAllByisDeletedIsFalse(pageable);
		Page<QnaDto> qnaDtoList = QnaDto.toQnaPageDto(qnaList);

		log.info("질문 목록 로딩 성공");
		return QnaListResDto.toQnaResDto(qnaDtoList);
	}

	public Integer checkAccess(Integer qnaSeq, HttpSession session) {
		final int user = 200;  // 일반 사용자
		final int authorizedUser = 300;  // 접근 권한이 있는 사용자
		final int admin = 400;  //  관리자

		Qna qna = qnaRepository.findById(qnaSeq)
			.orElseThrow(QnaNotFoundException::new);

		String userKey = (String)session.getAttribute("user");

		User currentUser = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		if (currentUser.getRole() == 1) {
			log.info("<관리자>에 대한 접근 요청을 반환합니다.");
			return admin;
		} else if (qna.getSecretStatus()) {
			if (qna.getUser().getUserKey().equals(currentUser.getUserKey())) {
				log.info("<게시글 작성자>에 대한 접근 요청을 반환합니다.");
				return authorizedUser;
			}
		}

		log.info("<일반 사용자>에 대한 접근 요청을 반환합니다.");
		return user;
	}

	public QnaListResDto getMyQuestions(Pageable pageable, HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		Page<Qna> qnaList = qnaRepository.findAllByisDeletedIsFalseAndUser(pageable, user);

		Page<QnaDto> qnaDtoList = QnaDto.toQnaPageDto(qnaList);

		log.info("{} 시용자가 질문 목록을 조회했습니다.", user.getUserKey());
		return QnaListResDto.toQnaResDto(qnaDtoList);
	}
}

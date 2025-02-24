package com.project.luckybocky.qna.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.luckybocky.qna.dto.QnaDto;
import com.project.luckybocky.qna.dto.QnaListResDto;
import com.project.luckybocky.qna.dto.QnaUserReqDto;
import com.project.luckybocky.qna.entity.Qna;
import com.project.luckybocky.qna.exception.QnaDeleteException;
import com.project.luckybocky.qna.exception.QnaNotFoundException;
import com.project.luckybocky.qna.exception.QnaSaveException;
import com.project.luckybocky.qna.exception.QnaUpdateException;
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
@Transactional
public class QnaService {
	private final UserRepository userRepository;
	private final QnaRepository qnaRepository;
	private final int generalUser = 200;  // 일반 사용자
	private final int authorizedUser = 300;  // 접근 권한이 있는 사용자
	private final int admin = 400;  //  관리자

	public void saveQuestion(QnaUserReqDto qnaUserReqDto, HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		try {
			qnaRepository.save(Qna.makeQuestion(user, qnaUserReqDto));
			log.info("{} 시용자가 질문을 등록했습니다.", user.getUserKey() == null ? "익명" : user.getUserKey());
		} catch (Exception e) {
			throw new QnaSaveException(e.getMessage());
		}
	}

	// public void saveAnswer(QnaDto qnaDto) {
	// 	Qna qna = qnaRepository.findById(qnaDto.getQnaSeq()).orElseThrow(QnaNotFoundException::new);
	//
	// 	try {
	// 		qna.setAnswer(qnaDto.getAnswer());
	// 		log.info("{}번 질문에 대한 답변 등록에 성공했습니다.", qnaDto.getQnaSeq());
	// 	} catch (Exception e) {
	// 		log.error("{}번 질문에 대한 답변 등록에 실패했습니다.", qnaDto.getQnaSeq());
	// 		throw new QnaSaveException(e.getMessage());
	// 	}
	// }

	public QnaListResDto getQuestions(Pageable pageable, HttpSession session) {
		Page<Qna> qnaList = qnaRepository.findAllByisDeletedIsFalse(pageable);
		String userKey = (String)session.getAttribute("user");

		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		Page<QnaDto> qnaDtoList = QnaDto.toQnaPageDto(qnaList, user.getRole() == 1, user.getUserKey());

		log.info("질문 목록 로딩 성공");
		return QnaListResDto.toQnaResDto(qnaDtoList);
	}

	public Integer getQuestion(Integer qnaSeq, HttpSession session) {
		Qna qna = qnaRepository.findById(qnaSeq)
			.orElseThrow(QnaNotFoundException::new);

		String userKey = (String)session.getAttribute("user");

		User currentUser = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		if (currentUser.getRole() == 1) {
			log.info("{}번 질문 - <관리자>에 대한 접근 요청을 반환합니다.", qnaSeq);
			return admin;
		}

		if (qna.getUser().getUserKey() != null) {
			if (qna.getUser().getUserKey().equals(currentUser.getUserKey())) {
				log.info("{}번 질문 - <게시글 작성자>에 대한 접근 요청을 반환합니다.", qnaSeq);
				return authorizedUser;
			}
		}

		log.info("{}번 질문 - <일반 사용자>에 대한 접근 요청을 반환합니다.", qnaSeq);
		return generalUser;
	}

	public void deleteQna(Integer qnaSeq) {
		Qna qna = qnaRepository.findById(qnaSeq)
			.orElseThrow(QnaNotFoundException::new);

		try {
			qna.setDeleted(true);
			log.info("{}번 질문 삭제 성공", qnaSeq);
		} catch (Exception e) {
			log.info("{}번 질문 삭제 실패", qnaSeq);
			throw new QnaDeleteException(e.getMessage());
		}
	}

	public void updateQna(Integer qnaSeq, QnaUserReqDto qnaUserReqDto) {
		Qna qna = qnaRepository.findById(qnaSeq)
			.orElseThrow(QnaNotFoundException::new);

		try {
			qna.setTitle(qnaUserReqDto.getTitle());
			qna.setContent(qnaUserReqDto.getContent());
			qna.setSecretStatus(qnaUserReqDto.getSecretStatus());
			log.info("{}번 질문 수정 성공", qnaSeq);
		} catch (Exception e) {
			log.error("{}번 질문 수정 실패", qnaSeq);
			throw new QnaUpdateException(e.getMessage());
		}
	}

	public QnaListResDto getMyQuestions(Pageable pageable, HttpSession session) {
		String userKey = (String)session.getAttribute("user");

		User user = userRepository.findByUserKey(userKey)
			.orElseThrow(UserNotFoundException::new);

		Page<Qna> qnaList = qnaRepository.findAllByisDeletedIsFalseAndUser(pageable, user);

		Page<QnaDto> qnaDtoList = QnaDto.toQnaPageDto(qnaList, false, user.getUserKey());

		log.info("{} 시용자가 질문 목록을 조회했습니다.", user.getUserKey());
		return QnaListResDto.toQnaResDto(qnaDtoList);
	}
}

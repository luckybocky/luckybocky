package com.project.luckybocky.admin.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.luckybocky.admin.dto.ArticleAdminListResDto;
import com.project.luckybocky.admin.dto.FeedbackAdminListResDto;
import com.project.luckybocky.admin.dto.QnaAdminListResDto;
import com.project.luckybocky.admin.dto.QnaAdminReqDto;
import com.project.luckybocky.admin.dto.ReportAdminListResDto;
import com.project.luckybocky.admin.dto.UserAdminListResDto;
import com.project.luckybocky.admin.respository.ArticleAdminRepository;
import com.project.luckybocky.admin.respository.FeedbackAdminRepository;
import com.project.luckybocky.admin.respository.QnaAdminRepository;
import com.project.luckybocky.admin.respository.ReportAdminRepository;
import com.project.luckybocky.admin.respository.UserAdminRepository;
import com.project.luckybocky.qna.entity.Qna;
import com.project.luckybocky.qna.exception.QnaNotFoundException;
import com.project.luckybocky.qna.exception.QnaSaveException;
import com.project.luckybocky.qna.repository.QnaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {
	private final UserAdminRepository userAdminRepository;
	private final ArticleAdminRepository articleAdminRepository;
	private final FeedbackAdminRepository feedbackAdminRepository;
	private final ReportAdminRepository reportAdminRepository;
	private final QnaAdminRepository qnaAdminRepository;
	private final QnaRepository qnaRepository;

	public UserAdminListResDto getFilteredUsers(String startDate, String endDate, Boolean userNickname,
		Pageable pageable) {
		log.info("유저 목록 조회 성공");
		return userAdminRepository.findUsersWithFilters(startDate, endDate, userNickname, pageable);
	}

	public FeedbackAdminListResDto getFilteredFeedbacks(String startDate, String endDate, Pageable pageable) {
		log.info("피드백 목록 조회 성공");
		return feedbackAdminRepository.findFeedbacksWithFilters(startDate, endDate, pageable);
	}

	public ReportAdminListResDto getFilteredReports(String startDate, String endDate, Pageable pageable) {
		log.info("신고 목록 조회 성공");
		return reportAdminRepository.findReportsWithFilters(startDate, endDate, pageable);
	}

	public QnaAdminListResDto getFilteredQnas(String startDate, String endDate, Boolean deleted, Boolean answer,
		Pageable pageable) {
		log.info("문의 목록 조회 성공");
		return qnaAdminRepository.findQnaWithFilters(startDate, endDate, deleted, answer, pageable);
	}

	public void putAnsweredQna(Integer qnaSeq, QnaAdminReqDto answerDto) {
		Qna qna = qnaRepository.findById(qnaSeq).orElseThrow(QnaNotFoundException::new);

		try {
			qna.setAnswer(answerDto.getAnswer());
			log.info("{}번 문의 답변 등록 성공", qnaSeq);
		} catch (Exception e) {
			log.error("{}번 문의 답변 등록 실패", qnaSeq);
			throw new QnaSaveException(e.getMessage());
		}
	}

	public ArticleAdminListResDto getFilteredArticles(String startDate, String endDate, Boolean deleted,
		Pageable pageable) {
		log.info("게시글 목록 조회 성공");
		return articleAdminRepository.findArticlesWithFilters(startDate, endDate, deleted, pageable);
	}
}

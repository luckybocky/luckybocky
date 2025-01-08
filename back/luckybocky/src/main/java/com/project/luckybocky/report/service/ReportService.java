package com.project.luckybocky.report.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.common.SessionNotFoundException;
import com.project.luckybocky.report.dto.ReportDto;
import com.project.luckybocky.report.dto.ReportReqDto;
import com.project.luckybocky.report.dto.ReportResDto;
import com.project.luckybocky.report.entity.Report;
import com.project.luckybocky.report.exception.ReportNotFoundException;
import com.project.luckybocky.report.exception.ReportSaveException;
import com.project.luckybocky.report.repository.ReportRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.exception.UserNotFoundException;
import com.project.luckybocky.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;
	private final ReportRepository reportRepository;

	public void saveReport(ReportReqDto reportReqDto, HttpSession session) {
		if(session == null) {
			throw new SessionNotFoundException("User session not found, unable to save feedback");
		}
		String userKey = (String) session.getAttribute("user");

		Article article = articleRepository.findByArticleSeq(reportReqDto.getArticleSeq())
			.orElseThrow(() -> new ArticleNotFoundException(reportReqDto.getArticleSeq() + " Article not found"));

		User reporter = userRepository.findByUserKey(userKey)
			.orElseThrow(() -> new UserNotFoundException("Reporter not found with key"));  // 유효 신고자인지 확인

		User offender = null;
		if(article.getUser() != null) {
			offender = userRepository.findByUserSeq(article.getUser().getUserSeq())
				.orElseThrow(() -> new UserNotFoundException("Offender not found userSeq"));
		}

		try {
			reportRepository.save(
				Report.builder()
					.article(article)
					.reporter(reporter)
					.offender(offender)
					.reportType(reportReqDto.getReportType())
					.reportContent(reportReqDto.getReportContent())
					.build());
			log.info("Report save success");
		} catch (Exception e) {
			throw new ReportSaveException(e.getMessage());
		}
	}

	public ReportResDto getReport() {
		List<Report> reports = reportRepository.findAll();
		if(reports.isEmpty()) {
			throw new ReportNotFoundException("Report is nothing");
		}

		List<ReportDto> reportDtoList = reports.stream()
			.map(report -> ReportDto.builder()
				.reportSeq(report.getReportSeq())
				.articleSeq(report.getArticle().getArticleSeq())
				.reporterSeq(report.getReporter().getUserSeq())
				.offenderSeq(report.getOffender().getUserSeq())
				.reportType(report.getReportType())
				.reportContent(report.getReportContent())
				.createdAt(report.getCreatedAt())
				.modifiedAt(report.getModifiedAt())
				.isDeleted(report.isDeleted())
				.build())
			.toList();

		ReportResDto reportResDto = ReportResDto.builder()
			.reports(reportDtoList)
			.build();

		log.info("Report inquiry success");
		return reportResDto;
	}
}

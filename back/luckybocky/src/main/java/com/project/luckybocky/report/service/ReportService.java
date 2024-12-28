package com.project.luckybocky.report.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.luckybocky.article.entity.Article;
import com.project.luckybocky.article.repository.ArticleRepository;
import com.project.luckybocky.report.dto.ReportDto;
import com.project.luckybocky.report.dto.ReportReqDto;
import com.project.luckybocky.report.dto.ReportResDto;
import com.project.luckybocky.report.entity.Report;
import com.project.luckybocky.report.repository.ReportRepository;
import com.project.luckybocky.user.entity.User;
import com.project.luckybocky.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;
	private final ReportRepository reportRepository;

	public ReportDto saveReport(ReportReqDto reportReqDto, HttpSession session) {
		Object userKey = session.getAttribute("user");

		if(userKey != null) {
			Optional<User> user = userRepository.findByUserKey(userKey.toString());
			Optional<Article> article = articleRepository.findByArticleSeq(reportReqDto.getArticleSeq());

			if (user.isPresent() && article.isPresent()) {
				reportRepository.save(
					Report.builder()
						.article(article.get())
						.user(user.get())
						.reportType(reportReqDto.getReportType())
						.reportContent(reportReqDto.getReportContent())
						.build()
				);

				LocalDateTime current_time = LocalDateTime.now();  // 논의 후, 제거 -> 이유: db 저장 시간이랑 차이 날까봐
				return ReportDto.builder()
					.articleSeq(article.get().getArticleSeq())
					.userSeq(user.get().getUserSeq())
					.reportType(reportReqDto.getReportType())
					.reportContent(reportReqDto.getReportContent())
					.createdAt(current_time)
					.modifiedAt(current_time)
					.isDeleted(false)
					.build();
			}
		}

		return null;
	}

	public ReportResDto getReport() {
		List<Report> reports = reportRepository.findAll();

		if(!reports.isEmpty()) {
			List<ReportDto> reportDtoList = reports.stream()
				.map(report -> ReportDto.builder()
					.reportSeq(report.getReportSeq())
					.articleSeq(report.getArticle().getArticleSeq())
					.reportType(report.getReportType())
					.reportContent(report.getReportContent())
					.createdAt(report.getCreatedAt())
					.modifiedAt(report.getModifiedAt())
					.isDeleted(report.isDeleted())
					.build())
				.toList();

			return ReportResDto.builder()
				.reports(reportDtoList)
				.build();
		}

		return null;
	}
}

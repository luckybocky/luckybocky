package com.project.luckybocky.admin.respository;

import org.springframework.data.domain.Pageable;

import com.project.luckybocky.admin.dto.FeedbackAdminListResDto;

public interface FeedbackAdminRepository {
	FeedbackAdminListResDto findFeedbacksWithFilters(String startDate, String endDate, Pageable pageable);

	double findFeedbackAverageRate();
	//TODO 추후 캐싱 전략에 대해 고민해 볼 것
}

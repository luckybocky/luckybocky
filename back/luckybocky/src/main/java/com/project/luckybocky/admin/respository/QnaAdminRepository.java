package com.project.luckybocky.admin.respository;

import org.springframework.data.domain.Pageable;

import com.project.luckybocky.admin.dto.QnaAdminListResDto;

public interface QnaAdminRepository {
	QnaAdminListResDto findQnaWithFilters(String startDate, String endDate, Boolean deleted, Boolean answer,
		Pageable pageable);
}

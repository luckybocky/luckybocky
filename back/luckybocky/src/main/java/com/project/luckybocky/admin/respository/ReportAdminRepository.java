package com.project.luckybocky.admin.respository;

import org.springframework.data.domain.Pageable;

import com.project.luckybocky.admin.dto.ReportAdminListResDto;

public interface ReportAdminRepository {
	ReportAdminListResDto findReportsWithFilters(String startDate, String endDate, Pageable pageable);
}

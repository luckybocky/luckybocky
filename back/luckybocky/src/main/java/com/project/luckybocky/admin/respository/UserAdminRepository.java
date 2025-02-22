package com.project.luckybocky.admin.respository;

import org.springframework.data.domain.Pageable;

import com.project.luckybocky.admin.dto.UserAdminListResDto;

public interface UserAdminRepository {
	UserAdminListResDto findUsersWithFilters(String startDate, String endDate, Boolean userNickname,
		Pageable pageable);
}

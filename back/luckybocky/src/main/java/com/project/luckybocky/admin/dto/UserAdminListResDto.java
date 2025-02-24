package com.project.luckybocky.admin.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAdminListResDto {
	private Page<UserAdminDto> userBackOfficeResDto;

	public static UserAdminListResDto toUserBackOfficeResDto(Page<UserAdminDto> userBackOfficeDtoList) {
		return UserAdminListResDto.builder()
			.userBackOfficeResDto(userBackOfficeDtoList)
			.build();
	}
}

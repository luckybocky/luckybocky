package com.project.luckybocky.admin.respository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.project.luckybocky.admin.dto.UserAdminDto;
import com.project.luckybocky.admin.dto.UserAdminListResDto;
import com.project.luckybocky.pocket.entity.QPocket;
import com.project.luckybocky.user.entity.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserAdminRepositoryImpl implements UserAdminRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public UserAdminListResDto findUsersWithFilters(String startDate, String endDate, Boolean userNickname,
		Pageable pageable) {
		QUser user = QUser.user;
		QPocket pocket = QPocket.pocket;

		OrderSpecifier<?> orderSpecifier = Objects.requireNonNull(pageable.getSort().
			getOrderFor("createdAt")).isAscending() ? user.createdAt.asc() : user.createdAt.desc();

		List<UserAdminDto> content = jpaQueryFactory
			.select(Projections.bean(UserAdminDto.class,
				user.userSeq,
				user.userKey,
				user.userNickname,
				user.firebaseKey,
				user.alarmStatus,
				user.fortuneVisibility,
				user.role,
				pocket.pocketAddress,
				user.createdAt,
				user.modifiedAt,
				user.isDeleted))
			.from(user)
			.leftJoin(user.pockets, pocket)
			.where(
				user.userSeq.gt(0),
				userNickname != null ? userNickname ? user.userNickname.isNotNull() : user.userNickname.isNull() : null,
				startDate != null ? user.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()) : null,
				endDate != null ? user.createdAt.loe(LocalDate.parse(endDate).atTime(LocalTime.MAX)) : null
			)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return UserAdminListResDto.toUserBackOfficeResDto(new PageImpl<>(content, pageable, content.size()));
	}
}

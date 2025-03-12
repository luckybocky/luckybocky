package com.project.luckybocky.common.config;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@Component
@WebFilter("/*")
public class RequestIdFilter implements Filter {
	private static final String REQUEST_ID_HEADER = "X-Request-ID";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;

		// 요청 ID 가져오기
		String requestId = httpRequest.getHeader(REQUEST_ID_HEADER);

		// MDC에 저장하여 로깅 컨텍스트에 추가
		MDC.put("requestID", requestId);
		try {
			chain.doFilter(request, response);
		} finally {
			MDC.remove("requestID");
		}
	}
}

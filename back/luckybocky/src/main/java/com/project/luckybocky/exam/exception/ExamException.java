package com.project.luckybocky.exam.exception;

import lombok.Getter;

@Getter
public class ExamException extends CustomException {
    private final int statusCode = 404;
    private final String message = "테스트 예외";

    //이렇게 커스텀 예외를 만들고 아래처럼 handleException이라는 함수를 GlobalExceptionHandler에 구현해서
    // 간단하게 예외만 넣으면 return 시킬 수 있게 해놨었음 참고하면 좋을 듯

//	private ResponseEntity<ResponseDto> handleException(CustomException e) {
//		ResponseDto responseBody = new ResponseDto(e.getMessage());
//		return ResponseEntity.status(e.getStatusCode()).body(responseBody);
//	}
}
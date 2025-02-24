package com.project.luckybocky.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.article.exception.CommentConflictException;
import com.project.luckybocky.auth.exception.AuthErrorException;
import com.project.luckybocky.auth.exception.LoginFailedException;
import com.project.luckybocky.feedback.exception.FeedbackNotFoundException;
import com.project.luckybocky.feedback.exception.FeedbackSaveException;
import com.project.luckybocky.fortune.exception.FortuneNotFoundException;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.qna.exception.QnaDeleteException;
import com.project.luckybocky.qna.exception.QnaNotFoundException;
import com.project.luckybocky.qna.exception.QnaSaveException;
import com.project.luckybocky.qna.exception.QnaUpdateException;
import com.project.luckybocky.report.exception.ReportNotFoundException;
import com.project.luckybocky.report.exception.ReportSaveException;
import com.project.luckybocky.sharearticle.exception.ShareArticleException;
import com.project.luckybocky.user.exception.ForbiddenUserException;
import com.project.luckybocky.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ex.getMessage()));
	}

	//===== 창희 예외 추가 start ======
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(ArticleNotFoundException.class)
	public ResponseEntity<ResponseDto> handleArticleNotFoundException(ArticleNotFoundException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(PocketNotFoundException.class)
	public ResponseEntity<ResponseDto> handlePocketNotFoundException(PocketNotFoundException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(ShareArticleException.class)
	public ResponseEntity<ResponseDto> handleShareArticleException(ShareArticleException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(FirebaseMessagingException.class)
	public ResponseEntity<ResponseDto> handleFirebaseMessagingException(FirebaseMessagingException ex) {
		return ResponseEntity.status(HttpStatus.GONE).body(new ResponseDto("푸시 전송 에러 발생했습니다.(파이어베이스키가 유효하지 않습니다."));
	}

	//===== 창희 예외 추가 end ======

	//===== 재원 예외 추가 start ======
	@ExceptionHandler(FortuneNotFoundException.class)
	public ResponseEntity<ResponseDto> handleFortuneNotFoundException(FortuneNotFoundException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(CommentConflictException.class)
	public ResponseEntity<ResponseDto> handleCommentConflictException(CommentConflictException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(ForbiddenUserException.class)
	public ResponseEntity<ResponseDto> handleForbiddenUserException(ForbiddenUserException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
	}

	//===== 재원 예외 추가 end ======

	//===== 우재 예외 추가 start ======
	@ExceptionHandler(SessionNotFoundException.class)
	public ResponseEntity<ResponseDto> handleSessionNotFoundException(SessionNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(NicknameNotFoundException.class)
	public ResponseEntity<ResponseDto> handleNicknameNotFoundException(NicknameNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(FeedbackNotFoundException.class)
	public ResponseEntity<ResponseDto> handleFeedbackNotFoundException(FeedbackNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(ReportNotFoundException.class)
	public ResponseEntity<ResponseDto> handleReportNotFoundException(ReportNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ResponseDto> handleLoginFailedException(LoginFailedException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ex.getMessage()));
	}

	@ExceptionHandler(ReportSaveException.class)
	public ResponseEntity<ResponseDto> handleReportSaveException(ReportSaveException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto("Report save error: " + ex.getMessage()));
	}

	@ExceptionHandler(FeedbackSaveException.class)
	public ResponseEntity<ResponseDto> handleFeedbackSaveException(FeedbackSaveException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto("Feedback save error: " + ex.getMessage()));
	}

	@ExceptionHandler(QnaSaveException.class)
	public ResponseEntity<ResponseDto> handleQnaSaveException(QnaSaveException ex) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
			.body(new ResponseDto(("데이터베이스 문제로 인해 QnA를 저장할 수 없니다. -> " + ex.getMessage())));
	}

	@ExceptionHandler(QnaNotFoundException.class)
	public ResponseEntity<ResponseDto> handleQnaNotFoundException(QnaNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ResponseDto((ex.getMessage())));
	}

	@ExceptionHandler(QnaUpdateException.class)
	public ResponseEntity<ResponseDto> handleQnaUpdateException(QnaUpdateException ex) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
			.body(new ResponseDto(("데이터베이스 문제로 인해 QnA를 수정할 수 없니다. -> " + ex.getMessage())));
	}

	@ExceptionHandler(QnaDeleteException.class)
	public ResponseEntity<ResponseDto> handleQnaDeleteException(QnaDeleteException ex) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
			.body(new ResponseDto(("데이터베이스 문제로 인해 QnA를 삭제할 수 없니다. -> " + ex.getMessage())));
	}

	@ExceptionHandler(AuthErrorException.class)
	public ResponseEntity<ResponseDto> handleAuthErrorException(AuthErrorException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.body(new ResponseDto(ex.getMessage()));
	}
	//===== 우재 예외 추가 end ======
}

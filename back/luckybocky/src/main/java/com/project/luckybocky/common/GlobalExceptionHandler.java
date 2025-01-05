package com.project.luckybocky.common;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.luckybocky.article.exception.ArticleNotFoundException;
import com.project.luckybocky.auth.exception.LoginFailedException;
import com.project.luckybocky.feedback.exception.FeedbackNotFoundException;
import com.project.luckybocky.pocket.exception.PocketNotFoundException;
import com.project.luckybocky.report.exception.ReportNotFoundException;
import com.project.luckybocky.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    //===== 창희 예외 추가 start ======
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(ex.getMessage()));
    }


    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ResponseDto> handleArticleNotFoundException(ArticleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(PocketNotFoundException.class)
    public ResponseEntity<ResponseDto> handlePocketNotFoundException(PocketNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(FirebaseMessagingException.class)
    public ResponseEntity<ResponseDto> handleFirebaseMessagingException(FirebaseMessagingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("firebase end error "+ ex.getMessage()));
    }

    //===== 창희 예외 추가 end ======

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
    //===== 우재 예외 추가 end ======

}

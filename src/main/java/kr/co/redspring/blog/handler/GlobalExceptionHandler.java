package kr.co.redspring.blog.handler;

import kr.co.redspring.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice  //모든 Exception이 발생하면 여기로 들어오게한다.  31강
@RestController
public class GlobalExceptionHandler {

    // Exception이 발생되었을 때 이 함수를 실행
    @ExceptionHandler(value=Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e){
        return new ResponseDto<String> (HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
    /*public String handleArgumentException(Exception e){
        return "<h1>"+e.getMessage()+"</h1>";
    }*/

}

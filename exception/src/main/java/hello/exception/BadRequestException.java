package hello.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류") //Exception 자체에 400번 코드를 지정해주는 것
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") //Exception 자체에 400번 코드를 지정해주는 것

public class BadRequestException extends RuntimeException{

}

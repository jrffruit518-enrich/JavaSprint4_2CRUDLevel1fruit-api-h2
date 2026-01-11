package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIdExists(IdExistsException e) {
        return new ErrorResponse(409, e.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(IdNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIdNotExists(IdNotExistsException e) {
        return new ErrorResponse(404, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception exception) {

        return new ErrorResponse(500, exception.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        // 你可以提取具体的错误信息，或者简单返回一个通用的提示
        return new ErrorResponse(400, "Validation failed", LocalDateTime.now());
    }

}

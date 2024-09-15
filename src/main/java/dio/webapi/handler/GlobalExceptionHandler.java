package dio.webapi.handler;

import jakarta.annotation.Resource;
import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        List<Map<String, String>> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> errorDetails = new HashMap<>();
                    errorDetails.put("field", fieldError.getField());
                    errorDetails.put("message", fieldError.getDefaultMessage());
                    return errorDetails;
                })
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("erros", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({BusinessException.class})
//    public  ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
//        ResponseError error = responseError(e.getMessage(), HttpStatus.CONFLICT);
//        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
//    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
            ResponseError error = responseError("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String message, HttpStatus statusCode) {
        ResponseError responseError = new ResponseError();
        responseError.setStatus("error");
        responseError.setError(message);
        responseError.setStatusCode(statusCode.value());
        return responseError;
    }
}

package foodwarehouse.web.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DatabaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(DatabaseException e) {
        return new ResponseEntity<>(new ErrorResponse(new RestError(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

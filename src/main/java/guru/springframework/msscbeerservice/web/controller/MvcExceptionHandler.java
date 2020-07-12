package guru.springframework.msscbeerservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-12 08:55
 */
@ControllerAdvice
public class MvcExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<List> validationErrorHandler(ConstraintViolationException ex) {
    List<String> errorList = new ArrayList<>(ex.getConstraintViolations().size());

    ex.getConstraintViolations()
        .forEach(constraintViolation -> errorList.add(constraintViolation.toString()));

    return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
  }
}

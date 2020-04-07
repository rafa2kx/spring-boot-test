package cu.musala.gateway.controller.advice;

import cu.musala.gateway.exception.EntityNotFoundException;
import cu.musala.gateway.exception.ValidationException;
import cu.musala.gateway.response.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class BusinessErrorAdvice {
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    JsonResponse entityNotFoundHandler(EntityNotFoundException ex) {
        return new JsonResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), false, ex.getId());
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    JsonResponse validationHandler(ValidationException ex) {
        return new JsonResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), false, null);
    }
}

package org.msa.item.exception;

import org.msa.item.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.json.JsonException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> commonHandleException(HttpServletRequest request, Exception e) throws Exception {
        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
        responseBuilder.code("500").message(e.getMessage());
        return ResponseEntity.ok(responseBuilder.build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) throws JsonException {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("]는(은) ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
        responseBuilder.code("500").message(builder.toString());
        return ResponseEntity.ok(responseBuilder.build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> apiException(HttpServletRequest request, ApiException e) throws Exception {
        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
        responseBuilder.code("501").message(e.getMessage());
        return ResponseEntity.ok(responseBuilder.build());
    }

}

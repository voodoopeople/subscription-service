package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionalHandler {

    /**
     * Перехват любого исключения и возврат текстового сообщения с HTTP 500.
     *
     * @param ex      пойманное исключение
     * @param request текущий HTTP-запрос
     * @return ResponseEntity c телом ошибки и статусом 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, HttpServletRequest request) {
        log.error("Error processing request [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage(),
                ex);

        String body = "Ошибка: " + ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.TEXT_PLAIN)
                .body(body);
    }
}


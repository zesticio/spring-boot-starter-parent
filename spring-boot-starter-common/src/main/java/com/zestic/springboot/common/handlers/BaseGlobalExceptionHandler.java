/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zestic.springboot.common.handlers;

import com.zestic.springboot.common.entity.Result;
import com.zestic.springboot.common.ratelimit.RateLimitException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BaseGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseGlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        Result<List<String>> result = new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        result.setData(errors);
        return new ResponseEntity<>(result, headers, status);
    }

    @ExceptionHandler(RateLimitException.class)
    public final ResponseEntity<Result> rateLimitException(RateLimitException ex, WebRequest request) throws IOException {
        logger.error("Rate limit exception", ex);
        return createErrorResponseEntity(ex, HttpStatus.TOO_MANY_REQUESTS, HttpStatus.TOO_MANY_REQUESTS.value());
    }

    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public final ResponseEntity<Result> exceptionHandler(Exception ex, WebRequest request) {
        logger.error("Internal error.", ex);
        return createErrorResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    protected ResponseEntity<Result> createErrorResponseEntity(Exception exception, HttpStatus status, Integer code) {
        logger.error(String.format("Error: %s with code: %d", exception.getMessage(), code), exception);
        Result<Void> result = new Result(code, exception.getMessage());
        ResponseEntity<Result> response = new ResponseEntity<>(result, status);
        return response;
    }
}

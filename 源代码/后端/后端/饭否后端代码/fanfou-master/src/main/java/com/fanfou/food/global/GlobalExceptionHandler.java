package com.fanfou.food.global;

import com.fanfou.food.domain.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

/**
 * @author jzw
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultData> handleException(Exception e) {
        log.info("异常：" + Arrays.toString(e.getStackTrace()));

        ResultData resultData = new ResultData();
        resultData.setCode(500);
        resultData.setData(null);
        resultData.setMessage(e.getMessage());

        return ResponseEntity.ok(resultData);
    }
}


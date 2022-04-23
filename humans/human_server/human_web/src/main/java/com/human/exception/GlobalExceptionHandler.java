package com.human.exception;

import com.human.pojo.RespBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @author Li JieGui
 * @date 2020/12/1 17:35
 * 全局异常统一处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e) {
        if(e instanceof MySQLIntegrityConstraintViolationException) {
            log.error(e + "该数据有关联数据，操作失败！");
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        log.error(e + "数据库异常，操作失败！");
        return RespBean.error("数据库异常，操作失败！");
    }
}

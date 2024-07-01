package pers.johns.crm.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.johns.crm.exception.UserException;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.HttpResultCode;

/**
 * ClassName    : GlobalExceptionHandler
 * <br/>
 * Description  : 全局异常处理器
 * <br/>
 * CreateTime   : 2024/7/1 10:00
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理数据库异常
     * @param e 异常信息
     * @return 响应前端错误信息
     */
    @ExceptionHandler(DataAccessException.class)
    public HttpResult handleException(DataAccessException e) {
        log.error("数据库访问出现问题: {}", e.getMessage());
        return HttpResult.CustomResult(HttpResultCode.DATA_ACCESS_EXCEPTION);
    }

    /**
     * 处理用户业务异常
     * @param e 异常信息
     * @return 响应前端错误信息
     */
    @ExceptionHandler(UserException.class)
    public HttpResult handleException(UserException e) {
        log.error("用户业务出现异常: {}", e.getMessage());
        return HttpResult.CustomResult(HttpResultCode.USER_SERVICE_EXCEPTION);
    }

    /**
     * 处理任意异常
     * @param e 异常信息
     * @return 响应前端错误信息
     */
    @ExceptionHandler(Exception.class)
    public HttpResult handleException(Exception e) {
        log.error("程序出现异常: {}", e.getMessage());
        return HttpResult.Fail(e.getMessage());
    }
}

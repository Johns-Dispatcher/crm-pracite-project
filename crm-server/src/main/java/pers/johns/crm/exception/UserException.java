package pers.johns.crm.exception;

/**
 * ClassName    : UserException
 * <br/>
 * Description  : 用户相关业务异常
 * <br/>
 * CreateTime   : 2024/7/1 10:15
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class UserException extends RuntimeException{
    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }
}

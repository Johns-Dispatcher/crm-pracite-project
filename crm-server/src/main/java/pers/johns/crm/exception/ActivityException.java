package pers.johns.crm.exception;

/**
 * ClassName    : ActivityException
 * <br/>
 * Description  : 活动业务异常
 * <br/>
 * CreateTime   : 2024/7/3 15:45
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class ActivityException extends RuntimeException{
    public ActivityException() {
        super();
    }

    public ActivityException(String message) {
        super(message);
    }
}

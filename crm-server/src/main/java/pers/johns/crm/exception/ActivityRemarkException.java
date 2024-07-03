package pers.johns.crm.exception;

/**
 * ClassName    : ActivityRemarkException
 * <br/>
 * Description  : 活动备注业务异常
 * <br/>
 * CreateTime   : 2024/7/3 23:49
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class ActivityRemarkException extends RuntimeException{
    public ActivityRemarkException() {
        super();
    }

    public ActivityRemarkException(String message) {
        super(message);
    }
}

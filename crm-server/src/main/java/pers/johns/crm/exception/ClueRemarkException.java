package pers.johns.crm.exception;

/**
 * ClassName    : ClueRemarkException
 * <br/>
 * Description  : 线索跟踪方式业务异常
 * <br/>
 * CreateTime   : 2024/7/8 15:59
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class ClueRemarkException extends RuntimeException{
    public ClueRemarkException() {
        super();
    }

    public ClueRemarkException(String message) {
        super(message);
    }
}

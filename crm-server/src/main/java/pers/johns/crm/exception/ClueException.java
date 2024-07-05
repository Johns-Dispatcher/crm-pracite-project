package pers.johns.crm.exception;

/**
 * ClassName    : ClueException
 * <br/>
 * Description  : 线索业务相关异常
 * <br/>
 * CreateTime   : 2024/7/5 20:40
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class ClueException extends RuntimeException{
    public ClueException() {
        super();
    }

    public ClueException(String message) {
        super(message);
    }
}

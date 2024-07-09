package pers.johns.crm.exception;

/**
 * ClassName    : CustomerException
 * <br/>
 * Description  : 客户业务相关异常
 * <br/>
 * CreateTime   : 2024/7/8 21:20
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public class CustomerException extends RuntimeException{
    public CustomerException() {
        super();
    }

    public CustomerException(String message) {
        super(message);
    }
}

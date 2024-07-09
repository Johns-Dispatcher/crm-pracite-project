package pers.johns.crm.service;

import pers.johns.crm.model.vo.ViewCustomer;

/**
 * InterfaceName : CustomerService
 * <br/>
 * Description   : 客户业务接口
 * <br/>
 * CreateTime    : 2024/7/8 20:55
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface CustomerService {
    Boolean checkCustomer(Integer clueId);

    Boolean addCustomer(ViewCustomer viewCustomer);
}

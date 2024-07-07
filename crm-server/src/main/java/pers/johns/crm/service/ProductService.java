package pers.johns.crm.service;

import pers.johns.crm.model.vo.ViewProduct;

import java.util.List;

/**
 * InterfaceName : ProductServiceImpl
 * <br/>
 * Description   : 产品业务接口
 * <br/>
 * CreateTime    : 2024/7/6 23:50
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ProductService {
    /**
     * 查询产品名称
     * @return 产品名称列表
     */
    List<ViewProduct> getProductNames();
}

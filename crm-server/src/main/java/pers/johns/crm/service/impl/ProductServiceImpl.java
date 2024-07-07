package pers.johns.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.manager.RedisManager;
import pers.johns.crm.mapper.ProductMapper;
import pers.johns.crm.model.po.Product;
import pers.johns.crm.model.vo.ViewProduct;
import pers.johns.crm.service.ProductService;
import pers.johns.crm.utils.CacheUtils;

import java.util.List;

/**
 * ClassName    : ProductServiceImpl
 * <br/>
 * Description  : 产品业务实现类
 * <br/>
 * CreateTime   : 2024/7/6 23:53
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final RedisManager redisManager;

    @Override
    public List<ViewProduct> getProductNames() {
        return CacheUtils.getCacheData(
                () -> redisManager.getList(Constants.PRODUCT_NAME_REDIS_KEY, ViewProduct.class),
                () -> {
                    List<Product> products = productMapper.selectAll();
                    return products.stream()
                            .map(product -> ViewProduct
                                    .builder()
                                    .id(product.getId())
                                    .name(product.getName())
                                    .build())
                            .toList();
                },
                list -> redisManager.saveValue(Constants.PRODUCT_NAME_REDIS_KEY, list)
        );
    }
}

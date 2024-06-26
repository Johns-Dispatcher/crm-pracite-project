package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.Product;

import java.util.List;

/**
 * ClassName    : ProductMapperTest
 * <br/>
 * Description  : 测试 {@link ProductMapper} 使用是否正常
 * <br/>
 * CreateTime   : 2024/6/25 21:30
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class ProductMapperTest {
    @Resource
    private ProductMapper productMapper;

    @Test
    public void testGetProducts() {
        log.info("测试查询表 t_product ...");
        List<Product> products = productMapper.selectAll();

        Assertions.assertNotNull(products);

        log.info("共查询出 {} 个结果", products.size());

        products.forEach(product -> log.info("商品信息: {}", product));
    }
}

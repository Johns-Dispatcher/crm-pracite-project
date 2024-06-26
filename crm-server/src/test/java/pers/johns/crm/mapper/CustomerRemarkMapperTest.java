package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.CustomerRemark;

import java.util.List;

/**
 * ClassName    : CustomerRemarkMapperTest
 * <br/>
 * Description  : 测试 {@link CustomerRemarkMapper} 是否正常使用
 * <br/>
 * CreateTime   : 2024/6/25 14:22
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class CustomerRemarkMapperTest {
    @Resource
    private CustomerRemarkMapper customerRemarkMapper;

    @Test
    public void testGetCustomerRemarks() {
        log.info("测试查询表 t_customer_remark ...");
        List<CustomerRemark> customerRemarks = customerRemarkMapper.selectAll();

        Assertions.assertNotNull(customerRemarks);

        log.info("共查询 {} 个结果", customerRemarks.size());

        customerRemarks.forEach(customerRemark -> log.info("顾客备注: {}", customerRemark));
    }
}

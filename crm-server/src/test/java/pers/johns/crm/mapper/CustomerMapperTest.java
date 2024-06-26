package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.Customer;
import pers.johns.crm.model.po.CustomerRemark;

import java.util.List;

/**
 * ClassName    : CustomerMapperTest
 * <br/>
 * Description  : 测试 {@link CustomerMapper} 是否使用正常
 * <br/>
 * CreateTime   : 2024/6/25 13:33
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class CustomerMapperTest {
    @Resource
    private CustomerMapper customerMapper;

    @Test
    public void testGetCustomers() {
        log.info("测试查询表 t_customer ...");
        List<Customer> customers = customerMapper.selectAll();

        Assertions.assertNotNull(customers);

        log.info("共查询到 {} 个结果", customers.size());

        customers.forEach(customer -> log.info("客户信息: {}", customer));
    }

    @Test
    public void testGetCustomerWithRemarks() {
        log.info("测试查询表 t_customer ...");
        log.info("尝试查询顾客 id 为 1 的顾客信息");
        Customer customer = customerMapper.selectById(1);

        Assertions.assertNotNull(customer);

        List<CustomerRemark> customerRemarks = customer.getCustomerRemarks();

        Assertions.assertNotNull(customerRemarks);

        log.info("顾客 {} 的备注信息是:", customer.getDescription());
        customerRemarks.forEach(customerRemark -> log.info("\t备注信息: {}", customerRemark));
    }

}

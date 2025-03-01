package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewCustomer;
import pers.johns.crm.query.CustomerQuery;
import pers.johns.crm.service.CustomerService;

import java.time.LocalDateTime;

/**
 * ClassName    : CustomerController
 * <br/>
 * Description  : 客户相关控制器
 * <br/>
 * CreateTime   : 2024/7/8 21:28
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public HttpResult addCustomer(@RequestBody ViewCustomer viewCustomer, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewCustomer.setCreateBy(securityUser.getId());
        viewCustomer.setCreateTime(LocalDateTime.now());
        viewCustomer.setEditBy(securityUser.getId());
        viewCustomer.setEditTime(LocalDateTime.now());

        return HttpResult.OK("提升为客户成功", customerService.addCustomer(viewCustomer));
    }

    @GetMapping("/page/{current}")
    public HttpResult getCustomersByPage(@PathVariable("current") Integer current) {
        PageInfo<Object> pageInfo = customerService.getCustomersByPage(new CustomerQuery(current));
        return HttpResult.OK("分页查询成功", pageInfo);
    }
}

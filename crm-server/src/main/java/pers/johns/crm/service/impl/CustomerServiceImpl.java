package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ClueException;
import pers.johns.crm.exception.CustomerException;
import pers.johns.crm.mapper.ClueMapper;
import pers.johns.crm.mapper.CustomerMapper;
import pers.johns.crm.model.po.Clue;
import pers.johns.crm.model.po.Customer;
import pers.johns.crm.model.vo.ViewClue;
import pers.johns.crm.model.vo.ViewCustomer;
import pers.johns.crm.model.vo.ViewProduct;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.query.CustomerQuery;
import pers.johns.crm.service.ClueService;
import pers.johns.crm.service.CustomerService;
import pers.johns.crm.service.ProductService;
import pers.johns.crm.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ClassName    : CustomerServiceImpl
 * <br/>
 * Description  : 客户业务实现类
 * <br/>
 * CreateTime   : 2024/7/8 20:56
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final ClueMapper clueMapper;

    private final ClueService clueService;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public Boolean checkCustomer(Integer clueId) {
        return customerMapper.countExistClue(clueId) >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCustomer(ViewCustomer viewCustomer) {
        Integer count = customerMapper.insertCustomer(convertToCustomer(viewCustomer));

        if (count != 1) throw new CustomerException("客户添加失败");

        count = clueMapper.updateClue(Clue
                .builder()
                .id(viewCustomer.getClueId())
                .state(-1)
                .build());

        if (count != 1) throw new ClueException("更新线索状态失败");

        return true;
    }

    @Override
    public PageInfo<Object> getCustomersByPage(CustomerQuery customerQuery) {
        PageHelper.startPage(customerQuery.getCurrentPage(), Constants.DEFAULT_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(customerMapper.selectAll(customerQuery));

        List<Object> list = pageInfo.getList().stream()
                .map(this::convertToViewCustomer)
                .collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    private Customer convertToCustomer(ViewCustomer viewCustomer) {
        return Customer
                .builder()
                .id(viewCustomer.getId())
                .clueId(viewCustomer.getClueId())
                .product(viewCustomer.getProduct())
                .description(viewCustomer.getDescription())
                .nextContactTime(viewCustomer.getNextContactTime())
                .createTime(viewCustomer.getCreateTime())
                .createBy(viewCustomer.getCreateBy())
                .editTime(viewCustomer.getEditTime())
                .editBy(viewCustomer.getEditBy())
                .build();
    }

    private ViewCustomer convertToViewCustomer(Object object) {
        Customer customer = (Customer) object;

        ViewClue clueInfo = clueService.getClueInfo(customer.getClueId());

        String creator = null;
        String editor = null;

        for (ViewUser viewUser : userService.getUserWithName()) {
            if (creator != null && editor != null) break;

            if (creator == null && Objects.equals(viewUser.getId(), customer.getCreateBy()))
                creator = viewUser.getName();

            if (editor == null && Objects.equals(viewUser.getId(), customer.getEditBy()))
                editor = viewUser.getName();
        }

        String productName = null;

        for (ViewProduct product : productService.getProductNames()) {
            if (Objects.equals(product.getId(), customer.getProduct())) {
                productName = product.getName();
                break;
            }
        }

        return ViewCustomer
                .builder()
                .id(customer.getId())
                .viewClue(clueInfo)
                .product(customer.getProduct())
                .productName(productName)
                .nextContactTime(customer.getNextContactTime())
                .editTime(customer.getEditTime())
                .editor(editor)
                .createTime(customer.getCreateTime())
                .creator(creator)
                .build();
    }
}

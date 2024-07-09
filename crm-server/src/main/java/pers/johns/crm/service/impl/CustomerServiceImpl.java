package pers.johns.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.johns.crm.exception.ClueException;
import pers.johns.crm.exception.CustomerException;
import pers.johns.crm.mapper.ClueMapper;
import pers.johns.crm.mapper.CustomerMapper;
import pers.johns.crm.model.po.Clue;
import pers.johns.crm.model.po.Customer;
import pers.johns.crm.model.vo.ViewCustomer;
import pers.johns.crm.service.CustomerService;

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
}

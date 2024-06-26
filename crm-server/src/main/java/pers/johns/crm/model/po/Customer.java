package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName    : Customer
 * <br/>
 * Description  : 客户对象类，对应数据 t_customer
 * <br/>
 * CreateTime   : 2024/6/25 12:39
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private Integer id;
    private Integer clueId;
    private Integer product;
    private String description;
    private LocalDateTime nextContactTime;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private List<CustomerRemark> customerRemarks;
}

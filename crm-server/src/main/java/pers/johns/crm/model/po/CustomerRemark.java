package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : CustomerRemark
 * <br/>
 * Description  : 客户备注类，对应数据表 t_customer_remark
 * <br/>
 * CreateTime   : 2024/6/25 13:38
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRemark {
    private Integer id;
    private Integer customerId;
    private Integer noteWay;
    private String noteContent;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private Integer deleted;
}

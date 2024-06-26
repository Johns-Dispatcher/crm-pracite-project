package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : TranRemark
 * <br/>
 * Description  : 交易备注类，对应数据库表 t_tran_remark
 * <br/>
 * CreateTime   : 2024/6/25 22:09
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranRemark {
    private Integer id;
    private Integer tranId;
    private Integer noteWay;
    private String noteContent;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private Integer deleted;
}

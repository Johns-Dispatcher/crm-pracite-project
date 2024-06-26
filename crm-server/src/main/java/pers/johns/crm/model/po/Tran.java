package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : Tran
 * <br/>
 * Description  : 交易信息类，对应数据库 t_tran
 * <br/>
 * CreateTime   : 2024/6/25 21:37
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tran {
    private Integer id;
    private String tranNo;
    private Integer customerId;
    private Double money;
    private LocalDateTime expectedDate;
    private Integer stage;
    private String description;
    private LocalDateTime nextContactTime;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
}

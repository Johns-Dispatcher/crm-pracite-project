package pers.johns.crm.mode.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : TranHistory
 * <br/>
 * Description  : 交易历史类，对应数据库 t_tran_history
 * <br/>
 * CreateTime   : 2024/6/25 22:41
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranHistory {
    private Integer id;
    private Integer tranId;
    private Integer stage;
    private Double money;
    private LocalDateTime expectedDate;
    private LocalDateTime createTime;
    private Integer createBy;
}

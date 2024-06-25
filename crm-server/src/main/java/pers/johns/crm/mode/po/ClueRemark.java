package pers.johns.crm.mode.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : ClueRemark
 * <br/>
 * Description  : 线索备注类，对应数据库表 t_clue_remark
 * <br/>
 * CreateTime   : 2024/6/25 10:56
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClueRemark {
    private Integer id;
    private Integer clueId;
    private Integer noteWay;
    private String noteContent;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private Integer deleted;
}

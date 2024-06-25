package pers.johns.crm.mode.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : ActivityRemark
 * <br/>
 * Description  : 活动备注类，对应数据库 t_activity_remark
 * <br/>
 * CreateTime   : 2024/6/24 23:06
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRemark {
    private Integer id;
    private Integer activityId;
    private String noteContent;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private Integer deleted;
}

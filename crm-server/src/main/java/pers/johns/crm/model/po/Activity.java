package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName    : Activity
 * <br/>
 * Description  : 活动信息类，对应数据库 t_activity
 * <br/>
 * CreateTime   : 2024/6/24 21:37
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
    private Integer id;
    private Integer ownerId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double cost;
    private String description;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private List<ActivityRemark> activityRemarks;
}

package pers.johns.crm.mode.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName    : Clue
 * <br/>
 * Description  : 线索信息类，对应数据库 t_clue
 * <br/>
 * CreateTime   : 2024/6/25 9:53
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clue {
    private Integer id;
    private Integer ownerId;
    private Integer activityId;
    private String fullName;
    private Integer appellation;
    private String phone;
    private String wechat;
    private String qq;
    private String email;
    private Integer age;
    private String job;
    private Double yearIncome;
    private String address;
    private Integer needLoan;
    private Integer intentionState;
    private Integer intentionProduct;
    private Integer state;
    private Integer source;
    private String description;
    private LocalDateTime nextContactTime;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
    private List<ClueRemark> clueRemarks;
}

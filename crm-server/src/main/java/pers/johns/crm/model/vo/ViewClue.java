package pers.johns.crm.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName    : ViewClue
 * <br/>
 * Description  : 视图线索对象，用于传递前端数据，封装前端数据
 * <br/>
 * CreateTime   : 2024/7/4 21:33
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ViewClue implements Serializable {
    private Integer id;
    private Integer ownerId;
    private String owner;
    private Integer activityId;
    private String activity;
    private String fullName;
    private String appellation;
    private String phone;
    private String wechat;
    private String qq;
    private String email;
    private Integer age;
    private String job;
    private Double yearIncome;
    private String address;
    private String needLoan;
    private String intentionState;
    private Integer intentionProduct;
    private String productName;
    private String state;
    private String source;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextContactTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private Integer createBy;
    private String creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime editTime;
    private Integer editBy;
    private String editor;
}

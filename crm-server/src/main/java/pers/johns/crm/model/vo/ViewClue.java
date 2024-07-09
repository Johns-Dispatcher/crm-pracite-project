package pers.johns.crm.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.johns.crm.model.vo.constant.ClueExcelHeader;

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
    @ExcelProperty(ClueExcelHeader.OWNER_ID)
    private Integer ownerId;
    private String owner;
    @ExcelProperty(ClueExcelHeader.ACTIVITY_ID)
    private Integer activityId;
    private String activity;
    @ExcelProperty(ClueExcelHeader.FULL_NAME)
    private String fullName;
    @ExcelProperty(ClueExcelHeader.APPELLATION)
    private String appellation;
    private Integer appellationDicId;
    @ExcelProperty(ClueExcelHeader.PHONE)
    private String phone;
    @ExcelProperty(ClueExcelHeader.WECHAT)
    private String wechat;
    @ExcelProperty(ClueExcelHeader.QQ)
    private String qq;
    @ExcelProperty(ClueExcelHeader.EMAIL)
    private String email;
    @ExcelProperty(ClueExcelHeader.AGE)
    private Integer age;
    @ExcelProperty(ClueExcelHeader.JOB)
    private String job;
    @ExcelProperty(ClueExcelHeader.YEAR_INCOME)
    private Double yearIncome;
    @ExcelProperty(ClueExcelHeader.ADDRESS)
    private String address;
    @ExcelProperty(ClueExcelHeader.NEED_LOAN)
    private String needLoan;
    private Integer needLoadDicId;
    @ExcelProperty(ClueExcelHeader.INTENTION_STATE)
    private String intentionState;
    private Integer intentionStateDicId;
    private Integer intentionProduct;
    @ExcelProperty(ClueExcelHeader.PRODUCT_NAME)
    private String productName;
    @ExcelProperty(ClueExcelHeader.STATE)
    private String state;
    private Integer stateDicId;
    @ExcelProperty(ClueExcelHeader.SOURCE)
    private String source;
    private Integer sourceDicId;
    @ExcelProperty(ClueExcelHeader.DESCRIPTION)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty(ClueExcelHeader.NEXT_CONTACT_TIME)
    private LocalDateTime nextContactTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private Integer createBy;
    private String creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime editTime;
    private Integer editBy;
    private String editor;
    private Boolean customer;
}

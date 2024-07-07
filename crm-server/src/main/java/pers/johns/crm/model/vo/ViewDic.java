package pers.johns.crm.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pers.johns.crm.model.vo.constant.DicTypeConst;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName    : ViewDic
 * <br/>
 * Description  : 字典视图对象
 * <br/>
 * CreateTime   : 2024/7/6 22:55
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
public class ViewDic implements Serializable {
    private Integer typeId;
    private DicType typeCode;
    private String typeName;
    private String typeRemark;
    private List<ViewDicValue> values;

    /**
     * 用存储字典值的内部类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ViewDicValue implements Serializable{
        private Integer id;
        private String value;
        private Integer order;
        private String remark;
    }

    /**
     * 用于存储字典类型的枚举
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public enum DicType {
        SEX(DicTypeConst.SEX),
        APPELLATION(DicTypeConst.APPELLATION),
        CLUE_STATE(DicTypeConst.CLUE_STATE),
        RETURN_PRIORITY(DicTypeConst.RETURN_PRIORITY),
        RETURN_STATE(DicTypeConst.RETURN_STATE),
        SOURCE(DicTypeConst.SOURCE),
        STAGE(DicTypeConst.STAGE),
        TRANSACTION_TYPE(DicTypeConst.TRANSACTION_TYPE),
        INTENTION_STATE(DicTypeConst.INTENTION_STATE),
        NEED_LOAN(DicTypeConst.NEED_LOAN),
        EDUCATIONAL(DicTypeConst.EDUCATIONAL),
        USER_STATE(DicTypeConst.USER_STATE),
        NOTE_WAY(DicTypeConst.NOTE_WAY),
        ;

        private String value;
    }
}

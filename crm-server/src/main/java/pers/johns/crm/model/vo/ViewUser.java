package pers.johns.crm.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.johns.crm.model.po.Permission;
import pers.johns.crm.model.po.Role;
import pers.johns.crm.model.po.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName    : ViewUser
 * <br/>
 * Description  : 视图用户对象，对用户对象进行了筛选，用于前端信息展示
 * <br/>
 * CreateTime   : 2024/6/28 20:08
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ViewUser implements Serializable {
    private Integer id;
    private String loginAct;
    private String loginPwd;
    private String name;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private Integer createBy;
    private String creatorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime editTime;
    private Integer editBy;
    private String editorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;
    private Boolean accountNoExpired;
    private Boolean accountNoLocked;
    private Boolean accountEnabled;
    private Boolean credentialsNoExpired;
    private List<String> authentications;
    private Long expireTime;
}

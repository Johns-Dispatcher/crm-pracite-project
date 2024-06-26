package pers.johns.crm.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName    : SystemInfo
 * <br/>
 * Description  : 系统信息对象，对应数据库表 t_system_info
 * <br/>
 * CreateTime   : 2024/6/24 15:23
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemInfo {
    private Integer id;
    private String systemCode;
    private String name;
    private String site;
    private String logo;
    private String title;
    private String description;
    private String keywords;
    private String shortcutIcon;
    private String tel;
    private String wechat;
    private String email;
    private String address;
    private String version;
    private String closeMsg;
    private String isOpen;
    private LocalDateTime createTime;
    private Integer createBy;
    private LocalDateTime editTime;
    private Integer editBy;
}

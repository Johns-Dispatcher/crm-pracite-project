package pers.johns.crm.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ClassName    : ActivitySearchQuery
 * <br/>
 * Description  : 用于接收前端的搜索参数对象
 * <br/>
 * CreateTime   : 2024/7/2 20:40
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySearchQuery extends DataFilterQuery {
    private Integer ownerId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Double cost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private Integer current;
}

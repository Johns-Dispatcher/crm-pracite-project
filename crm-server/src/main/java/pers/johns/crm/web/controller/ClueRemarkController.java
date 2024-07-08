package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewClueRemark;
import pers.johns.crm.query.ClueRemarkQuery;
import pers.johns.crm.service.ClueRemarkService;

import java.time.LocalDateTime;

/**
 * ClassName    : ClueRemarkController
 * <br/>
 * Description  : 线索跟踪信息控制器
 * <br/>
 * CreateTime   : 2024/7/8 15:40
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequestMapping("/api/clue/remark")
@RequiredArgsConstructor
public class ClueRemarkController {

    private final ClueRemarkService clueRemarkService;

    /**
     * 添加线索跟进信息
     * @param viewClueRemark 线索跟进信息
     * @param authentication 认证信息
     * @return 含有添加成功的响应结果对象
     */
    @PostMapping("/")
    public HttpResult addClueRemark(@RequestBody ViewClueRemark viewClueRemark, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewClueRemark.setCreateTime(LocalDateTime.now());
        viewClueRemark.setCreateBy(securityUser.getId());
        viewClueRemark.setEditTime(LocalDateTime.now());
        viewClueRemark.setEditBy(securityUser.getId());

        return HttpResult.OK("添加线索跟踪信息成功", clueRemarkService.addClueRemark(viewClueRemark));
    }

    /**
     * 分页查询某个线索的跟进信息
     * @param current 页码
     * @param id 线索 id
     * @return 含有分页信息的响应结果对象
     */
    @GetMapping("/{page}/{id}")
    public HttpResult getClueRemarksByPage(
            @PathVariable("page")
            Integer current,
            @PathVariable("id")
            Integer id
    ) {
        PageInfo<Object> pageInfo = clueRemarkService.getClueRemarksByPage(new ClueRemarkQuery(current, id));
        return HttpResult.OK("分页查询线索跟踪信息成功", pageInfo);
    }

    /**
     * 修改线索跟进信息
     * @param viewClueRemark 线索跟进信息
     * @param authentication 认证信息
     * @return 含有成功信息的响应结果对象
     */
    @PutMapping("/")
    public HttpResult editClueRemark(@RequestBody ViewClueRemark viewClueRemark, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewClueRemark.setEditTime(LocalDateTime.now());
        viewClueRemark.setEditBy(securityUser.getId());

        return HttpResult.OK("修改线索跟踪信息成功", clueRemarkService.editClueRemark(viewClueRemark));
    }

    /**
     * 获取指定线索跟踪信息
     * @param id 线索跟进 id
     * @return 含有线索跟进信息的响应结果对象
     */
    @GetMapping("/info/{id}")
    public HttpResult getClueRemarkInfo(@PathVariable("id") Integer id) {
        return HttpResult.OK("获取线索跟踪信息成功", clueRemarkService.getClueRemarkInfo(id));
    }

    /**
     * 删除指定的线索跟踪信息（逻辑删除）
     * @param id 线索跟踪 id
     * @param authentication 认证信息
     * @return 删除成功响应结果对象
     */
    @DeleteMapping("/{id}")
    public HttpResult deleteClueRemark(@PathVariable("id") Integer id, Authentication authentication) {
        ViewClueRemark viewClueRemark = ViewClueRemark
                .builder()
                .id(id)
                .deleted(true)
                .build();

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewClueRemark.setEditTime(LocalDateTime.now());
        viewClueRemark.setEditBy(securityUser.getId());

        return HttpResult.OK("删除线索跟踪信息成功", clueRemarkService.deleteClueRemark(viewClueRemark));
    }
}

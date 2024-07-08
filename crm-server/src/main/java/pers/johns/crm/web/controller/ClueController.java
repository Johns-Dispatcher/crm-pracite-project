package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewClue;
import pers.johns.crm.query.ClueQuery;
import pers.johns.crm.service.ClueService;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ClassName    : ClueController
 * <br/>
 * Description  : 线索业务相关控制器，接收线索相关的前端请求
 * <br/>
 * CreateTime   : 2024/7/4 21:32
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clue")
@Slf4j
public class ClueController {

    private final ClueService clueService;

    /**
     * 分页查询线索对象
     * @param current 页码
     * @return 含有查询结果的响应结果
     */
    @GetMapping("/page/{current}")
    public HttpResult getCluesByPage(@PathVariable("current") Integer current) {

        PageInfo<Object> pageInfo = clueService.getCluesByPage(new ClueQuery(current));

        return HttpResult.OK("获取线索成功", pageInfo);
    }

    /**
     * 以 Excel 文件批量导入数据库
     * @param file Excel 文件
     * @return 导入成功的响应结果信息
     * @throws IOException I/O 异常
     */
    @PostMapping("/importExcel")
    public HttpResult uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {

        return HttpResult.OK("Excel 导入成功", clueService.importExcel(file));
    }

    /**
     * 添加线索
     * @param viewClue 线索信息
     * @param authentication 认证信息
     * @return 添加成功的响应结果信息
     */
    @PostMapping("/")
    public HttpResult addClue(@RequestBody ViewClue viewClue, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewClue.setOwnerId(securityUser.getId());
        viewClue.setCreateBy(securityUser.getId());
        viewClue.setEditBy(securityUser.getId());
        viewClue.setCreateTime(LocalDateTime.now());
        viewClue.setEditTime(LocalDateTime.now());

        return HttpResult.OK("添加成功", clueService.addClue(viewClue));
    }

    /**
     * 验证当前手机号是否存在
     * @param phone 手机号
     * @return 查询验证结果
     */
    @GetMapping("/phone/{phone}")
    public HttpResult checkPhone(@PathVariable("phone") String phone) {
        return HttpResult.OK("查询结果", clueService.checkPhoneExisted(phone));
    }

    /**
     * 获取指定 id 的线索信息
     * @param id 线索 id
     * @return 含有线索信息的响应结果对象
     */
    @GetMapping("/{id}")
    public HttpResult getClueInfo(@PathVariable("id") Integer id) {
        return HttpResult.OK("线索信息查询成功", clueService.getClueInfo(id));
    }

    /**
     * 修改线索信息
     * @param viewClue 线索信息
     * @param authentication 认证信息
     * @return 修改成功的响应结果信息
     */
    @PutMapping("/")
    public HttpResult editClueInfo(@RequestBody ViewClue viewClue, Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        viewClue.setEditBy(securityUser.getId());
        viewClue.setEditTime(LocalDateTime.now());

        return HttpResult.OK("修改成功", clueService.editClueInfo(viewClue));
    }
}

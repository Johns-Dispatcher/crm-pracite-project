package pers.johns.crm.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.query.ClueQuery;
import pers.johns.crm.service.ClueService;

import java.io.IOException;

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

    @PostMapping("/importExcel")
    public HttpResult uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {

        return HttpResult.OK("Excel 导入成功", clueService.importExcel(file));
    }
}

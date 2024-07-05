package pers.johns.crm.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pers.johns.crm.model.vo.ViewClue;
import pers.johns.crm.service.ClueService;

import java.util.List;

/**
 * ClassName    : UploadClueListener
 * <br/>
 * Description  : 线索上传监听器
 * <br/>
 * CreateTime   : 2024/7/5 19:55
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Slf4j
@RequiredArgsConstructor
public class UploadClueListener implements ReadListener<ViewClue> {

    /**
     * 默认缓存条数
     */
    private static final Integer BATCH_COUNT = 5;

    /**
     * 缓存列表
     */
    private List<ViewClue> cacheDathList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 线索业务对象
     */
    private final ClueService clueService;

    /**
     * 当读取数据时调用该函数处理该行数据
     * @param data    读取好的数据
     * @param context analysis context
     */
    @Override
    public void invoke(ViewClue data, AnalysisContext context) {
        log.info("解析 Excel 数据: {}", data);
        // 将解析数据加入缓存列表
        cacheDathList.add(data);

        if (cacheDathList.size() >= BATCH_COUNT) {
            // 存储数据库
            saveClueData();
            // 将缓存列表置空
            cacheDathList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 当全部数据读取完毕后调用该函数
     * @param context analysis context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveClueData();
        log.info("Excel 读取完毕!");
    }

    /**
     * 存储缓存数据到数据库中
     */
    private void saveClueData() {
        log.info("将数据存入数据库");

        if (clueService.batchAddClues(cacheDathList)) {
            log.info("成功添加数据");
        }
    }
}

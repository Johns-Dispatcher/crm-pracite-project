package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.TranHistory;

import java.util.List;

/**
 * ClassName    : TranHistoryMapperTest
 * <br/>
 * Description  : 测试 {@link TranHistoryMapper} 是否工作正常
 * <br/>
 * CreateTime   : 2024/6/25 23:09
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class TranHistoryMapperTest {
    @Resource
    private TranHistoryMapper tranHistoryMapper;

    @Test
    public void testGetTranHistories() {
        log.info("测试查询表 t_tran_history ...");
        List<TranHistory> tranHistories = tranHistoryMapper.selectAll();

        Assertions.assertNotNull(tranHistories);

        log.info("共查询 {} 个结果", tranHistories.size());

        tranHistories.forEach(tranHistory -> log.info("交易历史: {}", tranHistory));
    }
}

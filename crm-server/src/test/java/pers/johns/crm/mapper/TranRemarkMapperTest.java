package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.mode.po.TranRemark;

import java.util.List;

/**
 * ClassName    : TranRemarkMapperTest
 * <br/>
 * Description  : 测试 {@link TranRemarkMapper} 是否正常工作
 * <br/>
 * CreateTime   : 2024/6/25 22:36
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class TranRemarkMapperTest {
    @Resource
    private TranRemarkMapper tranRemarkMapper;

    @Test
    public void testGetTranRemarks() {
        log.info("测试查询表 t_tran_remark ...");
        List<TranRemark> tranRemarks = tranRemarkMapper.selectAll();

        Assertions.assertNotNull(tranRemarks);

        log.info("共查询 {} 个结果", tranRemarks.size());

        tranRemarks.forEach(tranRemark -> log.info("交易备注: {}", tranRemark));
    }
}

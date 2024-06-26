package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.ClueRemark;

import java.util.List;

/**
 * ClassName    : ClueRemarkMapperTest
 * <br/>
 * Description  : 测试 {@link ClueRemarkMapper} 是否正常使用
 * <br/>
 * CreateTime   : 2024/6/25 11:44
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class ClueRemarkMapperTest {
    @Resource
    private ClueRemarkMapper clueRemarkMapper;

    @Test
    public void testGetClueRemarks() {
        log.info("测试查询表 t_clue_remark ...");
        List<ClueRemark> clueRemarks = clueRemarkMapper.selectAll();

        Assertions.assertNotNull(clueRemarks);

        log.info("查询到 {} 个结果", clueRemarks.size());

        clueRemarks.forEach(clueRemark -> log.info("线索备注信息: {}", clueRemark));
    }
}

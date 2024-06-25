package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.mode.po.Clue;
import pers.johns.crm.mode.po.ClueRemark;

import java.util.List;

/**
 * ClassName    : ClueMapperTest
 * <br/>
 * Description  : 测试 {@link ClueMapper} 是否正常使用
 * <br/>
 * CreateTime   : 2024/6/25 10:46
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class ClueMapperTest {
    @Resource
    private ClueMapper clueMapper;

    @Test
    public void testGetClues() {
        log.info("测试查询表 t_clue ...");
        List<Clue> clues = clueMapper.selectAll();

        Assertions.assertNotNull(clues);

        log.info("共查询到 {} 个结果", clues.size());

        clues.forEach(clue -> log.info("线索信息: {}", clue));
    }

    @Test
    public void testGetClueWithRemarks() {
        log.info("测试查询表 t_clue ...");
        log.info("查询线索 id 为 1 的线索信息");
        Clue clue = clueMapper.selectById(1);

        Assertions.assertNotNull(clue);

        List<ClueRemark> clueRemarks = clue.getClueRemarks();

        Assertions.assertNotNull(clueRemarks);

        log.info("线索 {} 的备注信息为: ", clue.getFullName());
        clueRemarks.forEach(clueRemark -> log.info("\t线索备注: {}", clueRemark));
    }
}

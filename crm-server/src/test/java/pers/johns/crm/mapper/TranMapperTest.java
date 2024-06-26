package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.Tran;

import java.util.List;

/**
 * ClassName    : TranMapperTest
 * <br/>
 * Description  : 测试 {@link TranMapper} 是否正常使用
 * <br/>
 * CreateTime   : 2024/6/25 22:05
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class TranMapperTest {
    @Resource
    private TranMapper tranMapper;

    @Test
    public void testGetTrans() {
        log.info("测试查询表 t_tran ...");
        List<Tran> trans = tranMapper.selectAll();

        Assertions.assertNotNull(trans);

        log.info("共查询到 {} 个结果", trans.size());
        trans.forEach(tran -> log.info("交易信息: {}", tran));
    }
}

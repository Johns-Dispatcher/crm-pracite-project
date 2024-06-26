package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.model.po.DicValue;

import java.util.List;

/**
 * ClassName    : DicValueTest
 * <br/>
 * Description  : 测试 {@link DicValueMapper} 是否正常使用
 * <br/>
 * CreateTime   : 2024/6/25 23:30
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class DicValueTest {
    @Resource
    private DicValueMapper dicValueMapper;

    @Test
    public void testGetDicValues() {
        log.info("测试查询表 t_dic_value ...");
        List<DicValue> dicValues = dicValueMapper.selectAll();

        Assertions.assertNotNull(dicValues);

        log.info("共查询 {} 个结果", dicValues.size());

        dicValues.forEach(dicValue -> log.info("字典值: {}", dicValue));
    }
}

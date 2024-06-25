package pers.johns.crm.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.johns.crm.mode.po.DicType;

import java.util.List;

/**
 * ClassName    : DicTypeMapperTest
 * <br/>
 * Description  : 测试 {@link DicTypeMapper} 是否使用正常
 * <br/>
 * CreateTime   : 2024/6/25 23:53
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@SpringBootTest
@Slf4j
public class DicTypeMapperTest {
    @Resource
    private DicTypeMapper dicTypeMapper;

    @Test
    public void testGetDicTypes() {
        log.info("测试查询表 t_dic_type ...");
        List<DicType> dicTypes = dicTypeMapper.selectAll();

        Assertions.assertNotNull(dicTypes);

        log.info("共查询到 {} 个结果", dicTypes.size());

        dicTypes.forEach(dicType -> log.info("字典类型 {}", dicType));
    }
}

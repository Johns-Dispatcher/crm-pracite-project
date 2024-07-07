package pers.johns.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.manager.RedisManager;
import pers.johns.crm.mapper.DicTypeMapper;
import pers.johns.crm.mapper.DicValueMapper;
import pers.johns.crm.model.po.DicType;
import pers.johns.crm.model.po.DicValue;
import pers.johns.crm.model.vo.ViewDic;
import pers.johns.crm.service.DicService;
import pers.johns.crm.utils.CacheUtils;

import java.util.List;

/**
 * ClassName    : DicServiceImpl
 * <br/>
 * Description  : 字典业务实现类
 * <br/>
 * CreateTime   : 2024/7/6 23:18
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
public class DicServiceImpl implements DicService {

    private final DicTypeMapper dicTypeMapper;
    private final DicValueMapper dicValueMapper;
    private final RedisManager redisManager;

    @Override
    public ViewDic getDicInfo(ViewDic.DicType dicType) {

        return CacheUtils.getCacheData(
                () -> redisManager.getValue(Constants.DIC_VALUE_PREFIX_REDIS_KEY + dicType.getValue(), ViewDic.class),
                () -> {
                    DicType type = dicTypeMapper.selectByTypeCode(dicType.getValue());
                    List<DicValue> dicValues = dicValueMapper.selectByTypeCode(dicType.getValue());

                    return ViewDic.builder()
                            .typeId(type.getId())
                            .typeCode(dicType)
                            .typeName(type.getTypeName())
                            .typeRemark(type.getRemark())
                            .values(dicValues
                                    .stream()
                                    .map(value ->
                                            ViewDic.ViewDicValue
                                                    .builder()
                                                    .id(value.getId())
                                                    .value(value.getTypeValue())
                                                    .order(value.getOrder())
                                                    .remark(value.getRemark())
                                                    .build())
                                    .toList())
                            .build();
                },
                value -> redisManager.saveValue(Constants.DIC_VALUE_PREFIX_REDIS_KEY + dicType.getValue(), value)
        );
    }

    @Override
    public String translateDic(Integer dic) {
        return CacheUtils.getCacheData(
                () -> redisManager.getValue(Constants.DIC_VALUE_PREFIX_REDIS_KEY + dic, String.class),
                () -> dicValueMapper.translateByDic(dic),
                value -> redisManager.saveValue(Constants.DIC_VALUE_PREFIX_REDIS_KEY + dic, value)
        );
    }
}

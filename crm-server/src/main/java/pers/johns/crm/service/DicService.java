package pers.johns.crm.service;

import pers.johns.crm.model.vo.ViewDic;

import java.util.List;

/**
 * InterfaceName : DicService
 * <br/>
 * Description   : 字典相关业务接口
 * <br/>
 * CreateTime    : 2024/7/6 23:18
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface DicService {
    /**
     * 获取字典信息
     * @param dicType 字典的类型
     * @return 对应类型的字典对象
     */
    ViewDic getDicInfo(ViewDic.DicType dicType);

    /**
     * 根据字典 id 进行查询，翻译为对应存储的值
     * @param dic 字典 id
     * @return 字典值
     */
    String translateDic(Integer dic);
}

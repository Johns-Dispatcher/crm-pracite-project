package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import pers.johns.crm.query.ClueQuery;

/**
 * InterfaceName : ClueService
 * <br/>
 * Description   : 线索业务接口
 * <br/>
 * CreateTime    : 2024/7/4 21:40
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ClueService {

    /**
     * 分页查询线索信息
     * @param clueQuery 含有页码信息的查询对象
     * @return 含有分页信息的查询结果
     */
    PageInfo<Object> getCluesByPage(ClueQuery clueQuery);
}

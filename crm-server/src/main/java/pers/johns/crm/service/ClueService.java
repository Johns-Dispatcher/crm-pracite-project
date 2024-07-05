package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import pers.johns.crm.model.vo.ViewClue;
import pers.johns.crm.query.ClueQuery;

import java.io.IOException;
import java.util.List;

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

    /**
     * 导入 Excel 文件以创建新的线索信息
     * @param file 接收的文件对象
     * @return 是否成功导入
     * @throws IOException IO 异常
     */
    Boolean importExcel(MultipartFile file) throws IOException;

    /**
     * 批量添加线索
     * @param viewClues 线索列表集合
     * @return 是否成功导入
     */
    Boolean batchAddClues(List<ViewClue> viewClues);
}

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

    /**
     * 添加线索信息
     * @param viewClue 线索信息
     * @return 是否成功导入
     */
    Boolean addClue(ViewClue viewClue);

    /**
     * 查询手机号是否已经录入
     * @param phone 手机号
     * @return 是否存在
     */
    Boolean checkPhoneExisted(String phone);

    /**
     * 查询指定 id 的线索信息
     * @param id 线索 id
     * @return 线索对象
     */
    ViewClue getClueInfo(Integer id);

    /**
     * 修改指定的线索信息
     * @param viewClue 前端的线索信息
     * @return 是否成功修改
     */
    Boolean editClueInfo(ViewClue viewClue);

    Boolean deleteClue(Integer id);

    Boolean deleteBulkClues(List<Integer> ids);
}

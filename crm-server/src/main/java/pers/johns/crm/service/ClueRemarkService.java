package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import pers.johns.crm.model.vo.ViewClueRemark;
import pers.johns.crm.query.ClueRemarkQuery;

/**
 * InterfaceName : ClueRemarkService
 * <br/>
 * Description   : 线索跟踪信息业务接口
 * <br/>
 * CreateTime    : 2024/7/8 15:47
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface ClueRemarkService {

    /**
     * 添加线索跟进信息
     * @param viewClueRemark 线索跟进信息
     * @return 是否成功
     */
    Boolean addClueRemark(ViewClueRemark viewClueRemark);

    /**
     * 分页查询指定线索跟进信息
     * @param clueRemarkQuery 查询条件对象，含有页码和线索 id 信息
     * @return 含有查询结果的分页对象
     */
    PageInfo<Object> getClueRemarksByPage(ClueRemarkQuery clueRemarkQuery);

    /**
     * 修改线索跟进信息
     * @param viewClueRemark 线索跟进对象
     * @return 是否成功修改
     */
    Boolean editClueRemark(ViewClueRemark viewClueRemark);

    /**
     * 获取指定线索跟进信息
     * @param id 线索 id
     * @return 线索跟进信息
     */
    ViewClueRemark getClueRemarkInfo(Integer id);

    /**
     * 删除指定信息，逻辑删除，所以其实是更新 deleted 字段
     * @param viewClueRemark 含有 id 信息以及删除信息
     * @return 是否删除成功
     */
    Boolean deleteClueRemark(ViewClueRemark viewClueRemark);
}

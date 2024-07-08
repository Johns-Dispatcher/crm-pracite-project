package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ClueRemarkException;
import pers.johns.crm.mapper.ClueRemarkMapper;
import pers.johns.crm.model.po.ClueRemark;
import pers.johns.crm.model.vo.ViewClueRemark;
import pers.johns.crm.model.vo.ViewUser;
import pers.johns.crm.query.ClueRemarkQuery;
import pers.johns.crm.service.ClueRemarkService;
import pers.johns.crm.service.DicService;
import pers.johns.crm.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ClassName    : ClueRemarkServiceImpl
 * <br/>
 * Description  : 线索跟进业务实现类
 * <br/>
 * CreateTime   : 2024/7/8 15:51
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@AllArgsConstructor
public class ClueRemarkServiceImpl implements ClueRemarkService {

    private final ClueRemarkMapper clueRemarkMapper;
    private final UserService userService;
    private final DicService dicService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addClueRemark(ViewClueRemark viewClueRemark) {
        Integer count = clueRemarkMapper.insertClueRemark(convertToClueRemark(viewClueRemark));

        if (count != 1) throw new ClueRemarkException("添加线索跟踪信息失败");

        return true;
    }

    @Override
    public PageInfo<Object> getClueRemarksByPage(ClueRemarkQuery clueRemarkQuery) {
        PageHelper.startPage(clueRemarkQuery.getCurrentPage(), Constants.DEFAULT_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(clueRemarkMapper.selectAllByClueId(clueRemarkQuery));

        List<Object> list = pageInfo.getList().stream().map(this::convertToViewClueRemark).collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editClueRemark(ViewClueRemark viewClueRemark) {
        Integer count = clueRemarkMapper.updateClueRemark(convertToClueRemark(viewClueRemark));

        if (count != 1) throw new ClueRemarkException("添加线索跟踪信息失败");

        return true;
    }

    @Override
    public ViewClueRemark getClueRemarkInfo(Integer id) {
        ClueRemark clueRemark = clueRemarkMapper.selectById(id);

        return convertToViewClueRemark(clueRemark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteClueRemark(ViewClueRemark viewClueRemark) {
        Integer count = clueRemarkMapper.updateClueRemark(convertToClueRemark(viewClueRemark));

        if (count != 1) throw new ClueRemarkException("添加线索跟踪信息失败");

        return true;
    }

    /**
     * 将对象转换为视图线索跟进对象
     * @param object 线索跟进对象 {@link ClueRemark}
     * @return 视图线索跟进对象 {@link ViewClueRemark}
     */
    private ViewClueRemark convertToViewClueRemark(Object object) {
        ClueRemark clueRemark = (ClueRemark) object;

        String creator = null;
        String editor = null;

        for (ViewUser viewUser : userService.getUserWithName()) {
            if (creator != null && editor != null) break;

            if (creator == null && Objects.equals(viewUser.getId(), clueRemark.getCreateBy())) {
                creator = viewUser.getName();
            }

            if (editor == null && Objects.equals(viewUser.getId(), clueRemark.getEditBy())){
                editor = viewUser.getName();
            }
        }

        String noteWayDetail = dicService.translateDic(clueRemark.getNoteWay());

        return ViewClueRemark
                .builder()
                .id(clueRemark.getId())
                .clueId(clueRemark.getClueId())
                .noteWayDetail(noteWayDetail)
                .noteWay(clueRemark.getNoteWay())
                .noteContent(clueRemark.getNoteContent())
                .creator(creator)
                .editor(editor)
                .editTime(clueRemark.getEditTime())
                .build();
    }

    /**
     * 将视图线索跟进对象转换为线索跟进对象
     * @param viewClueRemark 视图线索跟进对象 {@link ViewClueRemark}
     * @return 线索跟进对象 {@link ClueRemark}
     */
    private ClueRemark convertToClueRemark(ViewClueRemark viewClueRemark) {
        return ClueRemark
                .builder()
                .id(viewClueRemark.getId())
                .clueId(viewClueRemark.getClueId())
                .noteWay(viewClueRemark.getNoteWay())
                .noteContent(viewClueRemark.getNoteContent())
                .createBy(viewClueRemark.getCreateBy())
                .createTime(viewClueRemark.getCreateTime())
                .editBy(viewClueRemark.getEditBy())
                .editTime(viewClueRemark.getEditTime())
                .deleted(viewClueRemark.getDeleted() != null && viewClueRemark.getDeleted() ? 1 : 0)
                .build();
    }
}

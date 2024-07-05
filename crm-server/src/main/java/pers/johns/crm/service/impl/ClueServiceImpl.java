package pers.johns.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.mapper.*;
import pers.johns.crm.model.po.Clue;
import pers.johns.crm.model.vo.ViewClue;
import pers.johns.crm.query.ClueQuery;
import pers.johns.crm.service.ClueService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName    : ClueServiceImpl
 * <br/>
 * Description  : 线索业务实现类
 * <br/>
 * CreateTime   : 2024/7/4 21:41
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Service
@RequiredArgsConstructor
public class ClueServiceImpl implements ClueService {

    private final ClueMapper clueMapper;
    private final ActivityMapper activityMapper;
    private final UserMapper userMapper;
    private final DicValueMapper dicValueMapper;
    private final ProductMapper productMapper;

    @Override
    public PageInfo<Object> getCluesByPage(ClueQuery clueQuery) {
        PageHelper.startPage(clueQuery.getCurrentPage(), Constants.DEFAULT_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(clueMapper.selectAll(clueQuery));

        List<Object> list = pageInfo.getList().stream().map(this::convertToViewClue).collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    private ViewClue convertToViewClue(Object object) {
        Clue clue = (Clue) object;

        String owner = userMapper.selectNameById(clue.getOwnerId());

        String activity = activityMapper.selectActivityNameById(clue.getActivityId());

        String appellation = dicValueMapper.translateByDic(clue.getAppellation());
        String needLoan = dicValueMapper.translateByDic(clue.getNeedLoan());
        String intentionState = dicValueMapper.translateByDic(clue.getIntentionState());
        String state = dicValueMapper.translateByDic(clue.getState());
        String source = dicValueMapper.translateByDic(clue.getSource());

        String productName = productMapper.selectProductNameById(clue.getIntentionProduct());

        return ViewClue
                .builder()
                .id(clue.getId())
                .owner(owner)
                .activity(activity)
                .fullName(clue.getFullName())
                .appellation(appellation)
                .phone(clue.getPhone())
                .wechat(clue.getWechat())
                .needLoan(needLoan)
                .intentionState(intentionState)
                .productName(productName)
                .state(state)
                .source(source)
                .nextContactTime(clue.getNextContactTime())
                .build();
    }
}

package pers.johns.crm.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ClueException;
import pers.johns.crm.listener.UploadClueListener;
import pers.johns.crm.mapper.*;
import pers.johns.crm.model.po.Clue;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.model.vo.ViewClue;
import pers.johns.crm.query.ClueQuery;
import pers.johns.crm.service.ClueService;

import java.io.IOException;
import java.time.LocalDateTime;
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
@Slf4j
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

    @Override
    public Boolean importExcel(MultipartFile file) throws IOException {
        // 使用 EasyExcel 完成数据的读取
        EasyExcel.read(
                // 文件输入流
                file.getInputStream(),
                ViewClue.class,
                new UploadClueListener(this)
        ).sheet().doRead();

        return true;
    }

    @Override
    @Transactional
    public Boolean batchAddClues(List<ViewClue> viewClues) {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = securityUser.getId();

        viewClues.forEach(viewClue -> {
            viewClue.setCreateBy(userId);
            viewClue.setEditBy(userId);
        });

        List<Clue> clues = viewClues.stream().map(this::convertToClue).toList();

        Integer count = clueMapper.insertBatchClues(clues);

        if (count != clues.size()) throw new ClueException("批量添加失败");

        return true;
    }

    /**
     * 将视图线索对象转换为线索对象
     * @param viewClue 视图线索对象
     * @return 线索对象
     */
    private Clue convertToClue(ViewClue viewClue) {

        if (userMapper.countById(viewClue.getOwnerId()) != 1) throw new ClueException("负责人非法，请检测 Excel 中的值");
        if (activityMapper.countById(viewClue.getActivityId()) != 1) throw new ClueException("所属活动非法，请检测 Excel 中的值");

        Integer appellation = dicValueMapper.selectDicIdByDicValue(viewClue.getAppellation());
        if (appellation == null) log.warn("未能找到对应数据，无法存储称呼信息");

        Integer needLoan = dicValueMapper.selectDicIdByDicValue(viewClue.getNeedLoan());
        if (needLoan == null) log.warn("未能找到对应数据，无法存储贷款情况");

        Integer intentionState = dicValueMapper.selectDicIdByDicValue(viewClue.getIntentionState());
        if (intentionState == null) log.warn("未能找到对应数据，无法存储意向状态");

        Integer state = dicValueMapper.selectDicIdByDicValue(viewClue.getState());
        if (state == null) log.warn("未能找到对应数据，无法存储线索状态");

        Integer source = dicValueMapper.selectDicIdByDicValue(viewClue.getSource());
        if (source == null) log.warn("未能找到对应数据，无法存储线索来源");

        Integer intentionProduct = productMapper.selectProductIdByName(viewClue.getProductName());
        if (intentionProduct == null) log.warn("未能找到对应数据，无法存储产品名称");

        return Clue
                .builder()
                .ownerId(viewClue.getOwnerId())
                .activityId(viewClue.getActivityId())
                .fullName(viewClue.getFullName())
                .appellation(appellation)
                .phone(viewClue.getPhone())
                .wechat(viewClue.getWechat())
                .qq(viewClue.getQq())
                .email(viewClue.getEmail())
                .age(viewClue.getAge())
                .job(viewClue.getJob())
                .yearIncome(viewClue.getYearIncome())
                .address(viewClue.getAddress())
                .needLoan(needLoan)
                .intentionState(intentionState)
                .intentionProduct(intentionProduct)
                .state(state)
                .source(source)
                .description(viewClue.getDescription())
                .nextContactTime(viewClue.getNextContactTime())
                .createTime(LocalDateTime.now())
                .createBy(viewClue.getCreateBy())
                .editTime(LocalDateTime.now())
                .editBy(viewClue.getEditBy())
                .build();
    }

    /**
     * 将线索对象转换为视图线索对象
     * @param object 线索对象
     * @return 视图线索对象
     */
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

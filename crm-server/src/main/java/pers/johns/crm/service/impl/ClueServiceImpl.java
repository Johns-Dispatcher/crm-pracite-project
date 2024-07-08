package pers.johns.crm.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.exception.ClueException;
import pers.johns.crm.listener.UploadClueListener;
import pers.johns.crm.mapper.*;
import pers.johns.crm.model.po.Clue;
import pers.johns.crm.model.vo.*;
import pers.johns.crm.query.ClueQuery;
import pers.johns.crm.service.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
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
    private final DicService dicService;
    private final UserService userService;
    private final ActivityService activityService;
    private final ProductService productService;

    @Override
    public PageInfo<Object> getCluesByPage(ClueQuery clueQuery) {
        PageHelper.startPage(clueQuery.getCurrentPage(), Constants.DEFAULT_PAGE_SIZE);

        PageInfo<Object> pageInfo = new PageInfo<>(clueMapper.selectAll(clueQuery));

        List<Object> list = pageInfo.getList().stream().map(this::convertToViewClue).collect(Collectors.toList());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchAddClues(List<ViewClue> viewClues) {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = securityUser.getId();

        viewClues.forEach(viewClue -> {
            viewClue.setCreateBy(userId);
            viewClue.setEditBy(userId);
            viewClue.setCreateTime(LocalDateTime.now());
            viewClue.setEditTime(LocalDateTime.now());
        });

        List<Clue> clues = viewClues.stream().map(this::convertToClue).toList();

        Integer count = clueMapper.insertBatchClues(clues);

        if (count != clues.size()) throw new ClueException("批量添加失败");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addClue(ViewClue viewClue) {
        Integer count = clueMapper.insertClue(convertToClue(viewClue));

        if (count != 1) throw new ClueException("添加线索失败");

        return true;
    }

    @Override
    public Boolean checkPhoneExisted(String phone) {
        return clueMapper.selectByPhone(phone) > 0;
    }

    @Override
    public ViewClue getClueInfo(Integer id) {
        Clue clue = clueMapper.selectById(id);
        return convertToViewClue(clue);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editClueInfo(ViewClue viewClue) {
        Integer count = clueMapper.updateClue(convertToClue(viewClue));

        if (count != 1) throw new ClueException("修改线索失败");

        return true;
    }

    /**
     * 将视图线索对象转换为线索对象
     * @param viewClue 视图线索对象
     * @return 线索对象
     */
    private Clue convertToClue(ViewClue viewClue) {

        if (userMapper.countById(viewClue.getOwnerId()) != 1) throw new ClueException("负责人非法，请检测对应项的值");
        if (activityMapper.countById(viewClue.getActivityId()) != 1) log.warn("未能找到对应数据，无法存储活动信息");

        Integer appellation = updateNullValue(
                viewClue.getAppellationDicId(),
                () -> dicValueMapper.selectDicIdByDicValue(viewClue.getAppellation())
        );
        if (appellation == null) log.warn("未能找到对应数据，无法存储称呼信息");

        Integer needLoan = updateNullValue(
                viewClue.getNeedLoadDicId(),
                () -> dicValueMapper.selectDicIdByDicValue(viewClue.getNeedLoan())
        );
        if (needLoan == null) log.warn("未能找到对应数据，无法存储贷款情况");

        Integer intentionState = updateNullValue(
                viewClue.getIntentionStateDicId(),
                () -> dicValueMapper.selectDicIdByDicValue(viewClue.getIntentionState())
        );
        if (intentionState == null) log.warn("未能找到对应数据，无法存储意向状态");

        Integer state = updateNullValue(
                viewClue.getStateDicId(),
                () -> dicValueMapper.selectDicIdByDicValue(viewClue.getState())
        );
        if (state == null) log.warn("未能找到对应数据，无法存储线索状态");

        Integer source = updateNullValue(
                viewClue.getStateDicId(),
                () -> dicValueMapper.selectDicIdByDicValue(viewClue.getSource())
        );
        if (source == null) log.warn("未能找到对应数据，无法存储线索来源");

        Integer intentionProduct = updateNullValue(
                viewClue.getIntentionProduct(),
                () -> productMapper.selectProductIdByName(viewClue.getProductName())
        );
        if (intentionProduct == null) log.warn("未能找到对应数据，无法存储产品名称");

        return Clue
                .builder()
                .id(viewClue.getId())
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
                .createTime(viewClue.getCreateTime())
                .createBy(viewClue.getCreateBy())
                .editTime(viewClue.getEditTime())
                .editBy(viewClue.getEditBy())
                .build();
    }

    /**
     * 将指定字段的空键更新
     * @param value 原始值，判断是否为空为空则更新
     * @param supplier 更新提供者，通常是查询数据库
     * @return 数据最终值
     * @param <T> 数据类型
     */
    private <T> T updateNullValue(T value, Supplier<T> supplier) {
        if (value != null) return value;
        else return supplier.get();
    }

    /**
     * 将线索对象转换为视图线索对象
     * @param object 线索对象
     * @return 视图线索对象
     */
    private ViewClue convertToViewClue(Object object) {
        Clue clue = (Clue) object;

        String owner = null;
        for (ViewUser viewUser : userService.getUserWithName()) {
            if (Objects.equals(viewUser.getId(), clue.getOwnerId())) {
                owner = viewUser.getName();
                break;
            }
        }

        String creator = null;
        for (ViewUser viewUser : userService.getUserWithName()) {
            if (Objects.equals(viewUser.getId(), clue.getCreateBy())) {
                creator = viewUser.getName();
                break;
            }
        }

        String editor = null;
        for (ViewUser viewUser : userService.getUserWithName()) {
            if (Objects.equals(viewUser.getId(), clue.getEditBy())) {
                editor = viewUser.getName();
                break;
            }
        }

        String activity = null;
        for (ViewActivity viewActivity : activityService.getAllActivitiesName()) {
            if (Objects.equals(viewActivity.getId(), clue.getActivityId())) {
                activity = viewActivity.getName();
                break;
            }
        }

        String productName = null;
        for (ViewProduct viewProduct : productService.getProductNames()) {
            if (Objects.equals(viewProduct.getId(), clue.getIntentionProduct())) {
                productName = viewProduct.getName();
                break;
            }
        }

        String appellation = dicService.translateDic(clue.getAppellation());
        String needLoan = dicService.translateDic(clue.getNeedLoan());
        String intentionState = dicService.translateDic(clue.getIntentionState());
        String state = dicService.translateDic(clue.getState());
        String source = dicService.translateDic(clue.getSource());

        return ViewClue
                .builder()
                .id(clue.getId())
                .owner(owner)
                .ownerId(clue.getOwnerId())
                .activity(activity)
                .activityId(clue.getActivityId())
                .fullName(clue.getFullName())
                .appellationDicId(clue.getAppellation())
                .appellation(appellation)
                .phone(clue.getPhone())
                .wechat(clue.getWechat())
                .qq(clue.getQq())
                .email(clue.getEmail())
                .age(clue.getAge())
                .job(clue.getJob())
                .yearIncome(clue.getYearIncome())
                .address(clue.getAddress())
                .needLoadDicId(clue.getNeedLoan())
                .needLoan(needLoan)
                .intentionStateDicId(clue.getIntentionState())
                .intentionState(intentionState)
                .intentionProduct(clue.getIntentionProduct())
                .productName(productName)
                .stateDicId(clue.getState())
                .state(state)
                .sourceDicId(clue.getSource())
                .source(source)
                .nextContactTime(clue.getNextContactTime())
                .description(clue.getDescription())
                .createTime(clue.getCreateTime())
                .creator(creator)
                .editTime(clue.getEditTime())
                .editor(editor)
                .build();
    }
}

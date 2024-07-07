package pers.johns.crm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.ViewDic;
import pers.johns.crm.model.vo.constant.DicTypeConst;
import pers.johns.crm.service.DicService;

/**
 * ClassName    : DicController
 * <br/>
 * Description  : 字典相关业务控制器
 * <br/>
 * CreateTime   : 2024/7/6 23:07
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequestMapping("/api/dic")
@RequiredArgsConstructor
public class DicController {

    private final DicService dicService;

    @GetMapping("/{dicType}")
    public HttpResult getDicValues(@PathVariable("dicType") String dicType) {
        ViewDic result = null;
        switch (dicType) {
            case DicTypeConst.SEX ->
                    result = dicService.getDicInfo(ViewDic.DicType.SEX);
            case DicTypeConst.APPELLATION ->
                    result = dicService.getDicInfo(ViewDic.DicType.APPELLATION);
            case DicTypeConst.CLUE_STATE ->
                    result = dicService.getDicInfo(ViewDic.DicType.CLUE_STATE);
            case DicTypeConst.RETURN_PRIORITY ->
                    result = dicService.getDicInfo(ViewDic.DicType.RETURN_PRIORITY);
            case DicTypeConst.RETURN_STATE ->
                    result = dicService.getDicInfo(ViewDic.DicType.RETURN_STATE);
            case DicTypeConst.SOURCE ->
                    result = dicService.getDicInfo(ViewDic.DicType.SOURCE);
            case DicTypeConst.STAGE ->
                    result = dicService.getDicInfo(ViewDic.DicType.STAGE);
            case DicTypeConst.TRANSACTION_TYPE ->
                    result = dicService.getDicInfo(ViewDic.DicType.TRANSACTION_TYPE);
            case DicTypeConst.INTENTION_STATE ->
                    result = dicService.getDicInfo(ViewDic.DicType.INTENTION_STATE);
            case DicTypeConst.NEED_LOAN ->
                    result = dicService.getDicInfo(ViewDic.DicType.NEED_LOAN);
            case DicTypeConst.EDUCATIONAL ->
                    result = dicService.getDicInfo(ViewDic.DicType.EDUCATIONAL);
            case DicTypeConst.USER_STATE ->
                    result = dicService.getDicInfo(ViewDic.DicType.USER_STATE);
            case DicTypeConst.NOTE_WAY ->
                    result = dicService.getDicInfo(ViewDic.DicType.NOTE_WAY);
        }

        if (result == null) {
            return HttpResult.Fail("获取参数有误，请检测参数");
        } else {
            return HttpResult.OK("获取相关字典信息成功", result);
        }
    }
}

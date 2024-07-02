package pers.johns.crm.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pers.johns.crm.annotation.DataScope;
import pers.johns.crm.constant.Constants;
import pers.johns.crm.model.vo.SecurityUser;
import pers.johns.crm.query.DataFilterQuery;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ClassName    : DataFilterAdvice
 * <br/>
 * Description  : 使用 AOP 进行数据权限过滤
 * <br/>
 * CreateTime   : 2024/7/1 13:43
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DataFilterAdvice {

    @Pointcut("@annotation(pers.johns.crm.annotation.DataScope)")
    private void pointCut() {}

    @Around("DataFilterAdvice.pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("在指定查询之前进行过滤条件的添加");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        // 获取当前登录者的角色信息
        List<String> roleList = securityUser.getRoleList();

        log.info("当前角色列表 : {}", roleList);

        // 如果不是管理员，就进行筛选
        if (!roleList.contains(Constants.DEFAULT_ADMIN_ROLE_NAME)) {
            // 获取查询方法
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method mapperMethod = signature.getMethod();
            DataScope dataScope = mapperMethod.getAnnotation(DataScope.class);

            // 获取过滤字段
            String tableField = dataScope.tableField();

            // 附加过滤条件
            for (Object arg : proceedingJoinPoint.getArgs()) {
                if (arg instanceof DataFilterQuery dataFilterQuery) {
                    dataFilterQuery.setFilterSQL(tableField + " = " + securityUser.getId());
                    break;
                }
            }

            log.info("过滤条件添加完成");
        }

        return proceedingJoinPoint.proceed();
    }
}

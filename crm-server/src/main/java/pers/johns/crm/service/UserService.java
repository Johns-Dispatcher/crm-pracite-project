package pers.johns.crm.service;

import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import pers.johns.crm.model.vo.ViewUser;

import java.util.List;
import java.util.Map;

/**
 * InterfaceName : UserService
 * <br/>
 * Description   : 用户业务结果，处理登录业务
 * <br/>
 * CreateTime    : 2024/6/26 10:16
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */
public interface UserService extends UserDetailsService {
    /**
     * 为指定用户进行续期
     * @param authentication 用户认证对象
     * @return 续期结果的 Map 用于向前端发送数据，含有以下项 <br/>
     *   token - 新生成的 Token
     *   expireTime - 新生成 Token 的过期时间
     */
    Map<String, Object> renewalToken(Authentication authentication);

    /**
     * 读取全部的用户信息
     * @return {@link ViewUser} 视图用户对象集合
     */
    List<ViewUser> getAllUsers();

    /**
     * 分页查询用户信息
     * @param currentPage 当前页数
     * @return 分页对象，使用 Object 是为了在方法中切换为视图对象
     */
    PageInfo<Object> getUserByPage(Integer currentPage);

    /**
     * 按照登录名称查询用户
     * @param loginAct 登录名称
     * @return 视图用户对象
     */
    ViewUser getUserByLoginAct(String loginAct);
}

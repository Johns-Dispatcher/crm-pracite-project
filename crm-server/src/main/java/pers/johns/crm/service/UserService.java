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

    /**
     * 查询数据库是否已经存在相同账户
     * @param loginAct 账户名
     * @return 是否存在
     */
    Boolean checkLoginAct(String loginAct);

    /**
     * 向数据库中添加用户
     * @param viewUser 视图用户对象，包含有前端数据
     * @return 是否添加成功
     */
    Boolean addUser(ViewUser viewUser);

    /**
     * 编辑指定用户，修改数据库中数据
     * @param viewUser 视图用户对象
     * @return 是否修改成功
     */
    Boolean editUser(ViewUser viewUser);

    /**
     * 删除指定 ID 的用户
     * @param id 用户id
     * @return 是否删除成功
     */
    Boolean deleteUser(Integer id);

    Boolean deleteUsersByIds(List<Integer> ids);
}

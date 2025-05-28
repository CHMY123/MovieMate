package cn.edu.scnu.moviemate.service;

import cn.edu.scnu.moviemate.entity.User;
import java.util.List;

public interface UserService {
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册是否成功
     */
    boolean register(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回token，失败返回null
     */
    String login(String username, String password);

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserInfo(Long userId);

    /**
     * 升级为VIP用户
     * @param userId 用户ID
     * @return 升级是否成功
     */
    boolean upgradeToVip(Long userId);

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);

    /**
     * 获取所有用户
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 创建新用户
     * @param user 用户信息
     */
    void createUser(User user);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新是否成功
     */
    boolean updateUser(User user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 更新用户最后登录时间
     * @param id 用户ID
     */
    void updateLastLoginTime(Long id);
}

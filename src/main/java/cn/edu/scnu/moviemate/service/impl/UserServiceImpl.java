package cn.edu.scnu.moviemate.service.impl;

import cn.edu.scnu.moviemate.entity.User;
import cn.edu.scnu.moviemate.mapper.UserMapper;
import cn.edu.scnu.moviemate.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String USER_KEY = "user:";

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        if (userMapper.selectOne(queryWrapper) != null) {
            return false;
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置用户类型为普通用户
        user.setUserType(0);
        // 设置创建时间（修改为 new Date()）
        user.setCreateTime(new Date());

        return userMapper.insert(user) > 0;
    }

    @Override
    public String login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 生成token
            String token = UUID.randomUUID().toString();
            // 更新最后登录时间（修改为 new Date()）
            user.setLastLoginTime(new Date());
            userMapper.updateById(user);
            // 将用户信息存入Redis
            redisTemplate.opsForValue().set("user:" + token, user, 7, TimeUnit.DAYS);
            return token;
        }
        return null;
    }

    @Override
    public User getUserInfo(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public boolean upgradeToVip(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setUserType(1);
            return userMapper.updateById(user) > 0;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserById(Long id) {
        String key = USER_KEY + id;
        User user = (User) redisTemplate.opsForValue().get(key);

        if (user == null) {
            user = userMapper.selectById(id);
            if (user != null) {
                redisTemplate.opsForValue().set(key, user, 30, TimeUnit.MINUTES);
            }
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public void createUser(User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置创建时间（修改为 new Date()）
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }

    @Override
    public boolean updateUser(User user) {
        // 如果密码被修改，需要重新加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        boolean result = userMapper.updateById(user) > 0;
        if (result) {
            // 清除缓存
            redisTemplate.delete(USER_KEY + user.getId());
        }
        return result;
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
        // 清除缓存
        redisTemplate.delete(USER_KEY + id);
    }

    @Override
    public void updateLastLoginTime(Long id) {
        User user = new User();
        user.setId(id);
        // 设置最后登录时间（修改为 new Date()）
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);
        // 清除缓存
        redisTemplate.delete(USER_KEY + id);
    }
}
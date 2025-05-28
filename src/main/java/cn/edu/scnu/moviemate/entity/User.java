package cn.edu.scnu.moviemate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    @TableField("user_type")
    private Integer userType; // 0=普通用户，1=VIP用户
    @TableField("vip_expire_time")
    private LocalDateTime vipExpireTime; // VIP过期时间
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;
}
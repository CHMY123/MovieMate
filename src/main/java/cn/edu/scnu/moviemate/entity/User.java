package cn.edu.scnu.moviemate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    @TableField("user_type")
    private Integer userType; // 0=普通用户，1=VIP用户
    @TableField("vip_expire_time")
    private Date vipExpireTime;
    @TableField("create_time")
    private Date createTime;
    @TableField("last_login_time")
    private Date lastLoginTime;
}
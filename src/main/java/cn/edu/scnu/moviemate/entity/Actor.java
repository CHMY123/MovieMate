package cn.edu.scnu.moviemate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_actor")
public class Actor implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("actor_name")
    private String actorName;
    private Integer gender; // 0=男，1=女
    @TableField("birth_date")
    private Date birthDate; // 修改为 java.util.Date 类型
    private String intro;
    @TableField("image_url")
    private String imageUrl;
    // 关联电影列表（多对多）
    @TableField(exist = false)
    private List<Movie> movies;
    // 关联关系表对象（用于插入关联数据）
    @TableField(exist = false)
    private List<MovieActor> movieActors;
}
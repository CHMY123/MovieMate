package cn.edu.scnu.moviemate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_movie")
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("movie_name")
    private String movieName;
    @TableField("movie_type")
    private String movieType;
    private String region;
    @TableField("director_id")
    private Long directorId;
    @TableField("release_time")
    private Date releaseTime; // 保持为 java.util.Date 类型
    private Double score;
    @TableField("view_count")
    private Long viewCount;
    @TableField("is_vip")
    private Integer isVip; // 0=普通影片，1=VIP影片
    @TableField("cover_img")
    private String coverImg;
    private String description;
    // 关联导演对象（一对一）
    @TableField(exist = false)
    private Director director;
    // 关联演员列表（一对多）
    @TableField(exist = false)
    private List<Actor> actors;
    // 关联关系表对象（用于插入关联数据）
    @TableField(exist = false)
    private List<MovieActor> movieActors;
}
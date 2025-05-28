package cn.edu.scnu.moviemate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@TableName("tb_director")
public class Director implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("director_name")
    private String directorName;
    private Integer gender; // 0=男，1=女
    @TableField("birth_date")
    private LocalDate birthDate;
    private String intro;
    @TableField("image_url")
    private String imageUrl;
    // 关联电影列表（一对多）
    @TableField(exist = false)
    private List<Movie> movies;
    // 关联关系表对象（用于插入关联数据）
    @TableField(exist = false)
    private List<MovieDirector> movieDirectors;
}
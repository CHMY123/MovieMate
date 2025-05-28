package cn.edu.scnu.moviemate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_movie_actor")
public class MovieActor implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long actorId;
    // 关联查询时使用的对象引用
    @TableField(exist = false)
    private Movie movie;
    @TableField(exist = false)
    private Actor actor;
}
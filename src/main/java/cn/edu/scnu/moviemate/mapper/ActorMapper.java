package cn.edu.scnu.moviemate.mapper;

import cn.edu.scnu.moviemate.entity.Actor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ActorMapper {
    @Select("SELECT a.* FROM tb_actor a JOIN tb_movie_actor ma ON a.id = ma.actor_id WHERE ma.movie_id = #{movieId}")
    List<Actor> findActorsByMovieId(Long movieId);
}

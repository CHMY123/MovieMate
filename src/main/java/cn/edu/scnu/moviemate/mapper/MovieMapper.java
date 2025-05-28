package cn.edu.scnu.moviemate.mapper;

import cn.edu.scnu.moviemate.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MovieMapper extends BaseMapper<Movie> {
    
    @Select("SELECT movie_type as name, COUNT(*) as value FROM tb_movie GROUP BY movie_type")
    List<Map<String, Object>> getMovieTypeDistribution();
    
    @Select("SELECT DATE_FORMAT(release_time, '%Y-%m') as month, SUM(view_count) as count " +
            "FROM tb_movie GROUP BY DATE_FORMAT(release_time, '%Y-%m') ORDER BY month")
    List<Map<String, Object>> getMonthlyViewCountTrend();
    
    @Select("SELECT region as name, COUNT(*) as value FROM tb_movie GROUP BY region")
    List<Map<String, Object>> getRegionMovieCount();
    
    @Select("SELECT FLOOR(score) as score, COUNT(*) as count FROM tb_movie GROUP BY FLOOR(score) ORDER BY score")
    List<Map<String, Object>> getScoreDistribution();
    
    @Select("SELECT " +
            "COUNT(CASE WHEN is_vip = 1 THEN 1 END) as vip_count, " +
            "COUNT(*) as total_count, " +
            "ROUND(COUNT(CASE WHEN is_vip = 1 THEN 1 END) * 100.0 / COUNT(*), 2) as vip_ratio " +
            "FROM tb_movie")
    Map<String, Object> getVipMovieRatio();
    
    @Select("SELECT * FROM tb_movie WHERE id IN " +
            "(SELECT movie_id FROM tb_movie_actor WHERE actor_id = #{actorId})")
    List<Movie> findMoviesByActorId(Long actorId);
    
    @Select("SELECT * FROM tb_movie WHERE director_id = #{directorId}")
    List<Movie> findMoviesByDirectorId(Long directorId);
    
    @Select("SELECT * FROM tb_movie ORDER BY view_count DESC LIMIT #{limit}")
    List<Movie> findHotMovies(int limit);
}

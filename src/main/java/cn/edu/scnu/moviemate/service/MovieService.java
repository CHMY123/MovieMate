package cn.edu.scnu.moviemate.service;

import cn.edu.scnu.moviemate.entity.Movie;
import java.util.List;

public interface MovieService {
    /**
     * 获取电影详情
     * @param id 电影ID
     * @return 电影详情
     */
    Movie getMovieDetail(Long id);

    /**
     * 根据类型获取电影列表
     * @param type 电影类型
     * @param page 页码
     * @param size 每页大小
     * @return 电影列表
     */
    List<Movie> getMoviesByType(String type, int page, int size);

    /**
     * 根据地区获取电影列表
     * @param region 地区
     * @param page 页码
     * @param size 每页大小
     * @return 电影列表
     */
    List<Movie> getMoviesByRegion(String region, int page, int size);

    /**
     * 获取热门电影
     * @param limit 限制数量
     * @return 热门电影列表
     */
    List<Movie> getHotMovies(int limit);

    /**
     * 根据演员ID获取电影列表
     * @param actorId 演员ID
     * @return 电影列表
     */
    List<Movie> getMoviesByActor(Long actorId);

    /**
     * 根据导演ID获取电影列表
     * @param directorId 导演ID
     * @return 电影列表
     */
    List<Movie> getMoviesByDirector(Long directorId);

    /**
     * 增加电影播放量
     * @param movieId 电影ID
     */
    void incrementViewCount(Long movieId);
}

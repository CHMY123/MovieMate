package cn.edu.scnu.moviemate.service.impl;

import cn.edu.scnu.moviemate.entity.Movie;
import cn.edu.scnu.moviemate.entity.Actor;
import cn.edu.scnu.moviemate.entity.Director;
import cn.edu.scnu.moviemate.mapper.MovieMapper;
import cn.edu.scnu.moviemate.mapper.ActorMapper;
import cn.edu.scnu.moviemate.mapper.DirectorMapper;
import cn.edu.scnu.moviemate.service.MovieService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ActorMapper actorMapper;
    
    @Autowired
    private DirectorMapper directorMapper;
    
    private static final String HOT_MOVIES_KEY = "hot_movies:";
    private static final String MOVIE_DETAIL_KEY = "movie:detail:";

    @Override
    @SuppressWarnings("unchecked")
    public Movie getMovieDetail(Long id) {
        // 先从Redis缓存中获取
        String key = MOVIE_DETAIL_KEY + id;
        Movie movie = (Movie) redisTemplate.opsForValue().get(key);
        
        if (movie == null) {
            // 缓存未命中，从数据库查询
            movie = movieMapper.selectById(id);
            if (movie != null) {
                // 查询导演
                Director director = directorMapper.selectById(movie.getDirectorId());
                movie.setDirector(director);
                // 查询演员
                List<Actor> actors = actorMapper.findActorsByMovieId(movie.getId());
                movie.setActors(actors);
                // 将结果存入Redis，设置30分钟过期
                redisTemplate.opsForValue().set(key, movie, 30, TimeUnit.MINUTES);
            }
        }
        return movie;
    }

    @Override
    public List<Movie> getMoviesByType(String type, int page, int size) {
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("movie_type", type);
        Page<Movie> moviePage = new Page<>(page, size);
        return movieMapper.selectPage(moviePage, wrapper).getRecords();
    }

    @Override
    public List<Movie> getMoviesByRegion(String region, int page, int size) {
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("region", region);
        Page<Movie> moviePage = new Page<>(page, size);
        return movieMapper.selectPage(moviePage, wrapper).getRecords();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> getHotMovies(int limit) {
        // 先从Redis缓存中获取
        String key = HOT_MOVIES_KEY + limit;
        List<Movie> movies = (List<Movie>) redisTemplate.opsForValue().get(key);
        
        if (movies == null) {
            // 缓存未命中，从数据库查询
            movies = movieMapper.findHotMovies(limit);
            if (movies != null && !movies.isEmpty()) {
                // 将结果存入Redis，设置1小时过期
                redisTemplate.opsForValue().set(key, movies, 1, TimeUnit.HOURS);
            }
        }
        return movies;
    }

    @Override
    public List<Movie> getMoviesByActor(Long actorId) {
        return movieMapper.findMoviesByActorId(actorId);
    }

    @Override
    public List<Movie> getMoviesByDirector(Long directorId) {
        return movieMapper.findMoviesByDirectorId(directorId);
    }

    @Override
    public void incrementViewCount(Long movieId) {
        // 更新数据库中的播放量
        Movie movie = movieMapper.selectById(movieId);
        if (movie != null) {
            movie.setViewCount(movie.getViewCount() + 1);
            movieMapper.updateById(movie);
            
            // 清除相关缓存
            redisTemplate.delete(MOVIE_DETAIL_KEY + movieId);
            redisTemplate.delete(HOT_MOVIES_KEY + "*");
        }
    }
} 
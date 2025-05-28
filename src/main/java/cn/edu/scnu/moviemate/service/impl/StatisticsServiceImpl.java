package cn.edu.scnu.moviemate.service.impl;

import cn.edu.scnu.moviemate.mapper.MovieMapper;
import cn.edu.scnu.moviemate.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private MovieMapper movieMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    private static final String STATS_TYPE_DIST_KEY = "stats:type_dist";
    private static final String STATS_MONTHLY_TREND_KEY = "stats:monthly_trend";
    private static final String STATS_REGION_COUNT_KEY = "stats:region_count";
    private static final String STATS_SCORE_DIST_KEY = "stats:score_dist";
    private static final String STATS_VIP_RATIO_KEY = "stats:vip_ratio";

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getMovieTypeDistribution() {
        // 先从Redis缓存中获取
        List<Map<String, Object>> result = (List<Map<String, Object>>) redisTemplate.opsForValue().get(STATS_TYPE_DIST_KEY);
        if (result == null) {
            // 缓存未命中，从数据库查询
            result = movieMapper.getMovieTypeDistribution();
            if (result != null && !result.isEmpty()) {
                // 将结果存入Redis，设置1小时过期
                redisTemplate.opsForValue().set(STATS_TYPE_DIST_KEY, result, 1, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getMonthlyViewCountTrend() {
        List<Map<String, Object>> result = (List<Map<String, Object>>) redisTemplate.opsForValue().get(STATS_MONTHLY_TREND_KEY);
        if (result == null) {
            result = movieMapper.getMonthlyViewCountTrend();
            if (result != null && !result.isEmpty()) {
                redisTemplate.opsForValue().set(STATS_MONTHLY_TREND_KEY, result, 1, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getRegionMovieCount() {
        List<Map<String, Object>> result = (List<Map<String, Object>>) redisTemplate.opsForValue().get(STATS_REGION_COUNT_KEY);
        if (result == null) {
            result = movieMapper.getRegionMovieCount();
            if (result != null && !result.isEmpty()) {
                redisTemplate.opsForValue().set(STATS_REGION_COUNT_KEY, result, 1, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getScoreDistribution() {
        List<Map<String, Object>> result = (List<Map<String, Object>>) redisTemplate.opsForValue().get(STATS_SCORE_DIST_KEY);
        if (result == null) {
            result = movieMapper.getScoreDistribution();
            if (result != null && !result.isEmpty()) {
                redisTemplate.opsForValue().set(STATS_SCORE_DIST_KEY, result, 1, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getVipMovieRatio() {
        Map<String, Object> result = (Map<String, Object>) redisTemplate.opsForValue().get(STATS_VIP_RATIO_KEY);
        if (result == null) {
            result = movieMapper.getVipMovieRatio();
            if (result != null && !result.isEmpty()) {
                redisTemplate.opsForValue().set(STATS_VIP_RATIO_KEY, result, 1, TimeUnit.HOURS);
            }
        }
        return result;
    }
} 
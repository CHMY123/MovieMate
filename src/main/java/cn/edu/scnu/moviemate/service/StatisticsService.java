package cn.edu.scnu.moviemate.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    // 获取电影类型分布数据
    List<Map<String, Object>> getMovieTypeDistribution();
    
    // 获取月度播放量趋势
    List<Map<String, Object>> getMonthlyViewCountTrend();
    
    // 获取地区电影数量统计
    List<Map<String, Object>> getRegionMovieCount();
    
    // 获取评分分布统计
    List<Map<String, Object>> getScoreDistribution();
    
    // 获取VIP电影占比
    Map<String, Object> getVipMovieRatio();
} 
package cn.edu.scnu.moviemate.controller;

import cn.edu.scnu.moviemate.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/type-distribution")
    public ResponseEntity<?> getMovieTypeDistribution() {
        return ResponseEntity.ok(statisticsService.getMovieTypeDistribution());
    }

    @GetMapping("/monthly-trend")
    public ResponseEntity<?> getMonthlyViewCountTrend() {
        return ResponseEntity.ok(statisticsService.getMonthlyViewCountTrend());
    }

    @GetMapping("/region-count")
    public ResponseEntity<?> getRegionMovieCount() {
        return ResponseEntity.ok(statisticsService.getRegionMovieCount());
    }

    @GetMapping("/score-distribution")
    public ResponseEntity<?> getScoreDistribution() {
        return ResponseEntity.ok(statisticsService.getScoreDistribution());
    }

    @GetMapping("/vip-ratio")
    public ResponseEntity<?> getVipMovieRatio() {
        return ResponseEntity.ok(statisticsService.getVipMovieRatio());
    }
} 
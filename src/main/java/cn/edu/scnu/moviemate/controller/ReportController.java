package cn.edu.scnu.moviemate.controller;

import cn.edu.scnu.moviemate.service.StatisticsService;
import cn.edu.scnu.moviemate.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/movie-rank")
    public ResponseEntity<byte[]> exportMovieRank() throws IOException {
        // 获取电影排行数据
        List<Map<String, Object>> rankData = statisticsService.getMovieTypeDistribution();
        
        // 生成Excel文件
        Workbook workbook = ExcelUtil.generateMovieRankExcel(rankData);
        
        // 将Excel写入字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "电影播放榜单_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        headers.setContentDispositionFormData("attachment", fileName);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

    @GetMapping("/statistics")
    public ResponseEntity<byte[]> exportStatistics() throws IOException {
        // 获取统计数据
        List<Map<String, Object>> typeData = statisticsService.getMovieTypeDistribution();
        List<Map<String, Object>> regionData = statisticsService.getRegionMovieCount();
        List<Map<String, Object>> scoreData = statisticsService.getScoreDistribution();
        Map<String, Object> vipData = statisticsService.getVipMovieRatio();
        
        // 生成Excel文件
        Workbook workbook = ExcelUtil.generateStatisticsExcel(typeData, regionData, scoreData, vipData);
        
        // 将Excel写入字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "电影统计数据_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        headers.setContentDispositionFormData("attachment", fileName);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }
} 
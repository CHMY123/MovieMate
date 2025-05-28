package cn.edu.scnu.moviemate.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public class ExcelUtil {
    
    public static Workbook generateMovieRankExcel(List<Map<String, Object>> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("电影播放榜单");
        
        // 创建标题样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"排名", "电影名称", "类型", "地区", "播放量", "评分"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // 填充数据
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            Map<String, Object> rowData = data.get(i);
            
            dataRow.createCell(0).setCellValue(i + 1);
            dataRow.createCell(1).setCellValue((String) rowData.get("movieName"));
            dataRow.createCell(2).setCellValue((String) rowData.get("movieType"));
            dataRow.createCell(3).setCellValue((String) rowData.get("region"));
            dataRow.createCell(4).setCellValue((Long) rowData.get("viewCount"));
            dataRow.createCell(5).setCellValue((Double) rowData.get("score"));
        }
        
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        return workbook;
    }
    
    public static Workbook generateStatisticsExcel(List<Map<String, Object>> typeData,
                                                 List<Map<String, Object>> regionData,
                                                 List<Map<String, Object>> scoreData,
                                                 Map<String, Object> vipData) {
        Workbook workbook = new XSSFWorkbook();
        
        // 电影类型分布
        Sheet typeSheet = workbook.createSheet("类型分布");
        createDistributionSheet(typeSheet, "电影类型分布", typeData);
        
        // 地区分布
        Sheet regionSheet = workbook.createSheet("地区分布");
        createDistributionSheet(regionSheet, "地区电影数量", regionData);
        
        // 评分分布
        Sheet scoreSheet = workbook.createSheet("评分分布");
        createDistributionSheet(scoreSheet, "评分分布", scoreData);
        
        // VIP占比
        Sheet vipSheet = workbook.createSheet("VIP占比");
        Row headerRow = vipSheet.createRow(0);
        headerRow.createCell(0).setCellValue("统计项");
        headerRow.createCell(1).setCellValue("数值");
        
        Row vipRow = vipSheet.createRow(1);
        vipRow.createCell(0).setCellValue("VIP电影数量");
        vipRow.createCell(1).setCellValue((Long) vipData.get("vip_count"));
        
        Row totalRow = vipSheet.createRow(2);
        totalRow.createCell(0).setCellValue("总电影数量");
        totalRow.createCell(1).setCellValue((Long) vipData.get("total_count"));
        
        Row ratioRow = vipSheet.createRow(3);
        ratioRow.createCell(0).setCellValue("VIP占比(%)");
        ratioRow.createCell(1).setCellValue((Double) vipData.get("vip_ratio"));
        
        // 自动调整列宽
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
        }
        
        return workbook;
    }
    
    private static void createDistributionSheet(Sheet sheet, String title, List<Map<String, Object>> data) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue(title);
        
        Row dataHeaderRow = sheet.createRow(1);
        dataHeaderRow.createCell(0).setCellValue("名称");
        dataHeaderRow.createCell(1).setCellValue("数量");
        
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 2);
            Map<String, Object> rowData = data.get(i);
            dataRow.createCell(0).setCellValue((String) rowData.get("name"));
            dataRow.createCell(1).setCellValue((Long) rowData.get("value"));
        }
    }
} 
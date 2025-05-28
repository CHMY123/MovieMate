# MovieMate 电影网站

MovieMate 是一个基于 Spring Boot 开发的电影网站，提供电影展示、会员管理、VIP 升级等功能。

## 技术栈

- 后端框架：Spring Boot 3.2.4
- 持久层框架：MyBatis Plus 3.5.3.1
- 缓存技术：Redis 6.2
- 前端技术：Thymeleaf 3.1 + Bootstrap 5
- 其他技术：POI 5.2.3（报表生成）、ECharts 5.4.0（数据可视化）、支付宝沙箱支付

## 功能特性

- 用户管理：注册、登录、VIP 升级
- 电影展示：分类浏览、排行榜、搜索
- 主创信息：演员、导演作品展示
- 数据统计：播放量统计、类型分布
- 报表导出：热门电影榜单导出

## 快速开始

### 环境要求

- JDK 21
- MySQL 8.0+
- Redis 6.2+
- Maven 3.8+

### 安装步骤

1. 克隆项目
```bash
git clone https://github.com/yourusername/MovieMate.git
```

2. 创建数据库
```bash
mysql -u root -p < src/main/resources/db/init.sql
```

3. 修改配置
编辑 `src/main/resources/application.yml`，配置数据库和 Redis 连接信息

4. 启动项目
```bash
mvn spring-boot:run
```

5. 访问项目
打开浏览器访问 http://localhost:8080

## 项目结构

```
MovieMate
├── src/main/java/cn/edu/scnu/moviemate
│   ├── controller    // 控制器层
│   ├── service      // 服务层
│   ├── mapper      // 数据访问层
│   ├── entity      // 实体类
│   ├── config      // 配置类
│   ├── utils       // 工具类
│   └── dto         // 数据传输对象
└── src/main/resources
    ├── static      // 静态资源
    ├── templates   // 模板文件
    ├── mapper     // MyBatis XML映射文件
    └── db         // 数据库脚本
```

## 开发团队

- 张三（软工 2301-1）：数据库设计与搭建、Redis 缓存实现、主创作品模块开发
- 李四（软工 2301-2）：会员模块、支付宝沙箱支付集成
- 王五（软工 2301-3）：影片展示模块、ECharts 图表开发
- 赵六（软工 2301-4）：POI 报表生成、系统测试、文档撰写

## 许可证

本项目采用 MIT 许可证 
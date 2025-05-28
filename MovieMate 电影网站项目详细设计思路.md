# MovieMate 电影网站项目详细设计思路

## 一、项目整体架构设计

### （一）技术栈选型

- **后端框架**：Spring Boot 3.2.4（搭建快速开发框架）
- **持久层框架**：MyBatis Plus 3.5.3.1（简化数据库操作）
- **缓存技术**：Redis 6.2（热点数据缓存，提升响应速度）
- **前端技术**：Thymeleaf 3.1（模板引擎）+ Bootstrap 5（前端 UI 框架）
- **加分技术**：POI 5.2.3（报表生成）、ECharts 5.4.0（数据可视化）、支付宝沙箱支付 SDK



### （二）架构分层设计

```plaintext
MovieMate
├── presentation层（控制器层）
│   ├── controller
│   │   ├── UserController（会员模块）
│   │   ├── MovieController（影片模块）
│   │   ├── RankController（排行模块）
│   │   ├── ActorController（主创模块）
│   │   └── PaymentController（支付模块）
├── service层（服务层）
│   ├── service
│   │   ├── UserService（会员服务）
│   │   ├── MovieService（影片服务）
│   │   ├── RankService（排行服务）
│   │   ├── ActorService（主创服务）
│   │   └── PaymentService（支付服务）
├── mapper层（数据访问层）
│   ├── mapper
│   │   ├── UserMapper
│   │   ├── MovieMapper
│   │   ├── ActorMapper
│   │   ├── DirectorMapper
│   │   └── MovieRelationMapper（关联表映射）
├── entity层（实体层）
│   ├── entity
│   │   ├── User（用户实体）
│   │   ├── Movie（电影实体）
│   │   ├── Actor（演员实体）
│   │   ├── Director（导演实体）
│   │   ├── MovieActor（电影-演员关联）
│   │   └── MovieDirector（电影-导演关联）
├── config层（配置层）
│   ├── config
│   │   ├── RedisConfig（Redis配置）
│   │   ├── SecurityConfig（权限配置）
│   │   └── MyBatisPlusConfig（MyBatis Plus配置）
├── utils层（工具类）
│   ├── utils
│   │   ├── ExcelUtil（POI工具类）
│   │   ├── EChartsUtil（图表工具类）
│   │   ├── PaymentUtil（支付工具类）
│   │   └── JwtUtil（令牌工具类）
└── dto层（数据传输对象）
    ├── dto
    │   ├── UserDTO（用户传输对象）
    │   ├── MovieDTO（电影传输对象）
    │   ├── RankDTO（排行传输对象）
    │   └── ActorDTO（主创传输对象）
```



### （三）团队分工方案（4 人团队）

| 成员 | 班别 + 学号 | 具体分工                                             |
| ---- | ----------- | ---------------------------------------------------- |
| 张三 | 软工 2301-1 | 数据库设计与搭建、Redis 缓存实现、主创作品模块开发   |
| 李四 | 软工 2301-2 | 会员模块（注册 / 登录 / 权限）、支付宝沙箱支付集成   |
| 王五 | 软工 2301-3 | 影片展示模块（分类 / 排行 / 分页）、ECharts 图表开发 |
| 赵六 | 软工 2301-4 | POI 报表生成、系统测试、文档撰写与视频录制           |



## 二、数据库详细设计

### （一）核心表结构设计

#### 1. 用户表（tb_user）

| 字段名          | 数据类型     | 主键 | 非空 | 说明                        |
| --------------- | ------------ | ---- | ---- | --------------------------- |
| id              | bigint       | 是   | 是   | 用户 ID                     |
| username        | varchar(50)  | 否   | 是   | 用户名                      |
| password        | varchar(100) | 否   | 是   | 密码（加密存储）            |
| email           | varchar(50)  | 否   | 是   | 邮箱                        |
| phone           | varchar(20)  | 否   | 否   | 手机号                      |
| user_type       | tinyint      | 否   | 是   | 用户类型（0 = 普通，1=VIP） |
| create_time     | datetime     | 否   | 是   | 注册时间                    |
| last_login_time | datetime     | 否   | 否   | 最后登录时间                |



#### 2. 电影表（tb_movie）

| 字段名       | 数据类型     | 主键 | 非空 | 说明                             |
| ------------ | ------------ | ---- | ---- | -------------------------------- |
| id           | bigint       | 是   | 是   | 电影 ID                          |
| movie_name   | varchar(100) | 否   | 是   | 电影名称                         |
| movie_type   | varchar(50)  | 否   | 是   | 电影类型（动作 / 喜剧 / 爱情等） |
| region       | varchar(50)  | 否   | 是   | 地区                             |
| director_id  | bigint       | 否   | 是   | 导演 ID（外键）                  |
| release_time | date         | 否   | 是   | 上映时间                         |
| score        | decimal(3,1) | 否   | 是   | 评分（1-10 分）                  |
| view_count   | bigint       | 否   | 是   | 播放量                           |
| is_vip       | tinyint      | 否   | 是   | 是否 VIP 影片（0 = 普通，1=VIP） |
| cover_img    | varchar(200) | 否   | 否   | 封面图 URL                       |
| description  | text         | 否   | 否   | 电影简介                         |



#### 3. 演员表（tb_actor）

| 字段名     | 数据类型     | 主键 | 非空 | 说明                   |
| ---------- | ------------ | ---- | ---- | ---------------------- |
| id         | bigint       | 是   | 是   | 演员 ID                |
| actor_name | varchar(50)  | 否   | 是   | 演员姓名               |
| gender     | tinyint      | 否   | 是   | 性别（0 = 男，1 = 女） |
| birth_date | date         | 否   | 否   | 出生日期               |
| intro      | text         | 否   | 否   | 演员简介               |
| image_url  | varchar(200) | 否   | 否   | 头像 URL               |



#### 4. 导演表（tb_director）

| 字段名        | 数据类型     | 主键 | 非空 | 说明                   |
| ------------- | ------------ | ---- | ---- | ---------------------- |
| id            | bigint       | 是   | 是   | 导演 ID                |
| director_name | varchar(50)  | 否   | 是   | 导演姓名               |
| gender        | tinyint      | 否   | 是   | 性别（0 = 男，1 = 女） |
| birth_date    | date         | 否   | 否   | 出生日期               |
| intro         | text         | 否   | 否   | 导演简介               |
| image_url     | varchar(200) | 否   | 否   | 头像 URL               |



#### 5. 电影 - 演员关联表（tb_movie_actor）

| 字段名   | 数据类型 | 主键 | 非空 | 说明            |
| -------- | -------- | ---- | ---- | --------------- |
| id       | bigint   | 是   | 是   | 关联 ID         |
| movie_id | bigint   | 否   | 是   | 电影 ID（外键） |
| actor_id | bigint   | 否   | 是   | 演员 ID（外键） |



#### 6. 电影 - 导演关联表（tb_movie_director）

| 字段名      | 数据类型 | 主键 | 非空 | 说明            |
| ----------- | -------- | ---- | ---- | --------------- |
| id          | bigint   | 是   | 是   | 关联 ID         |
| movie_id    | bigint   | 否   | 是   | 电影 ID（外键） |
| director_id | bigint   | 否   | 是   | 导演 ID（外键） |



### （二）索引与约束设计

- **唯一索引**：在`tb_user.username`和`tb_user.email`字段创建唯一索引，确保用户信息唯一性
- **组合索引**：在`tb_movie(movie_type, region)`创建组合索引，优化分类查询性能
- **外键约束**：在关联表中设置外键约束（如`tb_movie.director_id`关联`tb_director.id`）
- **非空约束**：关键业务字段（如用户名、电影名称）设置 NOT NULL 约束



## 三、核心功能模块实现方案

### （一）会员模块（李四负责）

#### 1. 注册功能

- 前端验证：用户名（6-20 位字母 / 数字）、邮箱格式、密码强度（8-20 位，含大小写字母 + 数字）
- 后端逻辑：
  - 验证用户名和邮箱唯一性（查询数据库）
  - 密码加密（BCryptPasswordEncoder）
  - 默认为普通用户（user_type=0）
  - 生成用户 Token（JWT）并缓存至 Redis（有效期 7 天）



#### 2. 登录功能

- 认证流程：
  - ![f5708a4a3a3f9fc1e64c154ae75e63cb](C:\Users\92415\Desktop\f5708a4a3a3f9fc1e64c154ae75e63cb.png)
- 权限控制：基于 Spring Security+JWT 实现，通过 Token 解析用户类型（普通 / VIP）



#### 3. VIP 升级功能

- 集成支付宝沙箱支付：
  - 创建支付订单（订单号、金额、VIP 有效期）
  - 调用支付宝 API 生成支付链接
  - 支付回调处理：更新用户类型为 VIP（user_type=1），设置过期时间
  - 订单状态记录至数据库（待支付 / 已支付 / 已过期）



### （二）影片展示模块（王五负责）

#### 1. 分类展示实现

- 按类型展示：

  ```java
  // MovieService.java
  List<Movie> getMoviesByType(String type, int page, int size) {
      // 使用MyBatis Plus条件构造器
      QueryWrapper<Movie> wrapper = new QueryWrapper<>();
      wrapper.eq("movie_type", type);
      // 分页查询
      Page<Movie> moviePage = new Page<>(page, size);
      return movieMapper.selectPage(moviePage, wrapper).getRecords();
  }
  ```

- **按地区展示**：类似类型查询，通过`region`字段过滤

- **分页实现**：统一使用 MyBatis Plus 的 Page 对象，前端传递 page 和 size 参数



#### . 热播排行实现

- 热度算法：`heat = view_count * 0.6 + score * 0.4 + comment_count * 0.2`
- 缓存策略：
  - 热门电影列表缓存至 Redis（有效期 1 小时）
  - 实时播放量更新时，先更新 Redis 缓存，再异步更新数据库
  - 缓存 Key 设计：`hot_movies:week`（本周排行）、`hot_movies:month`（本月排行）



### （三）主创作品模块（张三负责）

#### 1. 演员作品检索

- 多表联查实现：

  ```java
  // ActorService.java
  List<Movie> getMoviesByActor(Long actorId) {
      // 使用MyBatis Plus关联查询
      return movieMapper.selectList(
          new QueryWrapper<Movie>()
              .inSql("id", 
                  "SELECT movie_id FROM tb_movie_actor WHERE actor_id = " + actorId)
      );
  }
  ```

- 前端交互：点击演员头像 / 姓名，跳转至`/actor/{id}/movies`页面，展示该演员参演的所有电影



#### 2. 导演作品检索

- 实现方式与演员检索类似，通过`tb_movie_director`关联表查询
- 优化点：
  - 导演作品列表启用 Redis 缓存（有效期 24 小时）
  - 提供导演简介卡片，展示导演代表作数量和评分均值



### （四）权限控制模块（李四 + 张三协作）

#### 1. 播放权限控制

- 拦截器实现：

  ```java
  // VipMovieInterceptor.java
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
      // 从JWT令牌获取用户信息
      UserDTO user = JwtUtil.getUserFromToken(request);
      if (user == null) {
          // 未登录，重定向登录页
          response.sendRedirect("/login");
          return false;
      }
      // 获取电影ID
      Long movieId = Long.parseLong(request.getParameter("movieId"));
      // 查询电影是否为VIP影片
      Movie movie = movieService.getById(movieId);
      if (movie != null && movie.getIsVip() == 1 && user.getUserType() == 0) {
          // 普通用户访问VIP影片，返回权限不足
          response.setStatus(403);
          response.getWriter().write("无权限观看此影片");
          return false;
      }
      return true;
  }
  ```

- 前端控制：VIP 影片展示专属标识，普通用户点击时提示需升级 VIP



## 四、加分功能实现方案

### （一）POI 报表生成（赵六负责）

#### 1. 电影播放榜单报表

- 报表内容：

  - 表头：MovieMate 电影播放榜（按周 / 月 / 全部）
  - 列名：排名、电影名称、类型、地区、播放量、评分
  - 数据来源：从 Redis 获取热门电影缓存数据

- 实现代码：

  ```java
  // ExcelUtil.java
  public void generatePlayRankExcel(String type, OutputStream os) {
      Workbook workbook = new XSSFWorkbook();
      Sheet sheet = workbook.createSheet("播放榜单");
      // 创建表头行
      Row headerRow = sheet.createRow(0);
      headerRow.createCell(0).setCellValue("排名");
      headerRow.createCell(1).setCellValue("电影名称");
      // ...其他列
      // 获取排行数据（从Redis或数据库）
      List<RankDTO> rankList = rankService.getRankList(type);
      // 填充数据
      for (int i = 0; i < rankList.size(); i++) {
          Row dataRow = sheet.createRow(i + 1);
          dataRow.createCell(0).setCellValue(i + 1);
          dataRow.createCell(1).setCellValue(rankList.get(i).getMovieName());
          // ...其他数据
      }
      // 自动调整列宽
      for (int i = 0; i < 6; i++) {
          sheet.autoSizeColumn(i);
      }
      workbook.write(os);
      workbook.close();
  }
  ```



### （二）ECharts 图表展示（王五负责）

#### 1. 图表类型与数据

- 电影类型分布饼图：

  - 数据来源：统计`tb_movie`中各类型电影数量

  - 前端代码片段：

    ```html
    <div id="typeChart" style="width: 600px; height: 400px;"></div>
    <script>
    var myChart = echarts.init(document.getElementById('typeChart'));
    var option = {
        title: { text: '电影类型分布' },
        series: [{
            type: 'pie',
            data: [
                {value: 50, name: '动作'},
                {value: 30, name: '喜剧'},
                // ...其他类型
            ]
        }]
    };
    myChart.setOption(option);
    </script>
    ```

- 月度播放量趋势折线图：

  - 数据来源：按月份统计`view_count`总和
  - 功能点：支持鼠标悬停查看具体数值，显示趋势走向



### （三）其他扩展功能（团队可选）

- 电影评论系统：
  - 表设计：`tb_comment`（评论 ID、电影 ID、用户 ID、评论内容、点赞数）
  - 功能：用户登录后可评论，支持评论点赞和回复
- 个性化推荐：
  - 基于用户观看历史（Redis 缓存最近观看记录）
  - 简单推荐算法：观看过动作片的用户推荐同类型热门电影



## 五、系统测试与优化方案

### （一）测试用例设计

#### 1. 会员模块测试

| 测试场景              | 输入数据                    | 预期结果                  |
| --------------------- | --------------------------- | ------------------------- |
| 普通用户注册          | 合法用户名 / 密码 / 邮箱    | 注册成功，用户类型为普通  |
| VIP 用户登录          | VIP 账号 / 密码             | 登录成功，可访问 VIP 影片 |
| 普通用户访问 VIP 影片 | 普通账号登录，点击 VIP 影片 | 提示无权限，无法播放      |



#### 2. 权限控制测试

| 测试场景         | 操作步骤                                | 预期结果                                 |
| ---------------- | --------------------------------------- | ---------------------------------------- |
| 未登录访问首页   | 直接访问首页                            | 正常显示非 VIP 影片，不显示 VIP 影片入口 |
| 未登录访问播放页 | 直接访问 /movie/play?id=123（VIP 影片） | 自动跳转到登录页面                       |
| VIP 用户支付续费 | VIP 用户访问支付页面，选择续费 3 个月   | 生成订单，跳转支付宝沙箱支付页面         |



### （二）性能优化策略

#### 1. Redis 缓存策略

- 热点数据缓存：

  - 热门电影列表（`hot_movies:{period}`）：缓存 1 小时
  - 用户会话信息（`user_session:{token}`）：缓存 7 天
  - 电影详情（`movie_detail:{id}`）：缓存 30 分钟

- 缓存更新机制：

  ```java
  // MovieServiceImpl.java
  @Override
  @Cacheable(value = "movie_detail", key = "#id")
  public Movie getMovieDetail(Long id) {
      // 从数据库查询电影详情
      return movieMapper.selectById(id);
  }
  
  @Override
  @CacheEvict(value = "movie_detail", key = "#movie.id")
  public void updateMovie(Movie movie) {
      // 更新数据库
      movieMapper.updateById(movie);
      // 自动清除对应缓存
  }
  ```



#### 2. 数据库优化

- 慢查询优化：

  - 对`tb_movie`表的`view_count`字段添加索引，优化排行查询
  - 定期清理历史评论数据，归档到冷数据存储

- 分页查询优化：

  ```java
  // 优化前（深度分页问题）
  SELECT * FROM tb_movie ORDER BY view_count DESC LIMIT 100000, 10;
  
  // 优化后（覆盖索引+子查询）
  SELECT * FROM tb_movie 
  WHERE id IN (SELECT id FROM tb_movie ORDER BY view_count DESC LIMIT 100000, 10);
  ```


-- ----------------------------
-- MovieMate数据库创建
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `moviemate` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `moviemate`;

-- ----------------------------
-- 用户表 tb_user
-- ----------------------------
CREATE TABLE `tb_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码（加密存储）',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型（0=普通，1=VIP）',
  `vip_expire_time` datetime DEFAULT NULL COMMENT 'VIP过期时间',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_user_type` (`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- 电影表 tb_movie
-- ----------------------------
CREATE TABLE `tb_movie` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '电影ID',
  `movie_name` varchar(100) NOT NULL COMMENT '电影名称',
  `movie_type` varchar(50) NOT NULL COMMENT '电影类型（动作/喜剧/爱情等）',
  `region` varchar(50) NOT NULL COMMENT '地区',
  `director_id` bigint NOT NULL COMMENT '导演ID',
  `release_time` datetime NOT NULL COMMENT '上映时间', -- 修改为datetime类型
  `score` decimal(3,1) NOT NULL DEFAULT 0.0 COMMENT '评分',
  `view_count` bigint NOT NULL DEFAULT 0 COMMENT '播放量',
  `is_vip` tinyint NOT NULL DEFAULT 0 COMMENT '是否VIP影片（0=普通，1=VIP）',
  `cover_img` varchar(200) DEFAULT NULL COMMENT '封面图URL',
  `description` text COMMENT '电影简介',
  PRIMARY KEY (`id`),
  KEY `idx_movie_type` (`movie_type`),
  KEY `idx_region` (`region`),
  KEY `idx_director_id` (`director_id`),
  KEY `idx_view_count` (`view_count`),
  KEY `idx_is_vip` (`is_vip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='电影表';

-- ----------------------------
-- 演员表 tb_actor
-- ----------------------------
CREATE TABLE `tb_actor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '演员ID',
  `actor_name` varchar(50) NOT NULL COMMENT '演员姓名',
  `gender` tinyint NOT NULL DEFAULT 0 COMMENT '性别（0=男，1=女）',
  `birth_date` datetime DEFAULT NULL COMMENT '出生日期', -- 修改为datetime类型
  `intro` text COMMENT '演员简介',
  `image_url` varchar(200) DEFAULT NULL COMMENT '头像URL',
  PRIMARY KEY (`id`),
  KEY `idx_actor_name` (`actor_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='演员表';

-- ----------------------------
-- 导演表 tb_director
-- ----------------------------
CREATE TABLE `tb_director` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '导演ID',
  `director_name` varchar(50) NOT NULL COMMENT '导演姓名',
  `gender` tinyint NOT NULL DEFAULT 0 COMMENT '性别（0=男，1=女）',
  `birth_date` datetime DEFAULT NULL COMMENT '出生日期', -- 修改为datetime类型
  `intro` text COMMENT '导演简介',
  `image_url` varchar(200) DEFAULT NULL COMMENT '头像URL',
  PRIMARY KEY (`id`),
  KEY `idx_director_name` (`director_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='导演表';

-- ----------------------------
-- 电影-演员关联表 tb_movie_actor
-- ----------------------------
CREATE TABLE `tb_movie_actor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `movie_id` bigint NOT NULL COMMENT '电影ID',
  `actor_id` bigint NOT NULL COMMENT '演员ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_movie_actor` (`movie_id`,`actor_id`),
  KEY `idx_actor_id` (`actor_id`),
  CONSTRAINT `fk_movie_actor_movie` FOREIGN KEY (`movie_id`) REFERENCES `tb_movie` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_movie_actor_actor` FOREIGN KEY (`actor_id`) REFERENCES `tb_actor` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='电影-演员关联表';

-- ----------------------------
-- 电影-导演关联表 tb_movie_director
-- ----------------------------
CREATE TABLE `tb_movie_director` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `movie_id` bigint NOT NULL COMMENT '电影ID',
  `director_id` bigint NOT NULL COMMENT '导演ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_movie_director` (`movie_id`,`director_id`),
  KEY `idx_director_id` (`director_id`),
  CONSTRAINT `fk_movie_director_movie` FOREIGN KEY (`movie_id`) REFERENCES `tb_movie` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_movie_director_director` FOREIGN KEY (`director_id`) REFERENCES `tb_director` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='电影-导演关联表';

-- ----------------------------
-- 订单表 tb_order
-- ----------------------------
CREATE TABLE `tb_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `order_type` tinyint NOT NULL DEFAULT 1 COMMENT '订单类型（1=VIP购买，2=影片点播）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态（0=待支付，1=已支付，2=已取消）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `transaction_id` varchar(100) DEFAULT NULL COMMENT '交易流水号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ----------------------------
-- 初始化数据
-- ----------------------------
-- 插入测试用户
INSERT INTO `tb_user` (`username`, `password`, `email`, `phone`, `user_type`, `create_time`)
VALUES
('user1', '$2a$10$U9S8wYrS1kQzYt7sXlYf6OvJz4jJ1pz9y1m9Jq8jZ9e2c9e2a', 'user1@example.com', '13800138001', 0, NOW()),
('vip_user1', '$2a$10$U9S8wYrS1kQzYt7sXlYf6OvJz4jJ1pz9y1m9Jq8jZ9e2c9e2a', 'vip1@example.com', '13800138002', 1, NOW());

-- 插入测试导演
INSERT INTO `tb_director` (`director_name`, `gender`, `birth_date`, `intro`)
VALUES
('克里斯托弗·诺兰', 0, '1970-07-30 00:00:00', '英国导演、编剧、制片人，代表作《盗梦空间》《星际穿越》等'),
('詹姆斯·卡梅隆', 0, '1954-08-16 00:00:00', '加拿大导演、编剧，代表作《泰坦尼克号》《阿凡达》等');

-- 插入测试演员
INSERT INTO `tb_actor` (`actor_name`, `gender`, `birth_date`, `intro`)
VALUES
('莱昂纳多·迪卡普里奥', 0, '1974-11-11 00:00:00', '美国演员、制片人，代表作《泰坦尼克号》《盗梦空间》等'),
('凯特·温斯莱特', 1, '1975-10-05 00:00:00', '英国演员，代表作《泰坦尼克号》《朗读者》等'),
('马修·麦康纳', 0, '1969-11-04 00:00:00', '美国演员，代表作《星际穿越》《达拉斯买家俱乐部》等');

-- 插入测试电影
INSERT INTO `tb_movie` (`movie_name`, `movie_type`, `region`, `director_id`, `release_time`, `score`, `view_count`, `is_vip`, `cover_img`, `description`)
VALUES
('泰坦尼克号', '爱情/灾难', '美国', 2, '1997-12-19 00:00:00', 9.1, 1234567, 0, 'https://example.com/titanic.jpg', '一部跨越阶级的爱情悲剧，以泰坦尼克号沉船事件为背景'),
('盗梦空间', '科幻/悬疑', '美国', 1, '2010-07-16 00:00:00', 8.8, 987654, 1, 'https://example.com/inception.jpg', '一部关于梦境与现实的科幻电影，探讨人类潜意识的奥秘'),
('星际穿越', '科幻/冒险', '美国', 1, '2014-11-07 00:00:00', 9.0, 876543, 1, 'https://example.com/interstellar.jpg', '一部关于时空旅行和人类未来的科幻巨作');

-- 插入电影-演员关联
INSERT INTO `tb_movie_actor` (`movie_id`, `actor_id`)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 3);

-- 插入电影-导演关联
INSERT INTO `tb_movie_director` (`movie_id`, `director_id`)
VALUES
(1, 2),
(2, 1),
(3, 1);

-- 插入测试订单
INSERT INTO `tb_order` (`order_no`, `user_id`, `amount`, `order_type`, `status`, `create_time`, `pay_time`, `expire_time`, `transaction_id`)
VALUES
('ORD20250527001', 2, 99.00, 1, 1, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), '2025052722001425541400001234');
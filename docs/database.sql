/*
 Navicat Premium Data Transfer

 Source Server         : mysql8--3308
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3308
 Source Schema         : acdc

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 19/02/2023 20:45:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ac_account
-- ----------------------------
DROP TABLE IF EXISTS `ac_account`;
CREATE TABLE `ac_account`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` bigint(0) UNSIGNED NOT NULL,
  `atcoder_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rating` bigint(0) NOT NULL,
  `is_main` tinyint(0) NOT NULL,
  `is_using` tinyint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ac_id`(`id`) USING BTREE,
  UNIQUE INDEX `ac_uname`(`atcoder_id`) USING BTREE,
  INDEX `ac_uid_uid`(`uid`) USING BTREE,
  CONSTRAINT `ac_uid_uid` FOREIGN KEY (`uid`) REFERENCES `normal_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1997 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ac_contest
-- ----------------------------
DROP TABLE IF EXISTS `ac_contest`;
CREATE TABLE `ac_contest`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` int(0) NULL DEFAULT NULL,
  `start_time_stamp` bigint(0) NOT NULL,
  `end_time_stamp` bigint(0) NOT NULL,
  `duration` bigint(0) NOT NULL,
  `is_normal` tinyint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1432 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ac_stucontest
-- ----------------------------
DROP TABLE IF EXISTS `ac_stucontest`;
CREATE TABLE `ac_stucontest`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cid` bigint(0) UNSIGNED NOT NULL,
  `acid` bigint(0) UNSIGNED NOT NULL,
  `t_rank` int(0) NOT NULL,
  `solve` int(0) NOT NULL,
  `diff` int(0) NOT NULL,
  `rating` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `at_contest_acid_uid`(`acid`) USING BTREE,
  INDEX `ac_contest_cid_cid`(`cid`) USING BTREE,
  CONSTRAINT `ac_contest_cid_cid` FOREIGN KEY (`cid`) REFERENCES `ac_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ac_stucontest_acid_acid` FOREIGN KEY (`acid`) REFERENCES `ac_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4336 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ac_submit
-- ----------------------------
DROP TABLE IF EXISTS `ac_submit`;
CREATE TABLE `ac_submit`  (
  `sid` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cid` bigint(0) UNSIGNED NOT NULL,
  `acid` bigint(0) UNSIGNED NOT NULL,
  `q_index` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `submit_time` bigint(0) NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_after` tinyint(0) NOT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sid`) USING BTREE,
  INDEX `cf_submit_acid_acid`(`acid`) USING BTREE,
  INDEX `cf_submit_cid_cid`(`cid`) USING BTREE,
  CONSTRAINT `ac_submit_acid_acid` FOREIGN KEY (`acid`) REFERENCES `ac_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cf_submit_cid_cid` FOREIGN KEY (`cid`) REFERENCES `ac_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 38863031 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ac_submitcode
-- ----------------------------
DROP TABLE IF EXISTS `ac_submitcode`;
CREATE TABLE `ac_submitcode`  (
  `sid` bigint(0) UNSIGNED NOT NULL,
  `code` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`sid`) USING BTREE,
  CONSTRAINT `ac_code_sid_sid` FOREIGN KEY (`sid`) REFERENCES `ac_submit` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `school` bigint(0) UNSIGNED NULL DEFAULT NULL,
  `is_super` tinyint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `school_id`(`school`) USING BTREE,
  CONSTRAINT `school_id` FOREIGN KEY (`school`) REFERENCES `school` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` bigint(0) UNSIGNED NULL DEFAULT NULL,
  `time` bigint(0) NOT NULL,
  `opertation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parameter` json NOT NULL,
  `status` tinyint(0) NOT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operation_time` bigint(0) NULL DEFAULT NULL,
  `school` bigint(0) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid_id`(`uid`) USING BTREE,
  INDEX `application_school_id`(`school`) USING BTREE,
  CONSTRAINT `application_school_id` FOREIGN KEY (`school`) REFERENCES `school` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `uid_id` FOREIGN KEY (`uid`) REFERENCES `normal_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cf_account
-- ----------------------------
DROP TABLE IF EXISTS `cf_account`;
CREATE TABLE `cf_account`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` bigint(0) UNSIGNED NOT NULL,
  `codeforces_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rating` int(0) NULL DEFAULT NULL,
  `is_main` tinyint(0) NOT NULL,
  `is_using` tinyint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `coderforces_account_id`(`id`) USING BTREE,
  UNIQUE INDEX `cf_id`(`codeforces_id`) USING BTREE,
  INDEX `coderforces_account_uid`(`uid`) USING BTREE,
  CONSTRAINT `coderforces_account_uid` FOREIGN KEY (`uid`) REFERENCES `normal_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2475 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cf_contest
-- ----------------------------
DROP TABLE IF EXISTS `cf_contest`;
CREATE TABLE `cf_contest`  (
  `cid` bigint(0) UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `level` int(0) NOT NULL,
  `start_time_stamp` bigint(0) NOT NULL,
  `end_time_stamp` bigint(0) NOT NULL,
  `duration` bigint(0) NOT NULL,
  `is_normal` tinyint(0) NOT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cf_stucontest
-- ----------------------------
DROP TABLE IF EXISTS `cf_stucontest`;
CREATE TABLE `cf_stucontest`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cid` bigint(0) UNSIGNED NOT NULL,
  `cfid` bigint(0) UNSIGNED NOT NULL,
  `t_rank` int(0) NOT NULL,
  `solve` int(0) NOT NULL,
  `diff` int(0) NOT NULL,
  `rating` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stucontest_cfid_uid`(`cfid`) USING BTREE,
  INDEX `stucontest_cid_cid`(`cid`) USING BTREE,
  CONSTRAINT `stucontest_cfid_c` FOREIGN KEY (`cfid`) REFERENCES `cf_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stucontest_cid_f` FOREIGN KEY (`cid`) REFERENCES `cf_contest` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12784 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cf_submit
-- ----------------------------
DROP TABLE IF EXISTS `cf_submit`;
CREATE TABLE `cf_submit`  (
  `sid` bigint(0) UNSIGNED NOT NULL,
  `cid` bigint(0) UNSIGNED NOT NULL,
  `cfid` bigint(0) UNSIGNED NOT NULL,
  `q_index` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `submit_time` bigint(0) NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_after` tinyint(0) NOT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sid`) USING BTREE,
  INDEX `submit_cid_cid`(`cid`) USING BTREE,
  INDEX `submit_cfid_id`(`cfid`) USING BTREE,
  CONSTRAINT `submit_cfid_id` FOREIGN KEY (`cfid`) REFERENCES `cf_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `submit_cid_cid` FOREIGN KEY (`cid`) REFERENCES `cf_contest` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cf_submitcode
-- ----------------------------
DROP TABLE IF EXISTS `cf_submitcode`;
CREATE TABLE `cf_submitcode`  (
  `sid` bigint(0) UNSIGNED NOT NULL,
  `code` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sid`) USING BTREE,
  CONSTRAINT `submit_id_id` FOREIGN KEY (`sid`) REFERENCES `cf_submit` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for codeforces_problems
-- ----------------------------
DROP TABLE IF EXISTS `codeforces_problems`;
CREATE TABLE `codeforces_problems`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cid` bigint(0) UNSIGNED NULL DEFAULT NULL,
  `qindex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `difficulty` int(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `codeforces_peoblem_contest_id_qindex`(`cid`, `qindex`) USING BTREE,
  CONSTRAINT `codeforces_peoblem_contest_id` FOREIGN KEY (`cid`) REFERENCES `cf_contest` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8448 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for codeforces_problems_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `codeforces_problems_tag_map`;
CREATE TABLE `codeforces_problems_tag_map`  (
  `codeforces_problem_id` int(0) UNSIGNED NOT NULL,
  `tag_id` int(0) UNSIGNED NOT NULL,
  PRIMARY KEY (`codeforces_problem_id`, `tag_id`) USING BTREE,
  INDEX `codeforces__tag_id_tagMap`(`tag_id`) USING BTREE,
  CONSTRAINT `codeforces__tag_id_tagMap` FOREIGN KEY (`tag_id`) REFERENCES `problem_tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `codeforces_problem_id_tagMap` FOREIGN KEY (`codeforces_problem_id`) REFERENCES `codeforces_problems` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` bigint(0) UNSIGNED NOT NULL,
  `time` bigint(0) NOT NULL,
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for normal_user
-- ----------------------------
DROP TABLE IF EXISTS `normal_user`;
CREATE TABLE `normal_user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `school` bigint(0) UNSIGNED NULL DEFAULT NULL,
  `classname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `stu_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `year` int(0) NULL DEFAULT NULL,
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '0 禁用 1启用 2退役',
  `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `school`(`school`) USING BTREE,
  CONSTRAINT `school` FOREIGN KEY (`school`) REFERENCES `school` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 790 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `problem_tag`;
CREATE TABLE `problem_tag`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tag_name_s`(`tag_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for spider_log
-- ----------------------------
DROP TABLE IF EXISTS `spider_log`;
CREATE TABLE `spider_log`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `message` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `stage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status_code` int(0) NULL DEFAULT NULL,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spider_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `timestamp` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 329313 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag_user_map
-- ----------------------------
DROP TABLE IF EXISTS `tag_user_map`;
CREATE TABLE `tag_user_map`  (
  `uid` bigint(0) UNSIGNED NOT NULL,
  `tid` bigint(0) UNSIGNED NOT NULL,
  PRIMARY KEY (`uid`, `tid`) USING BTREE,
  INDEX `tag_tid_tag`(`tid`) USING BTREE,
  CONSTRAINT `normal_user_uid_tag` FOREIGN KEY (`uid`) REFERENCES `normal_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tag_tid_tag` FOREIGN KEY (`tid`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `vid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'vserionID',
  `contant` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版本更新的一条信息',
  `status` int(0) NOT NULL COMMENT '该信息状态 0不展示 1展示',
  `type` int(0) NOT NULL COMMENT '0 功能 1优化 2 bug',
  `c_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `u_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `p_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `p_admin` bigint(0) NULL DEFAULT NULL COMMENT '发布用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for ac_account_total_info
-- ----------------------------
DROP VIEW IF EXISTS `ac_account_total_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_account_total_info` AS select `src`.`username` AS `username`,`src`.`realname` AS `realname`,`src`.`classname` AS `classname`,`src`.`school` AS `school`,`src`.`status` AS `status`,`src`.`stu_no` AS `stu_no`,`src`.`year` AS `year`,`src`.`id` AS `id`,`src`.`uid` AS `uid`,`src`.`atcoder_id` AS `atcoder_id`,`src`.`rating` AS `rating`,`src`.`is_main` AS `is_main`,`src`.`is_using` AS `is_using`,`src`.`participate_time` AS `participate_time`,`ac_solve_count`.`solve` AS `solve`,`aft`.`solve` AS `after` from (((select `cawu`.`username` AS `username`,`cawu`.`realname` AS `realname`,`cawu`.`classname` AS `classname`,`cawu`.`school` AS `school`,`cawu`.`status` AS `status`,`cawu`.`stu_no` AS `stu_no`,`cawu`.`year` AS `year`,`cawu`.`id` AS `id`,`cawu`.`uid` AS `uid`,`cawu`.`atcoder_id` AS `atcoder_id`,`cawu`.`rating` AS `rating`,`cawu`.`is_main` AS `is_main`,`cawu`.`is_using` AS `is_using`,count(`acstuc`.`id`) AS `participate_time` from (`ac_account_with_username` `cawu` left join `ac_stucontest` `acstuc` on((`acstuc`.`acid` = `cawu`.`id`))) group by `cawu`.`id`) `src` left join `ac_solve_count` on((`src`.`id` = `ac_solve_count`.`acid`))) left join (select `ac_after`.`acid` AS `acid`,count(distinct `ac_after`.`cid`,`ac_after`.`q_index`) AS `solve` from `ac_after` group by `ac_after`.`acid`) `aft` on((`src`.`id` = `aft`.`acid`)));

-- ----------------------------
-- View structure for ac_account_with_username
-- ----------------------------
DROP VIEW IF EXISTS `ac_account_with_username`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_account_with_username` AS select `normal_user`.`username` AS `username`,`normal_user`.`classname` AS `classname`,`normal_user`.`school` AS `school`,`normal_user`.`status` AS `status`,`normal_user`.`stu_no` AS `stu_no`,`normal_user`.`year` AS `year`,`normal_user`.`realname` AS `realname`,`ac_account`.`id` AS `id`,`ac_account`.`uid` AS `uid`,`ac_account`.`atcoder_id` AS `atcoder_id`,`ac_account`.`rating` AS `rating`,`ac_account`.`is_main` AS `is_main`,`ac_account`.`is_using` AS `is_using` from (`ac_account` left join `normal_user` on((`ac_account`.`uid` = `normal_user`.`id`)));

-- ----------------------------
-- View structure for ac_after
-- ----------------------------
DROP VIEW IF EXISTS `ac_after`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_after` AS select distinct `t1`.`sid` AS `sid`,`t1`.`acid` AS `acid`,`t1`.`cid` AS `cid`,`t1`.`q_index` AS `q_index` from `ac_submit` `t1` where ((`t1`.`status` = 'AC') and exists(select distinct `t2`.`acid`,`t2`.`cid`,`t2`.`q_index` from `ac_submit` `t2` where ((`t2`.`status` = 'AC') and (`t2`.`is_after` = 0) and (`t1`.`acid` = `t2`.`acid`) and (`t1`.`cid` = `t2`.`cid`) and (`t1`.`q_index` = `t2`.`q_index`))) is false);

-- ----------------------------
-- View structure for ac_contest_with_participate
-- ----------------------------
DROP VIEW IF EXISTS `ac_contest_with_participate`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_contest_with_participate` AS select `acc`.`id` AS `id`,`acc`.`name` AS `name`,`acc`.`type` AS `type`,`acc`.`level` AS `level`,`acc`.`start_time_stamp` AS `start_time_stamp`,`acc`.`end_time_stamp` AS `end_time_stamp`,`acc`.`duration` AS `duration`,`acc`.`is_normal` AS `is_normal`,count(`acsc`.`acid`) AS `participate` from (`ac_contest` `acc` left join `ac_stucontest` `acsc` on((`acc`.`id` = `acsc`.`cid`))) group by `acc`.`id`;

-- ----------------------------
-- View structure for ac_month_submit
-- ----------------------------
DROP VIEW IF EXISTS `ac_month_submit`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_month_submit` AS select `ac_account_total_info`.`username` AS `username`,`t_cnt`.`acid` AS `acid`,`t_cnt`.`ym_date` AS `ym_date`,`t_cnt`.`submit_cnt` AS `submit_cnt` from ((select `ac_submit_with_ym`.`acid` AS `acid`,`ac_submit_with_ym`.`ym_date` AS `ym_date`,count(0) AS `submit_cnt` from (select `ac_submit`.`sid` AS `sid`,`ac_submit`.`cid` AS `cid`,`ac_submit`.`acid` AS `acid`,`ac_submit`.`q_index` AS `q_index`,`ac_submit`.`submit_time` AS `submit_time`,`ac_submit`.`status` AS `status`,`ac_submit`.`is_after` AS `is_after`,`ac_submit`.`language` AS `language`,date_format(from_unixtime(`ac_submit`.`submit_time`),'%Y-%m') AS `ym_date` from `ac_submit`) `ac_submit_with_ym` group by `ac_submit_with_ym`.`acid`,`ac_submit_with_ym`.`ym_date`) `t_cnt` left join `ac_account_total_info` on((`t_cnt`.`acid` = `ac_account_total_info`.`id`)));

-- ----------------------------
-- View structure for ac_solve_count
-- ----------------------------
DROP VIEW IF EXISTS `ac_solve_count`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_solve_count` AS select `ac_submit`.`acid` AS `acid`,count(distinct `ac_submit`.`acid`,`ac_submit`.`cid`,`ac_submit`.`q_index`) AS `solve` from (`ac_submit` left join `ac_account` on((`ac_submit`.`acid` = `ac_account`.`id`))) where (`ac_submit`.`status` = 'AC') group by `ac_submit`.`acid`;

-- ----------------------------
-- View structure for ac_stucontest_with_cinfo_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `ac_stucontest_with_cinfo_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_stucontest_with_cinfo_userinfo` AS select `ac_stucontest`.`id` AS `id`,`ac_stucontest`.`cid` AS `cid`,`ac_stucontest`.`acid` AS `acid`,`ac_stucontest`.`t_rank` AS `t_rank`,`ac_stucontest`.`solve` AS `solve`,`ac_stucontest`.`diff` AS `diff`,`ac_stucontest`.`rating` AS `rating`,`ac_account_with_username`.`atcoder_id` AS `atcoder_id`,`ac_account_with_username`.`username` AS `username`,`ac_account_with_username`.`classname` AS `classname`,`ac_account_with_username`.`stu_no` AS `stu_no`,`ac_account_with_username`.`school` AS `school`,`ac_account_with_username`.`realname` AS `realname`,`ac_contest`.`name` AS `name`,`ac_contest`.`start_time_stamp` AS `start_time_stamp` from ((`ac_stucontest` left join `ac_account_with_username` on((`ac_stucontest`.`acid` = `ac_account_with_username`.`id`))) left join `ac_contest` on((`ac_contest`.`id` = `ac_stucontest`.`cid`)));

-- ----------------------------
-- View structure for ac_stucontest_with_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `ac_stucontest_with_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_stucontest_with_userinfo` AS select `ac_stucontest`.`id` AS `id`,`ac_stucontest`.`cid` AS `cid`,`ac_stucontest`.`acid` AS `acid`,`ac_stucontest`.`t_rank` AS `t_rank`,`ac_stucontest`.`solve` AS `solve`,`ac_stucontest`.`diff` AS `diff`,`ac_stucontest`.`rating` AS `rating`,`ac_account_with_username`.`atcoder_id` AS `atcoder_id`,`ac_account_with_username`.`username` AS `username`,`ac_account_with_username`.`classname` AS `classname`,`ac_account_with_username`.`stu_no` AS `stu_no`,`ac_account_with_username`.`school` AS `school`,`ac_account_with_username`.`realname` AS `realname` from (`ac_stucontest` left join `ac_account_with_username` on((`ac_stucontest`.`acid` = `ac_account_with_username`.`id`)));

-- ----------------------------
-- View structure for ac_submit_with_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `ac_submit_with_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ac_submit_with_userinfo` AS select `cs`.`sid` AS `sid`,`cs`.`cid` AS `cid`,`cs`.`acid` AS `acid`,`cs`.`q_index` AS `q_index`,`cs`.`submit_time` AS `submit_time`,`cs`.`status` AS `status`,`cs`.`is_after` AS `is_after`,`cs`.`language` AS `language`,`ac_account_with_username`.`atcoder_id` AS `atcoder_id`,`ac_account_with_username`.`username` AS `username`,`ac_account_with_username`.`realname` AS `realname`,`ac_contest`.`nickname` AS `nickname`,`ac_contest`.`name` AS `name` from ((`ac_submit` `cs` left join `ac_account_with_username` on((`cs`.`acid` = `ac_account_with_username`.`id`))) left join `ac_contest` on((`ac_contest`.`id` = `cs`.`cid`)));

-- ----------------------------
-- View structure for application_with_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `application_with_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `application_with_userinfo` AS select `application`.`id` AS `id`,`application`.`uid` AS `uid`,`application`.`time` AS `time`,`application`.`opertation` AS `opertation`,`application`.`parameter` AS `parameter`,`application`.`status` AS `status`,`application`.`admin_name` AS `admin_name`,`application`.`operation_time` AS `operation_time`,`application`.`school` AS `school`,`normal_user`.`username` AS `username`,`normal_user`.`stu_no` AS `stu_no`,`normal_user`.`classname` AS `classname`,`normal_user`.`year` AS `year`,`normal_user`.`realname` AS `realname`,`normal_user`.`status` AS `ustatus` from (`application` left join `normal_user` on((`application`.`uid` = `normal_user`.`id`)));

-- ----------------------------
-- View structure for cf_account_total_info
-- ----------------------------
DROP VIEW IF EXISTS `cf_account_total_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_account_total_info` AS select `src`.`username` AS `username`,`src`.`realname` AS `realname`,`src`.`classname` AS `classname`,`src`.`school` AS `school`,`src`.`status` AS `status`,`src`.`stu_no` AS `stu_no`,`src`.`year` AS `year`,`src`.`id` AS `id`,`src`.`uid` AS `uid`,`src`.`codeforces_id` AS `codeforces_id`,`src`.`rating` AS `rating`,`src`.`is_main` AS `is_main`,`src`.`is_using` AS `is_using`,`src`.`participate_time` AS `participate_time`,`cf_solve_count`.`solve` AS `solve`,`aft`.`solve` AS `after` from (((select `cawu`.`username` AS `username`,`cawu`.`realname` AS `realname`,`cawu`.`classname` AS `classname`,`cawu`.`school` AS `school`,`cawu`.`status` AS `status`,`cawu`.`stu_no` AS `stu_no`,`cawu`.`year` AS `year`,`cawu`.`id` AS `id`,`cawu`.`uid` AS `uid`,`cawu`.`codeforces_id` AS `codeforces_id`,`cawu`.`rating` AS `rating`,`cawu`.`is_main` AS `is_main`,`cawu`.`is_using` AS `is_using`,count(`cfstuc`.`id`) AS `participate_time` from (`cf_account_with_username` `cawu` left join `cf_stucontest` `cfstuc` on((`cfstuc`.`cfid` = `cawu`.`id`))) group by `cawu`.`id`) `src` left join `cf_solve_count` on((`src`.`id` = `cf_solve_count`.`cfid`))) left join (select `cf_after`.`cfid` AS `cfid`,count(distinct `cf_after`.`cid`,`cf_after`.`q_index`) AS `solve` from `cf_after` group by `cf_after`.`cfid`) `aft` on((`src`.`id` = `aft`.`cfid`)));

-- ----------------------------
-- View structure for cf_account_with_username
-- ----------------------------
DROP VIEW IF EXISTS `cf_account_with_username`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_account_with_username` AS select `normal_user`.`username` AS `username`,`normal_user`.`classname` AS `classname`,`normal_user`.`school` AS `school`,`normal_user`.`status` AS `status`,`normal_user`.`stu_no` AS `stu_no`,`normal_user`.`year` AS `year`,`normal_user`.`realname` AS `realname`,`cf_account`.`id` AS `id`,`cf_account`.`uid` AS `uid`,`cf_account`.`codeforces_id` AS `codeforces_id`,`cf_account`.`rating` AS `rating`,`cf_account`.`is_main` AS `is_main`,`cf_account`.`is_using` AS `is_using` from (`cf_account` left join `normal_user` on((`cf_account`.`uid` = `normal_user`.`id`)));

-- ----------------------------
-- View structure for cf_after
-- ----------------------------
DROP VIEW IF EXISTS `cf_after`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_after` AS select distinct `t1`.`sid` AS `sid`,`t1`.`cfid` AS `cfid`,`t1`.`cid` AS `cid`,`t1`.`q_index` AS `q_index` from `cf_submit` `t1` where ((`t1`.`status` = 'OK') and exists(select distinct `t2`.`cfid`,`t2`.`cid`,`t2`.`q_index` from `cf_submit` `t2` where ((`t2`.`status` = 'OK') and (`t2`.`is_after` = 0) and (`t1`.`cfid` = `t2`.`cfid`) and (`t1`.`cid` = `t2`.`cid`) and (`t1`.`q_index` = `t2`.`q_index`))) is false);

-- ----------------------------
-- View structure for cf_contest_with_participate
-- ----------------------------
DROP VIEW IF EXISTS `cf_contest_with_participate`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_contest_with_participate` AS select `cfc`.`cid` AS `cid`,`cfc`.`name` AS `name`,`cfc`.`type` AS `type`,`cfc`.`level` AS `level`,`cfc`.`start_time_stamp` AS `start_time_stamp`,`cfc`.`end_time_stamp` AS `end_time_stamp`,`cfc`.`duration` AS `duration`,`cfc`.`is_normal` AS `is_normal`,count(`cfsc`.`cfid`) AS `participate` from (`cf_contest` `cfc` left join `cf_stucontest` `cfsc` on((`cfsc`.`cid` = `cfc`.`cid`))) group by `cfc`.`cid`;

-- ----------------------------
-- View structure for cf_month_submit
-- ----------------------------
DROP VIEW IF EXISTS `cf_month_submit`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_month_submit` AS select `cf_account_total_info`.`username` AS `username`,`t_cnt`.`cfid` AS `cfid`,`t_cnt`.`ym_date` AS `ym_date`,`t_cnt`.`submit_cnt` AS `submit_cnt` from ((select `cf_submit_with_ym`.`cfid` AS `cfid`,`cf_submit_with_ym`.`ym_date` AS `ym_date`,count(0) AS `submit_cnt` from (select `cf_submit`.`sid` AS `sid`,`cf_submit`.`cid` AS `cid`,`cf_submit`.`cfid` AS `cfid`,`cf_submit`.`q_index` AS `q_index`,`cf_submit`.`submit_time` AS `submit_time`,`cf_submit`.`status` AS `status`,`cf_submit`.`is_after` AS `is_after`,`cf_submit`.`language` AS `language`,date_format(from_unixtime(`cf_submit`.`submit_time`),'%Y-%m') AS `ym_date` from `cf_submit`) `cf_submit_with_ym` group by `cf_submit_with_ym`.`cfid`,`cf_submit_with_ym`.`ym_date`) `t_cnt` left join `cf_account_total_info` on((`t_cnt`.`cfid` = `cf_account_total_info`.`id`)));

-- ----------------------------
-- View structure for cf_solve_count
-- ----------------------------
DROP VIEW IF EXISTS `cf_solve_count`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_solve_count` AS select `cf_submit`.`cfid` AS `cfid`,count(distinct `cf_submit`.`cfid`,`cf_submit`.`cid`,`cf_submit`.`q_index`) AS `solve` from (`cf_submit` left join `cf_account` on((`cf_submit`.`cfid` = `cf_account`.`id`))) where (`cf_submit`.`status` = 'OK') group by `cf_submit`.`cfid`;

-- ----------------------------
-- View structure for cf_stucontest_with_cinfo_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `cf_stucontest_with_cinfo_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_stucontest_with_cinfo_userinfo` AS select `cf_stucontest`.`id` AS `id`,`cf_stucontest`.`cid` AS `cid`,`cf_stucontest`.`cfid` AS `cfid`,`cf_stucontest`.`t_rank` AS `t_rank`,`cf_stucontest`.`solve` AS `solve`,`cf_stucontest`.`diff` AS `diff`,`cf_stucontest`.`rating` AS `rating`,`cf_account_with_username`.`codeforces_id` AS `codeforces_id`,`cf_account_with_username`.`username` AS `username`,`cf_account_with_username`.`classname` AS `classname`,`cf_account_with_username`.`stu_no` AS `stu_no`,`cf_account_with_username`.`school` AS `school`,`cf_account_with_username`.`realname` AS `realname`,`cf_contest`.`name` AS `name`,`cf_contest`.`start_time_stamp` AS `start_time_stamp`,`cf_account_with_username`.`is_main` AS `is_main` from ((`cf_stucontest` left join `cf_account_with_username` on((`cf_stucontest`.`cfid` = `cf_account_with_username`.`id`))) left join `cf_contest` on((`cf_stucontest`.`cid` = `cf_contest`.`cid`)));

-- ----------------------------
-- View structure for cf_stucontest_with_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `cf_stucontest_with_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_stucontest_with_userinfo` AS select `cf_stucontest`.`id` AS `id`,`cf_stucontest`.`cid` AS `cid`,`cf_stucontest`.`cfid` AS `cfid`,`cf_stucontest`.`t_rank` AS `t_rank`,`cf_stucontest`.`solve` AS `solve`,`cf_stucontest`.`diff` AS `diff`,`cf_stucontest`.`rating` AS `rating`,`cf_account_with_username`.`codeforces_id` AS `codeforces_id`,`cf_account_with_username`.`username` AS `username`,`cf_account_with_username`.`classname` AS `classname`,`cf_account_with_username`.`stu_no` AS `stu_no`,`cf_account_with_username`.`school` AS `school`,`cf_account_with_username`.`realname` AS `realname` from (`cf_stucontest` left join `cf_account_with_username` on((`cf_stucontest`.`cfid` = `cf_account_with_username`.`id`)));

-- ----------------------------
-- View structure for cf_submit_with_userinfo
-- ----------------------------
DROP VIEW IF EXISTS `cf_submit_with_userinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cf_submit_with_userinfo` AS select `cs`.`sid` AS `sid`,`cs`.`cid` AS `cid`,`cs`.`cfid` AS `cfid`,`cs`.`q_index` AS `q_index`,`cs`.`submit_time` AS `submit_time`,`cs`.`status` AS `status`,`cs`.`is_after` AS `is_after`,`cs`.`language` AS `language`,`cf_account_with_username`.`codeforces_id` AS `codeforces_id`,`cf_account_with_username`.`username` AS `username`,`cf_account_with_username`.`realname` AS `realname`,`cf_contest`.`name` AS `name` from ((`cf_submit` `cs` left join `cf_account_with_username` on((`cs`.`cfid` = `cf_account_with_username`.`id`))) left join `cf_contest` on((`cf_contest`.`cid` = `cs`.`cid`)));

-- ----------------------------
-- View structure for codeforces_ok_submit_with_tags
-- ----------------------------
DROP VIEW IF EXISTS `codeforces_ok_submit_with_tags`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `codeforces_ok_submit_with_tags` AS select `base_submit_info`.`sid` AS `sid`,`base_submit_info`.`uid` AS `uid`,`base_submit_info`.`q_index` AS `q_index`,`base_submit_info`.`cid` AS `cid`,`base_submit_info`.`first_submit_time` AS `first_submit_time`,`codeforces_problems_with_tags`.`tags` AS `tags`,`codeforces_problems_with_tags`.`difficulty` AS `difficulty` from ((select min(`ok_submit`.`sid`) AS `sid`,`cf_account_with_username`.`uid` AS `uid`,`ok_submit`.`q_index` AS `q_index`,`ok_submit`.`cid` AS `cid`,min(`ok_submit`.`submit_time`) AS `first_submit_time` from ((select `cf_submit`.`sid` AS `sid`,`cf_submit`.`cid` AS `cid`,`cf_submit`.`cfid` AS `cfid`,`cf_submit`.`q_index` AS `q_index`,`cf_submit`.`submit_time` AS `submit_time`,`cf_submit`.`status` AS `status`,`cf_submit`.`is_after` AS `is_after`,`cf_submit`.`language` AS `language` from `cf_submit` where (`cf_submit`.`status` = 'OK')) `ok_submit` left join `cf_account_with_username` on((`ok_submit`.`cfid` = `cf_account_with_username`.`id`))) group by `cf_account_with_username`.`uid`,`ok_submit`.`q_index`,`ok_submit`.`cid`) `base_submit_info` left join `codeforces_problems_with_tags` on(((`codeforces_problems_with_tags`.`cid` = `base_submit_info`.`cid`) and (`base_submit_info`.`q_index` = `codeforces_problems_with_tags`.`qindex`))));

-- ----------------------------
-- View structure for codeforces_problems_with_tags
-- ----------------------------
DROP VIEW IF EXISTS `codeforces_problems_with_tags`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `codeforces_problems_with_tags` AS select `codeforces_problems`.`id` AS `id`,`codeforces_problems`.`cid` AS `cid`,`codeforces_problems`.`qindex` AS `qindex`,`codeforces_problems`.`difficulty` AS `difficulty`,`codeforces_problems`.`name` AS `name`,group_concat(`problem_tag`.`tag_name` separator ',') AS `tags` from ((`codeforces_problems_tag_map` left join `codeforces_problems` on((`codeforces_problems`.`id` = `codeforces_problems_tag_map`.`codeforces_problem_id`))) left join `problem_tag` on((`codeforces_problems_tag_map`.`tag_id` = `problem_tag`.`id`))) group by `codeforces_problems`.`id`;

-- ----------------------------
-- View structure for future_contest
-- ----------------------------
DROP VIEW IF EXISTS `future_contest`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `future_contest` AS select `ac_contest`.`id` AS `id`,`ac_contest`.`name` AS `name`,`ac_contest`.`nickname` AS `nickname`,`ac_contest`.`start_time_stamp` AS `start_time_stamp`,`ac_contest`.`duration` AS `duration`,'ac' AS `type` from `ac_contest` where (`ac_contest`.`start_time_stamp` > unix_timestamp(now())) union select `cf_contest`.`cid` AS `id`,`cf_contest`.`name` AS `name`,NULL AS `nickname`,`cf_contest`.`start_time_stamp` AS `start_time_stamp`,`cf_contest`.`duration` AS `duration`,'cf' AS `type` from `cf_contest` where (`cf_contest`.`start_time_stamp` > unix_timestamp(now()));

-- ----------------------------
-- View structure for tag_user_detail
-- ----------------------------
DROP VIEW IF EXISTS `tag_user_detail`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `tag_user_detail` AS select `normal_user`.`id` AS `uid`,`normal_user`.`username` AS `username`,`normal_user`.`school` AS `school`,`normal_user`.`classname` AS `classname`,`normal_user`.`stu_no` AS `stu_no`,`normal_user`.`year` AS `year`,`normal_user`.`status` AS `status`,`normal_user`.`realname` AS `realname`,`tag`.`id` AS `tid`,`tag`.`name` AS `tname` from ((`tag_user_map` left join `normal_user` on((`tag_user_map`.`uid` = `normal_user`.`id`))) left join `tag` on((`tag_user_map`.`tid` = `tag`.`id`)));

SET FOREIGN_KEY_CHECKS = 1;

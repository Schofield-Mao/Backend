/*
Navicat MySQL Data Transfer
Source Server         : 本地
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : test1
Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001
Date: 2016-11-05 21:17:33
*/
use wb;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `sex`  varchar(16) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(11) NOT NULL COMMENT 'user_id',
  `role` varchar(32) NOT NULL COMMENT 'role',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `moment`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `moment` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'moment_id',
    `user_id` bigint(11) NOT NULL COMMENT 'user_id',
    `content` varchar(1024) NOT NULL COMMENT 'character content',
    `multipart_id` bigint(11) DEFAULT NULL COMMENT 'picture or video',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `reply`(
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'comment_id',
    `user_id` bigint(11) NOT NULL COMMENT 'user_id',
    `parent_id` bigint(11) NOT NULL COMMENT 'reply_parent',
    `content` varchar(1024) NOT NULL COMMENT 'content',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     KEY (`parent_id`)
)ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `multipart` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'multipart_id',
    `moment_id` bigint(11) DEFAULT NULL COMMENT 'picture or video',
    `link` varchar(256) NOT NULL COMMENT 'link',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     KEY (`moment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
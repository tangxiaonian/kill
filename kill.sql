/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50724
Source Host           : 127.0.0.1:3306
Source Database       : kill

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-03-22 18:37:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_id
-- ----------------------------
DROP TABLE IF EXISTS `tb_id`;
CREATE TABLE `tb_id` (
  `id` int(11) NOT NULL,
  `id_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_id
-- ----------------------------

-- ----------------------------
-- Table structure for tb_kill_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_kill_shop`;
CREATE TABLE `tb_kill_shop` (
  `k_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL,
  `counts` int(11) DEFAULT NULL COMMENT '可以被秒杀的商品数',
  `k_start_time` datetime DEFAULT NULL,
  `k_end_time` datetime DEFAULT NULL,
  `k_is_active` int(11) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`k_id`),
  KEY `s_id` (`s_id`),
  CONSTRAINT `tb_kill_shop_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `tb_shop` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='待秒杀的表';

-- ----------------------------
-- Records of tb_kill_shop
-- ----------------------------
INSERT INTO `tb_kill_shop` VALUES ('1', '1', '10', '2020-03-11 18:28:16', '2020-03-21 18:28:23', '1');
INSERT INTO `tb_kill_shop` VALUES ('2', '2', '10', '2020-03-19 18:29:00', '2020-03-25 18:29:04', '1');
INSERT INTO `tb_kill_shop` VALUES ('3', '3', '10', '2020-03-19 18:29:17', '2020-03-22 18:29:20', '1');
INSERT INTO `tb_kill_shop` VALUES ('4', '4', '10', '2020-03-21 18:41:11', '2020-03-22 18:41:26', '1');

-- ----------------------------
-- Table structure for tb_kill_success
-- ----------------------------
DROP TABLE IF EXISTS `tb_kill_success`;
CREATE TABLE `tb_kill_success` (
  `id` varchar(255) NOT NULL,
  `u_id` int(11) DEFAULT NULL,
  `k_id` int(11) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '秒杀结果: -1 无效，0成功(未付款)，1 已付款，2已取消',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `k_id` (`k_id`),
  KEY `u_id` (`u_id`),
  CONSTRAINT `tb_kill_success_ibfk_2` FOREIGN KEY (`k_id`) REFERENCES `tb_kill_shop` (`k_id`),
  CONSTRAINT `tb_kill_success_ibfk_3` FOREIGN KEY (`u_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀成功订单表';

-- ----------------------------
-- Records of tb_kill_success
-- ----------------------------

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `counts` varchar(255) DEFAULT NULL,
  `buyTime` datetime DEFAULT NULL,
  `isactive` varchar(255) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES ('1', 'Spring编程思想', '11111111', '100', '2020-03-20 18:22:16', '1');
INSERT INTO `tb_shop` VALUES ('2', 'Liunx入门', '22222222', '100', '2020-03-20 18:23:19', '1');
INSERT INTO `tb_shop` VALUES ('3', 'Java编程思想', '33333333', '100', '2020-03-20 18:24:00', '1');
INSERT INTO `tb_shop` VALUES ('4', 'SQL优化入门', '44444444', '100', '2020-03-20 18:24:24', '0');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'tang', '721000', '11111', '111');
INSERT INTO `tb_user` VALUES ('2', 'xiao', '1111', '11111', '1111');
INSERT INTO `tb_user` VALUES ('3', 'nian', '4444', '1111', '1111');
INSERT INTO `tb_user` VALUES ('4', 'laoshi', '222', '1111', '222');
INSERT INTO `tb_user` VALUES ('5', 'tttt', '5555', '7777', '333');

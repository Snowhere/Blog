/*
Navicat MySQL Data Transfer

Source Server         : Mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : map

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-08-10 01:24:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for poi
-- ----------------------------
DROP TABLE IF EXISTS `poi`;
CREATE TABLE `poi` (
  `int` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '兴趣点类型',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签',
  `typecode` varchar(255) DEFAULT NULL COMMENT '兴趣点类型编码',
  `biz_type` varchar(255) DEFAULT NULL COMMENT '行业类型',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `location` varchar(255) DEFAULT NULL COMMENT '经纬度',
  `tel` varchar(255) DEFAULT NULL,
  `adcode` varchar(255) DEFAULT NULL COMMENT '区域编码',
  `adname` varchar(255) DEFAULT NULL COMMENT '区域名称',
  `gridcode` varchar(255) DEFAULT NULL COMMENT '地理格id',
  `navi_poiid` varchar(255) DEFAULT NULL COMMENT '地图编号',
  `business_area` varchar(255) DEFAULT NULL COMMENT '所在商圈',
  `rating` varchar(255) DEFAULT NULL COMMENT '评分',
  `cost` varchar(255) DEFAULT NULL COMMENT '人均消费',
  `star` varchar(255) DEFAULT NULL,
  `hotel_ordering` varchar(255) DEFAULT NULL,
  `lowest_price` varchar(255) DEFAULT NULL,
  `url_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`int`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of poi
-- ----------------------------

/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : map

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-09-02 18:11:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leftlng` double DEFAULT NULL,
  `leftlat` double DEFAULT NULL,
  `rightlng` double DEFAULT NULL,
  `rightlat` double DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2804 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `first` varchar(255) DEFAULT NULL,
  `second` varchar(255) DEFAULT NULL,
  `third` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=865 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for poi
-- ----------------------------
DROP TABLE IF EXISTS `poi`;
CREATE TABLE `poi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poiid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '兴趣点类型',
  `tag` varchar(1200) DEFAULT NULL COMMENT '标签',
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
  `time` datetime DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `page` int(11) DEFAULT NULL,
  `typecode1` varchar(255) DEFAULT NULL,
  `typecode2` varchar(255) DEFAULT NULL,
  `typecode3` varchar(255) DEFAULT NULL,
  `weight` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `poiid` (`poiid`),
  KEY `typecode` (`typecode`),
  KEY `location` (`location`)
) ENGINE=InnoDB AUTO_INCREMENT=1178307 DEFAULT CHARSET=utf8;

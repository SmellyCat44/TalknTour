/*
Navicat MySQL Data Transfer

Source Server         : SQW
Source Server Version : 50514
Source Host           : localhost:3306
Source Database       : speechdemo

Target Server Type    : MYSQL
Target Server Version : 50514
File Encoding         : 65001

Date: 2023-07-30 22:46:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` varchar(20) NOT NULL,
  `admin_pwd` varchar(16) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', 'admin');

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `reivew_id` int(11) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `score` int(11) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `img1` varchar(400) DEFAULT NULL,
  `img2` varchar(400) DEFAULT NULL,
  `img3` varchar(400) DEFAULT NULL,
  `img4` varchar(400) DEFAULT NULL,
  `review_date` datetime NOT NULL,
  PRIMARY KEY (`reivew_id`),
  KEY `FK_give` (`user_id`),
  CONSTRAINT `FK_give` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(50) NOT NULL,
  `user_pwd` varchar(16) NOT NULL,
  `img` varchar(500) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for wish
-- ----------------------------
DROP TABLE IF EXISTS `wish`;
CREATE TABLE `wish` (
  `place_id` varchar(200) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `add_date` varchar(200) NOT NULL,
  PRIMARY KEY (`place_id`),
  KEY `FK_make` (`user_id`),
  CONSTRAINT `FK_make` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wish
-- ----------------------------

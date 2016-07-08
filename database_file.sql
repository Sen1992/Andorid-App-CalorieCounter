CREATE DATABASE  IF NOT EXISTS `id27315657` /*!40100 DEFAULT CHARACTER SET gbk */;
USE `id27315657`;
/*
Navicat MySQL Data Transfer

Source Server         : id27315657
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : fit5183

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-04-29 21:37:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for consume
-- ----------------------------
DROP TABLE IF EXISTS `consume`;
CREATE TABLE `consume` (
  `uid` int(11) NOT NULL,
  `fid` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`,`fid`,`datetime`),
  KEY `fid_idx` (`fid`),
  CONSTRAINT `fk_fid` FOREIGN KEY (`fid`) REFERENCES `food` (`fid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consume
-- ----------------------------
INSERT INTO `consume` VALUES ('1', '1', '1', '2016-02-13 07:25:30');
INSERT INTO `consume` VALUES ('1', '1', '2', '2016-02-13 17:40:30');
INSERT INTO `consume` VALUES ('1', '3', '2', '2016-02-13 12:12:40');
INSERT INTO `consume` VALUES ('1', '7', '3', '2016-02-21 08:10:23');
INSERT INTO `consume` VALUES ('1', '7', '3', '2016-02-21 11:10:23');
INSERT INTO `consume` VALUES ('1', '7', '3', '2016-02-21 18:10:23');
INSERT INTO `consume` VALUES ('1', '7', '23', '2016-04-24 03:07:14');
INSERT INTO `consume` VALUES ('1', '7', '3', '2016-04-28 03:29:50');
INSERT INTO `consume` VALUES ('1', '9', '2', '2016-04-28 03:30:16');
INSERT INTO `consume` VALUES ('1', '10', '2', '2016-02-18 08:10:23');
INSERT INTO `consume` VALUES ('1', '13', '2', '2016-02-18 18:10:23');
INSERT INTO `consume` VALUES ('1', '15', '1', '2016-02-18 12:12:40');
INSERT INTO `consume` VALUES ('1', '23', '3', '2016-04-28 03:29:32');
INSERT INTO `consume` VALUES ('1', '25', '3', '2016-04-24 02:11:13');
INSERT INTO `consume` VALUES ('1', '25', '2', '2016-04-28 03:30:00');
INSERT INTO `consume` VALUES ('1', '26', '3', '2016-04-24 02:16:41');
INSERT INTO `consume` VALUES ('1', '26', '3', '2016-04-28 03:29:40');
INSERT INTO `consume` VALUES ('2', '3', '2', '2016-02-18 07:25:30');
INSERT INTO `consume` VALUES ('2', '7', '1', '2016-02-18 12:12:40');
INSERT INTO `consume` VALUES ('2', '8', '3', '2016-02-18 17:40:30');
INSERT INTO `consume` VALUES ('2', '24', '2', '2016-02-22 13:32:11');
INSERT INTO `consume` VALUES ('3', '7', '1', '2016-03-01 17:57:30');
INSERT INTO `consume` VALUES ('3', '11', '2', '2016-03-01 06:57:30');
INSERT INTO `consume` VALUES ('3', '23', '1', '2016-03-01 11:57:30');
INSERT INTO `consume` VALUES ('4', '2', '3', '2016-03-03 11:30:30');
INSERT INTO `consume` VALUES ('4', '5', '2', '2016-03-03 18:00:30');
INSERT INTO `consume` VALUES ('4', '7', '12', '2016-04-23 11:11:11');
INSERT INTO `consume` VALUES ('4', '7', '5', '2016-09-16 11:11:11');
INSERT INTO `consume` VALUES ('4', '10', '1', '2016-03-03 07:57:30');
INSERT INTO `consume` VALUES ('5', '4', '5', '2016-04-24 04:27:52');
INSERT INTO `consume` VALUES ('5', '7', '4', '2016-04-24 04:27:39');
INSERT INTO `consume` VALUES ('5', '8', '1', '2016-03-20 05:57:30');
INSERT INTO `consume` VALUES ('5', '19', '2', '2016-03-20 11:20:30');
INSERT INTO `consume` VALUES ('5', '24', '1', '2016-04-24 04:27:27');
INSERT INTO `consume` VALUES ('5', '25', '1', '2016-03-20 17:10:30');
INSERT INTO `consume` VALUES ('5', '26', '3', '2016-04-24 04:27:09');
INSERT INTO `consume` VALUES ('6', '7', '2', '2016-09-17 04:11:35');
INSERT INTO `consume` VALUES ('6', '7', '2', '2016-09-17 11:11:09');
INSERT INTO `consume` VALUES ('24', '4', '5', '2016-04-27 03:25:20');
INSERT INTO `consume` VALUES ('24', '5', '3', '2016-04-27 03:24:37');
INSERT INTO `consume` VALUES ('24', '7', '7', '2016-04-27 03:24:53');
INSERT INTO `consume` VALUES ('24', '25', '3', '2016-04-27 03:25:05');
INSERT INTO `consume` VALUES ('24', '25', '3', '2016-04-28 03:16:28');
INSERT INTO `consume` VALUES ('27', '5', '2', '2016-04-28 08:41:05');
INSERT INTO `consume` VALUES ('27', '8', '2', '2016-04-28 08:42:13');
INSERT INTO `consume` VALUES ('27', '11', '1', '2016-04-28 08:41:56');
INSERT INTO `consume` VALUES ('27', '16', '1', '2016-04-28 08:44:20');
INSERT INTO `consume` VALUES ('27', '18', '2', '2016-04-28 08:41:34');
INSERT INTO `consume` VALUES ('27', '23', '3', '2016-04-28 08:40:48');
INSERT INTO `consume` VALUES ('27', '29', '3', '2016-04-28 08:45:05');

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) NOT NULL,
  `calorie` int(11) NOT NULL,
  `fat` int(11) NOT NULL,
  `serving` varchar(10) NOT NULL,
  `category` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES ('1', 'Turkey', '32', '2', '1_slice', 'meat');
INSERT INTO `food` VALUES ('2', 'Applesauce', '52', '0', '1/2 cup', 'Applesauce');
INSERT INTO `food` VALUES ('3', 'Avocado', '121', '11', '1/2 cup', 'fruit');
INSERT INTO `food` VALUES ('4', 'Banana', '96', '0', '1 each', 'fruit');
INSERT INTO `food` VALUES ('5', 'Barley', '96', '0', '1/2 cup', 'meal');
INSERT INTO `food` VALUES ('6', 'chicken', '245', '6', '1 med', 'meat');
INSERT INTO `food` VALUES ('7', 'Beef', '166', '10', '1/2 cup', 'meat');
INSERT INTO `food` VALUES ('8', 'Beets', '37', '0', '1/2 cup', 'veggies');
INSERT INTO `food` VALUES ('9', 'Cakefat-free', '106', '1', '1 pc', 'cake');
INSERT INTO `food` VALUES ('10', 'chicken', '245', '6', '1 med', 'meat');
INSERT INTO `food` VALUES ('11', 'Candycaramels', '92', '2', '3 pcs', 'snack');
INSERT INTO `food` VALUES ('12', 'Carrots', '35', '0', '1/2 cup', 'veggies');
INSERT INTO `food` VALUES ('13', 'Caviar', '40', '3', '1 Tbsp', 'other');
INSERT INTO `food` VALUES ('14', 'Neufchatel', '75', '7', '2 Tbps', 'other');
INSERT INTO `food` VALUES ('15', 'Ricotta', '214', '16', '1/2 cup', 'other');
INSERT INTO `food` VALUES ('16', 'Dirty rice', '271', '5', '1 cup', 'meal');
INSERT INTO `food` VALUES ('17', 'Egg McMuffin', '291', '13', '1 each', 'meat');
INSERT INTO `food` VALUES ('18', 'Figs, dried', '143', '1', '3 small', 'fruit');
INSERT INTO `food` VALUES ('19', 'Fudge syrup', '74', '3', '1 Tbps', 'snake');
INSERT INTO `food` VALUES ('20', 'Ground lamb', '235', '17', '1 cup', 'meat');
INSERT INTO `food` VALUES ('21', 'Rutabaga', '33', '0', '1/2 cup', 'veggies');
INSERT INTO `food` VALUES ('22', 'Parsnips', '63', '0', '1/2 cup', 'veggies');
INSERT INTO `food` VALUES ('23', 'Oxtail', '211', '3', '1 Tbsp', 'drink');
INSERT INTO `food` VALUES ('24', 'Millet', '145', '2', '1/2 cup', 'meal');
INSERT INTO `food` VALUES ('25', 'hot dog', '108', '8', '1 each', 'meat');
INSERT INTO `food` VALUES ('26', 'Lemon juice', '3', '0', '1 Tbps', 'drink');
INSERT INTO `food` VALUES ('27', 'Lard', '116', '13', '1 Tbps', 'other');
INSERT INTO `food` VALUES ('28', 'Heart, beef', '149', '5', '3 oz', 'meat');
INSERT INTO `food` VALUES ('29', 'Big Mac', '515', '24', '1 each', 'bread');
INSERT INTO `food` VALUES ('30', 'Ham hocks', '109', '5', '1 pc', 'bread');
INSERT INTO `food` VALUES ('31', 'peanut butter', '171', '9', '2 pcs', 'other');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `uid` int(11) NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_calorie` double NOT NULL,
  `aim_calorie` double NOT NULL,
  `remaining` double NOT NULL,
  `consume_calorie` double NOT NULL,
  `total_steps` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`datetime`),
  CONSTRAINT `fkr_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES ('1', '2016-02-13 00:00:00', '432', '300', '2849.647', '338', '12000');
INSERT INTO `report` VALUES ('1', '2016-04-20 00:00:00', '300', '200', '100', '2000', '10000');
INSERT INTO `report` VALUES ('1', '2016-04-21 00:00:00', '200', '200', '100', '3500', '10000');
INSERT INTO `report` VALUES ('1', '2016-04-22 00:00:00', '127', '0', '0', '1456', '0');
INSERT INTO `report` VALUES ('1', '2016-04-23 00:00:00', '355', '55555', '0', '1000', '5380');
INSERT INTO `report` VALUES ('1', '2016-04-24 00:00:00', '267', '0', '0', '2134', '0');
INSERT INTO `report` VALUES ('1', '2016-04-25 00:00:00', '123', '0', '0', '2500', '0');
INSERT INTO `report` VALUES ('1', '2016-04-26 00:00:00', '535', '5000', '0', '1900', '0');
INSERT INTO `report` VALUES ('1', '2016-04-27 00:00:00', '120', '12465', '110', '710', '0');
INSERT INTO `report` VALUES ('1', '2016-04-28 00:00:00', '0', '160', '0', '0', '7300');
INSERT INTO `report` VALUES ('2', '2016-02-18 00:00:00', '2905.588', '300', '2086.588', '519', '9000');
INSERT INTO `report` VALUES ('3', '2016-03-01 00:00:00', '2754.307', '300', '1893.3069999999998', '561', '13000');
INSERT INTO `report` VALUES ('4', '2016-03-01 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-03-03 00:00:00', '1923.876', '123', '1030.876', '593', '100');
INSERT INTO `report` VALUES ('4', '2016-03-18 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-03-19 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-04-12 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-04-22 00:00:00', '0', '123451', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-04-23 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-07-11 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('4', '2016-07-12 00:00:00', '10', '0', '110', '10', '10');
INSERT INTO `report` VALUES ('4', '2016-07-27 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('5', '2016-03-20 00:00:00', '2322.643', '20000', '-17970.357', '293', '10000');
INSERT INTO `report` VALUES ('5', '2016-04-23 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('5', '2016-04-24 00:00:00', '0', '500', '0', '0', '95993');
INSERT INTO `report` VALUES ('5', '2016-04-28 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('16', '2016-04-22 00:00:00', '0', '1992', '0', '0', '0');
INSERT INTO `report` VALUES ('17', '2016-04-22 00:00:00', '0', '123456', '0', '0', '0');
INSERT INTO `report` VALUES ('18', '2016-04-22 00:00:00', '0', '23124', '0', '0', '0');
INSERT INTO `report` VALUES ('19', '2016-04-22 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('20', '2016-04-23 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('21', '2016-04-23 00:00:00', '0', '567890', '0', '0', '1546');
INSERT INTO `report` VALUES ('22', '2016-04-23 00:00:00', '0', '1000', '0', '0', '5368');
INSERT INTO `report` VALUES ('23', '2016-04-26 00:00:00', '0', '1000', '0', '0', '0');
INSERT INTO `report` VALUES ('23', '2016-04-28 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('24', '2016-04-26 00:00:00', '0', '1000', '0', '0', '9000');
INSERT INTO `report` VALUES ('24', '2016-04-27 00:00:00', '1200', '1500', '0', '2000', '7500');
INSERT INTO `report` VALUES ('24', '2016-04-28 00:00:00', '0', '500', '0', '0', '12099');
INSERT INTO `report` VALUES ('24', '2016-04-29 00:00:00', '0', '300', '0', '0', '0');
INSERT INTO `report` VALUES ('25', '2016-04-28 00:00:00', '0', '3000', '0', '0', '0');
INSERT INTO `report` VALUES ('26', '2016-04-28 00:00:00', '0', '0', '0', '0', '0');
INSERT INTO `report` VALUES ('27', '2016-04-28 00:00:00', '0', '500', '0', '0', '9800');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) NOT NULL,
  `age` int(11) NOT NULL,
  `height` double NOT NULL,
  `weight` double NOT NULL,
  `gender` varchar(6) NOT NULL,
  `level_of_activity` char(1) NOT NULL,
  `step_per_mile` int(11) NOT NULL DEFAULT '2200',
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Tam', '1', '1', '1', 'femal', '4', '4000', 'Tom123', 'Tom1992');
INSERT INTO `user` VALUES ('2', 'Jack', '30', '175', '90', 'male', '2', '2100', 'Jack123', 'Jack123');
INSERT INTO `user` VALUES ('3', 'Mary', '27', '169', '54', 'female', '5', '2200', 'Marry123', 'Marry123');
INSERT INTO `user` VALUES ('4', 'Alex', '52', '160', '65', 'male', '1', '1800', 'Alex123', 'Alex123');
INSERT INTO `user` VALUES ('5', 'Emliy', '18', '170', '55', 'female', '3', '2200', 'Emily123', 'Emily123');
INSERT INTO `user` VALUES ('6', 'sam', '24', '172.6', '77.5', 'male', '2', '2300', 'sam123', 'sam123');
INSERT INTO `user` VALUES ('7', '1', '11', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `user` VALUES ('8', 'sen', '19', '123', '21', 'male', '3', '1234', '12341', '23213412');
INSERT INTO `user` VALUES ('9', 'aaa', '15', '123', '123', 'male', '3', '1234', 'seds', 'sdasdsad');
INSERT INTO `user` VALUES ('10', 'qqq', '12', '123', '123', 'male', '1', '1234', 'useadsa', '234132314');
INSERT INTO `user` VALUES ('11', 'wangsen', '34', '190', '60', 'male', '4', '3411', 'senada', '12346');
INSERT INTO `user` VALUES ('12', 'Hunter', '12', '197', '98', 'female', '4', '3000', 'Hunter123', '19920510');
INSERT INTO `user` VALUES ('13', 'sen', '19', '180', '123', 'male', '1', '1234', 'sen123', '1233');
INSERT INTO `user` VALUES ('14', 'wanw1', '12', '190', '78', 'female', '1', '123123', '12311', '3123');
INSERT INTO `user` VALUES ('15', 'new1', '11', '123', '43', 'male', '1', '1234', '123456', '523123');
INSERT INTO `user` VALUES ('16', 'zhuguocheng', '24', '179', '80', 'male', '2', '1234', '19920510', '19920510');
INSERT INTO `user` VALUES ('17', 'wanghao', '12', '177', '62', 'male', '3', '2900', 'kaiyan', '19920510');
INSERT INTO `user` VALUES ('18', '12345667', '12', '124', '98', 'male', '1', '1234', '12345', '12345');
INSERT INTO `user` VALUES ('19', 'SSSAM', '14', '178', '67', 'female', '3', '1234', 'zxcv', 'zxcv');
INSERT INTO `user` VALUES ('20', 'LiMing', '24', '168', '57', 'female', '2', '1500', 'L1992', '1992');
INSERT INTO `user` VALUES ('21', 'WH', '18', '177', '67', 'male', '4', '1234', 'WH1992', '123456');
INSERT INTO `user` VALUES ('22', 'ZYP', '12', '167', '60', 'male', '3', '2000', 'ZYP123', '123');
INSERT INTO `user` VALUES ('23', 'Bruce', '24', '190', '90', 'male', '2', '2500', 'monash', '1992');
INSERT INTO `user` VALUES ('24', 'wanghao', '32', '175', '67', 'male', '2', '3000', 'henter', '1992');
INSERT INTO `user` VALUES ('25', 'Garnet', '32', '183', '79', 'male', '4', '2800', 'monash2015', '123456789');
INSERT INTO `user` VALUES ('26', 'TEST', '30', '190', '89', 'male', '4', '1999', 'test', '1992');
INSERT INTO `user` VALUES ('27', 'WangSen', '24', '178', '71', 'male', '3', '2200', '627989472', '1992');

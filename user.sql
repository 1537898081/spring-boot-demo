/*
 Navicat Premium Data Transfer

 Source Server         : 测试
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : school

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 11/01/2019 16:48:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', NULL);
INSERT INTO `user` VALUES (2, '李四', NULL);
INSERT INTO `user` VALUES (3, '王五', NULL);
INSERT INTO `user` VALUES (4, '阿道夫', NULL);
INSERT INTO `user` VALUES (5, '策4', NULL);
INSERT INTO `user` VALUES (6, '册3', NULL);
INSERT INTO `user` VALUES (7, '测2', NULL);
INSERT INTO `user` VALUES (8, '测1', NULL);
INSERT INTO `user` VALUES (9, '嗷嗷2', NULL);
INSERT INTO `user` VALUES (10, '啊啊4', NULL);
INSERT INTO `user` VALUES (22, '测试123123123123', NULL);

SET FOREIGN_KEY_CHECKS = 1;

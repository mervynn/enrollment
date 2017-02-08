/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : baoming

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-08-23 11:46:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tc_code
-- ----------------------------
DROP TABLE IF EXISTS `tc_code`;
CREATE TABLE `tc_code` (
  `code` varchar(3) NOT NULL COMMENT '类型代码',
  `sort` varchar(3) NOT NULL COMMENT '排序',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `english_name` varchar(64) DEFAULT NULL COMMENT '英文名称',
  `type` varchar(64) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`code`,`sort`),
  KEY `IDX_PK_TC_CODE_CODE` (`code`),
  KEY `IDX_PK_TC_CODE_SORT` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='码表';

-- ----------------------------
-- Records of tc_code
-- ----------------------------
INSERT INTO `tc_code` VALUES ('0', '0', '2', '说明（常量名称0：关闭;1:只对旧生;2全部，注意：改变本条数据的常量名称会清空当前阶段购课车和订单[您可到相应历史表中查看])', '招生阶段');
INSERT INTO `tc_code` VALUES ('1', '0', '否', 'no', '是否');
INSERT INTO `tc_code` VALUES ('1', '1', '是', 'yes', '是否');
INSERT INTO `tc_code` VALUES ('10', '1', '1471617938833000', 'Teacher_role_id', '授课老师角色ID');
INSERT INTO `tc_code` VALUES ('10', '2', '123456', 'Teacher_default_password', '授课老师默认登陆密码');
INSERT INTO `tc_code` VALUES ('11', '0', '待审核', 'todo', '审批状态');
INSERT INTO `tc_code` VALUES ('11', '1', '审核成功', 'succss', '审批状态');
INSERT INTO `tc_code` VALUES ('11', '2', '审核未通过', 'failed', '审批状态');
INSERT INTO `tc_code` VALUES ('2', '0', '下架', 'down', '上下架状态');
INSERT INTO `tc_code` VALUES ('2', '1', '上架', 'up', '上下架状态');
INSERT INTO `tc_code` VALUES ('3', '0', '新建学期', 'new', '学期状态');
INSERT INTO `tc_code` VALUES ('3', '1', '上学期', 'lastterm', '学期状态');
INSERT INTO `tc_code` VALUES ('3', '2', '其他学期', 'ohters', '学期状态');
INSERT INTO `tc_code` VALUES ('4', '0', '男', 'male', '性别');
INSERT INTO `tc_code` VALUES ('4', '1', '女', 'female', '性别');
INSERT INTO `tc_code` VALUES ('5', '0', '幼儿园', 'little', '学校类型');
INSERT INTO `tc_code` VALUES ('5', '1', '小学', 'small', '学校类型');
INSERT INTO `tc_code` VALUES ('5', '2', '初中', 'junior', '学校类型');
INSERT INTO `tc_code` VALUES ('5', '3', '高中', 'senior', '学校类型');
INSERT INTO `tc_code` VALUES ('5', '4', '其他', 'others', '学校类型');
INSERT INTO `tc_code` VALUES ('6', '1', '已提交', 'submitted', '订单状态');
INSERT INTO `tc_code` VALUES ('6', '2', '已付款', 'paid', '订单状态');
INSERT INTO `tc_code` VALUES ('6', '3', '已取消', 'cancel', '订单状态');
INSERT INTO `tc_code` VALUES ('7', '0', '身份证', 'idcard', '证件类型');
INSERT INTO `tc_code` VALUES ('7', '1', '其他', 'others', '证件类型');
INSERT INTO `tc_code` VALUES ('8', '1', '一年', 'lv1', '年级');
INSERT INTO `tc_code` VALUES ('8', '2', '二年', 'lv2', '年级');
INSERT INTO `tc_code` VALUES ('8', '3', '三年', 'lv3', '年级');
INSERT INTO `tc_code` VALUES ('8', '4', '四年', 'lv4', '年级');
INSERT INTO `tc_code` VALUES ('8', '5', '五年', 'lv5', '年级');
INSERT INTO `tc_code` VALUES ('8', '6', '六年', 'lv6', '年级');
INSERT INTO `tc_code` VALUES ('9', '0', 'H5内容页', 'html5Conent', '内容类型');

-- ----------------------------
-- Table structure for th_last_class
-- ----------------------------
DROP TABLE IF EXISTS `th_last_class`;
CREATE TABLE `th_last_class` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `code` varchar(10) DEFAULT NULL COMMENT '班级代码',
  `min_age` varchar(6) DEFAULT NULL COMMENT '限制最小年龄',
  `is_only_old_student` varchar(6) DEFAULT NULL COMMENT '是否只可以旧生报名',
  `target_group` varchar(50) DEFAULT NULL COMMENT '招生对象',
  `limited_amount` varchar(10) DEFAULT NULL COMMENT '招生人数',
  `default_amount` varchar(10) DEFAULT NULL COMMENT '预设名额',
  `remain_amount` varchar(10) DEFAULT NULL COMMENT '剩余人数',
  `paid_amount` varchar(10) DEFAULT NULL COMMENT '已付款人数',
  `status` varchar(3) DEFAULT NULL COMMENT '上下架状态',
  `tuition_fee` varchar(10) DEFAULT NULL COMMENT '学费',
  `class_time_week` varchar(50) DEFAULT NULL COMMENT '上课时间（星期）',
  `class_time_begin` varchar(50) DEFAULT NULL COMMENT '商品图片1',
  `class_time_end` varchar(50) DEFAULT NULL COMMENT '商品图片1',
  `picture` varchar(1000) DEFAULT NULL COMMENT '乐器或老师照片',
  `class_type_id` varchar(20) DEFAULT NULL COMMENT '课程类别',
  `teacher_id` varchar(20) DEFAULT NULL COMMENT '授课老师id',
  `classroom_id` varchar(20) DEFAULT NULL COMMENT '教室id',
  `term_id` varchar(20) DEFAULT NULL COMMENT '学期id',
  `lock` varchar(1) DEFAULT NULL COMMENT '乐观锁标识(更新，删除时使用)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_COMMODITY_ID` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史班级表';

-- ----------------------------
-- Records of th_last_class
-- ----------------------------
INSERT INTO `th_last_class` VALUES ('1470938179177000', '1', '1', '0', '1', '10', '1', '0', '1', '1', '100', '1', '9:00', '11:00', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2590/131/1733243901/459382/c9a8808b/5747f1b9Nca4b2aa6.jpg!q70.jpg', '1471023933664006', '1471028944881002', '1471028944881009', '1471028496235000', '0', 'admin', '2016-08-12 02:18:01', 'admin', '2016-08-22 05:23:15', '1星空琴行 成人钢琴培训班 钢琴入门 精品钢琴课（50节）');
INSERT INTO `th_last_class` VALUES ('1470938179177001', '2', '2', '0', '2', '10', '2', '4', '2', '1', '200', '2', '2', '2', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471023933664002', '1471028944881002', '1471028944881007', '1471028496235000', '0', 'admin', '2016-08-12 02:22:46', 'admin', '2016-08-22 05:23:15', '星空琴行 钢琴中级 精品钢琴课（50节）');
INSERT INTO `th_last_class` VALUES ('1470938179177002', '3', '3', '1', '3', '10', '3', '6', '3', '1', '3', '3', '3', '3', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2932/25/934876384/449099/2a564c1a/576d08f0Nb6d2a2b1.jpg!q70.jpg', '1471023933664005', '1471028944881003', '1471028944881006', '1471028496235000', '0', 'admin', '2016-08-12 02:24:49', 'admin', '2016-08-22 05:23:15', '星空琴行 10级强化');
INSERT INTO `th_last_class` VALUES ('1470938179177003', '4', '4', '0', '4', '20', '4', '11', '4', '1', '4', '4', '4', '4', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t1516/151/759511215/178960/6b02dad5/55a9b81aN8d2d5d15.jpg!q70.jpg', '1471023933664003', '1471028944881002', '1471028944881007', '1471028496235000', '0', 'admin', '2016-08-12 02:25:29', 'admin', '2016-08-22 05:23:15', '星空琴行 成人吉他 买一送一');
INSERT INTO `th_last_class` VALUES ('1470938179177004', '5', '5', '0', '5', '10', '5', '3', '5', '1', '5', '5', '5', '5', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2329/19/2071515288/261530/e2e01435/56974da6N41579b0c.jpg!q70.jpg', '1471023933664005', '1471028944881003', '1471028944881005', '1471028496235000', '0', 'admin', '2016-08-12 02:26:16', 'admin', '2016-08-22 05:23:15', '内部交流课程 只限VIP 哦 (木笛9级)');
INSERT INTO `th_last_class` VALUES ('1471458784422003', '123456', '1', '0', '1', '10', '1', '4', '', '1', '11', '1', '1', '1', 'http://localhost:8080\\upload\\20160819\\1471588741672.jpg', '1471023933664006', '1471028944881002', '1471028944881007', '1471028496235000', '0', 'admin', '2016-08-18 02:49:02', 'admin', '2016-08-22 05:23:15', '');
INSERT INTO `th_last_class` VALUES ('1471644901259000', '1', '8', '1', '8-15岁', '30', '5', '18', '', '1', '320', '1', '14:00', '16:00', 'http://localhost:8080\\upload\\20160820\\1471645037790.jpg', '1471023933664006', '1471028944881004', '1471029273331000', '1471028496235000', '0', 'admin', '2016-08-20 06:21:49', 'admin', '2016-08-22 05:23:15', '艺术体操~~ (20节)');

-- ----------------------------
-- Table structure for th_last_class_student
-- ----------------------------
DROP TABLE IF EXISTS `th_last_class_student`;
CREATE TABLE `th_last_class_student` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `class_id` varchar(20) DEFAULT NULL COMMENT '班级id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TT_SHOPPING_CART_ID` (`id`) USING BTREE,
  KEY `IDX_TT_SHOPPING_CART_UID` (`student_id`) USING BTREE,
  KEY `IDX_TT_SHOPPING_CART_COMMODITY_NO` (`class_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级学生关联历史表';

-- ----------------------------
-- Records of th_last_class_student
-- ----------------------------
INSERT INTO `th_last_class_student` VALUES ('1471889190425001', '1470938179177003', '1471458784422001', 'admin', null, 'admin', null, null);
INSERT INTO `th_last_class_student` VALUES ('1471889803146000', '1470938179177004', '1471458784422001', 'admin', null, 'admin', null, null);
INSERT INTO `th_last_class_student` VALUES ('1471889803146001', '1471458784422003', '1471458784422001', 'admin', null, 'admin', null, null);

-- ----------------------------
-- Table structure for tm_account
-- ----------------------------
DROP TABLE IF EXISTS `tm_account`;
CREATE TABLE `tm_account` (
  `id` varchar(20) NOT NULL COMMENT '流水di',
  `phonenumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `defult_student_id` varchar(20) DEFAULT NULL COMMENT '默认学生id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生账号表';

-- ----------------------------
-- Records of tm_account
-- ----------------------------
INSERT INTO `tm_account` VALUES ('1471102211862000', '3', '3', '1471099706035002', null, '2016-08-13 23:36:20', 'admin', '2016-08-22 23:28:10', '');
INSERT INTO `tm_account` VALUES ('1471102211862001', '19866665555', 'asdfasdf', null, null, '2016-08-13 23:38:45', null, '2016-08-13 23:38:45', null);
INSERT INTO `tm_account` VALUES ('1471228224262000', '1', '1', '1471232879625000', 'admin', '2016-08-15 10:31:43', 'admin', '2016-08-23 11:25:00', '');
INSERT INTO `tm_account` VALUES ('1471743314619000', '2', '2', '1471753464994004', 'admin', '2016-08-21 09:35:41', 'admin', '2016-08-21 12:29:23', '1');
INSERT INTO `tm_account` VALUES ('1471915232900000', '13816634559', 'hmw30423', '1471915232900001', null, '2016-08-23 10:06:41', null, '2016-08-23 10:07:47', null);

-- ----------------------------
-- Table structure for tm_class
-- ----------------------------
DROP TABLE IF EXISTS `tm_class`;
CREATE TABLE `tm_class` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `code` varchar(10) DEFAULT NULL COMMENT '班级代码',
  `min_age` varchar(6) DEFAULT NULL COMMENT '限制最小年龄',
  `is_only_old_student` varchar(6) DEFAULT NULL COMMENT '是否只可以旧生报名',
  `target_group` varchar(50) DEFAULT NULL COMMENT '招生对象',
  `limited_amount` varchar(10) DEFAULT NULL COMMENT '招生人数',
  `default_amount` varchar(10) DEFAULT NULL COMMENT '预设名额',
  `remain_amount` varchar(10) DEFAULT NULL COMMENT '剩余人数',
  `paid_amount` varchar(10) DEFAULT NULL COMMENT '已付款人数',
  `status` varchar(3) DEFAULT NULL COMMENT '上下架状态',
  `tuition_fee` varchar(10) DEFAULT NULL COMMENT '学费',
  `class_time_week` varchar(50) DEFAULT NULL COMMENT '上课时间（星期）',
  `class_time_begin` varchar(50) DEFAULT NULL COMMENT '上课时间',
  `class_time_end` varchar(50) DEFAULT NULL COMMENT '下课时间',
  `picture` varchar(1000) DEFAULT NULL COMMENT '乐器或老师照片',
  `class_type_id` varchar(20) DEFAULT NULL COMMENT '课程类别',
  `teacher_id` varchar(20) DEFAULT NULL COMMENT '授课老师id',
  `classroom_id` varchar(20) DEFAULT NULL COMMENT '教室id',
  `term_id` varchar(20) DEFAULT NULL COMMENT '学期id',
  `lock` varchar(1) DEFAULT NULL COMMENT '乐观锁标识(更新，删除时使用)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_COMMODITY_ID` (`id`),
  KEY `IDX_FOR_TYPE_ID` (`class_type_id`) USING BTREE,
  KEY `IDX_FOR_TEACHER_ID` (`teacher_id`) USING BTREE,
  KEY `IDX_FOR_CLASSROOM_ID` (`classroom_id`) USING BTREE,
  KEY `IDX_FOR_TERM_ID` (`term_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of tm_class
-- ----------------------------
INSERT INTO `tm_class` VALUES ('0', '1', '1', '0', '1', '10', '1', '0', '1', '1', '100', '1', '9:00', '11:00', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2590/131/1733243901/459382/c9a8808b/5747f1b9Nca4b2aa6.jpg!q70.jpg', '1471023933664006', '1471028944881002', '1471028944881009', '1471028496235000', '0', 'admin', '2016-08-12 02:18:01', 'admin', '2016-08-22 05:23:15', '1星空琴行 成人钢琴培训班 钢琴入门 精品钢琴课（50节）');
INSERT INTO `tm_class` VALUES ('1', '2', '2', '0', '2', '10', '2', '1', '2', '1', '200', '2', '2', '2', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471023933664002', '1471028944881002', '1471028944881007', '1471028496235000', '0', 'admin', '2016-08-12 02:22:46', 'admin', '2016-08-22 05:23:15', '星空琴行 钢琴中级 精品钢琴课（50节）');
INSERT INTO `tm_class` VALUES ('2', '3', '3', '1', '3', '10', '3', '3', '3', '1', '3', '3', '3', '3', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2932/25/934876384/449099/2a564c1a/576d08f0Nb6d2a2b1.jpg!q70.jpg', '1471023933664005', '1471028944881003', '1471028944881006', '1471028496235000', '0', 'admin', '2016-08-12 02:24:49', 'admin', '2016-08-22 05:23:15', '星空琴行 10级强化');
INSERT INTO `tm_class` VALUES ('3', '4', '4', '0', '4', '20', '4', '11', '4', '1', '4', '4', '4', '4', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t1516/151/759511215/178960/6b02dad5/55a9b81aN8d2d5d15.jpg!q70.jpg', '1471023933664003', '1471028944881002', '1471028944881007', '1471028496235000', '0', 'admin', '2016-08-12 02:25:29', 'admin', '2016-08-22 05:23:15', '星空琴行 成人吉他 买一送一');
INSERT INTO `tm_class` VALUES ('4', '5', '5', '0', '5', '10', '5', '3', '5', '1', '5', '5', '5', '5', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2329/19/2071515288/261530/e2e01435/56974da6N41579b0c.jpg!q70.jpg', '1471023933664005', '1471028944881003', '1471028944881005', '1471028496235000', '0', 'admin', '2016-08-12 02:26:16', 'admin', '2016-08-22 05:23:15', '内部交流课程 只限VIP 哦 (木笛9级)');
INSERT INTO `tm_class` VALUES ('5', '123456', '1', '0', '1', '10', '1', '3', '', '1', '11', '1', '1', '1', 'http://localhost:8080\\upload\\20160819\\1471588741672.jpg', '1471023933664006', '1471028944881002', '1471028944881007', '1471028496235000', '0', 'admin', '2016-08-18 02:49:02', 'admin', '2016-08-22 05:23:15', '');
INSERT INTO `tm_class` VALUES ('6', '1', '8', '1', '8-15岁', '30', '5', '18', '', '1', '320', '1', '14:00', '16:00', 'http://localhost:8080\\upload\\20160820\\1471645037790.jpg', '1471023933664006', '1471028944881004', '1471029273331000', '1471028496235000', '0', 'admin', '2016-08-20 06:21:49', 'admin', '2016-08-22 05:23:15', '艺术体操~~ (20节)');

-- ----------------------------
-- Table structure for tm_classroom
-- ----------------------------
DROP TABLE IF EXISTS `tm_classroom`;
CREATE TABLE `tm_classroom` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `classroom_no` varchar(10) DEFAULT NULL COMMENT '教室编号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`),
  UNIQUE KEY `IDX_UK_TM_USER_UID` (`classroom_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教室信息';

-- ----------------------------
-- Records of tm_classroom
-- ----------------------------
INSERT INTO `tm_classroom` VALUES ('1471028944881005', '201', 'admin', '2016-08-13 03:13:49', 'admin', '2016-08-13 03:13:49', '');
INSERT INTO `tm_classroom` VALUES ('1471028944881006', '202', 'admin', '2016-08-13 03:14:07', 'admin', '2016-08-13 03:14:07', '');
INSERT INTO `tm_classroom` VALUES ('1471028944881007', '203', 'admin', '2016-08-13 03:14:14', 'admin', '2016-08-13 03:14:14', '');
INSERT INTO `tm_classroom` VALUES ('1471028944881008', '315', 'admin', '2016-08-13 03:14:20', 'admin', '2016-08-13 03:14:20', '');
INSERT INTO `tm_classroom` VALUES ('1471028944881009', '316', 'admin', '2016-08-13 03:14:27', 'admin', '2016-08-13 03:14:27', '');
INSERT INTO `tm_classroom` VALUES ('1471029273331000', '401', 'admin', '2016-08-13 03:14:33', 'admin', '2016-08-13 03:14:33', '');

-- ----------------------------
-- Table structure for tm_class_type
-- ----------------------------
DROP TABLE IF EXISTS `tm_class_type`;
CREATE TABLE `tm_class_type` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `name` varchar(100) DEFAULT NULL COMMENT '类型名称',
  `picture_url` varchar(500) DEFAULT NULL COMMENT '图片url',
  `parent_id` varchar(20) DEFAULT NULL COMMENT '父级id',
  `sort` varchar(3) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`),
  UNIQUE KEY `IDX_UK_TM_USER_UID` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级类型';

-- ----------------------------
-- Records of tm_class_type
-- ----------------------------
INSERT INTO `tm_class_type` VALUES ('1471023933664001', '钢琴', '', '1471245507574004', '', 'admin', '2016-08-13 01:46:23', 'admin', '2016-08-15 15:33:36', '');
INSERT INTO `tm_class_type` VALUES ('1471023933664002', '吉他', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574003', '', 'admin', '2016-08-13 01:46:38', 'admin', '2016-08-15 18:23:35', '');
INSERT INTO `tm_class_type` VALUES ('1471023933664003', '古筝', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574003', '', 'admin', '2016-08-13 01:46:47', 'admin', '2016-08-15 18:23:30', '');
INSERT INTO `tm_class_type` VALUES ('1471023933664005', '武术', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574002', '', 'admin', '2016-08-13 01:48:13', 'admin', '2016-08-15 18:23:25', '');
INSERT INTO `tm_class_type` VALUES ('1471023933664006', '舞蹈', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574000', '', 'admin', '2016-08-13 01:48:20', 'admin', '2016-08-15 18:23:20', '');
INSERT INTO `tm_class_type` VALUES ('1471245507574000', '艺术', '', '', '1', 'admin', '2016-08-15 15:28:12', 'admin', '2016-08-15 15:28:12', '');
INSERT INTO `tm_class_type` VALUES ('1471245507574001', '声乐', '', '', '2', 'admin', '2016-08-15 15:28:29', 'admin', '2016-08-15 15:28:29', '');
INSERT INTO `tm_class_type` VALUES ('1471245507574002', '体育', '', '', '3', 'admin', '2016-08-15 15:28:44', 'admin', '2016-08-15 15:28:44', '');
INSERT INTO `tm_class_type` VALUES ('1471245507574003', '管弦乐', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574001', '', 'admin', '2016-08-15 15:31:26', 'admin', '2016-08-15 18:23:14', '');
INSERT INTO `tm_class_type` VALUES ('1471245507574004', '击打类', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574001', '', 'admin', '2016-08-15 15:31:57', 'admin', '2016-08-15 18:23:05', '');
INSERT INTO `tm_class_type` VALUES ('1471245507574005', '架子鼓', 'http://m.360buyimg.com/mobilecms/s357x357_jfs/t2056/125/2843368922/607448/b176bcfc/57199035Nf7e3ec09.jpg!q70.jpg', '1471245507574004', '', 'admin', '2016-08-15 15:34:10', 'admin', '2016-08-15 18:22:57', '');

-- ----------------------------
-- Table structure for tm_content
-- ----------------------------
DROP TABLE IF EXISTS `tm_content`;
CREATE TABLE `tm_content` (
  `id` varchar(64) NOT NULL COMMENT '内容编号',
  `type` varchar(10) DEFAULT NULL COMMENT '内容名称',
  `uri` varchar(255) DEFAULT NULL COMMENT '内容链接(一般情况最后实际指向content字段)',
  `content` longtext COMMENT '内容html',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TT_CONTENT_ID` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='h5内容表';

-- ----------------------------
-- Records of tm_content
-- ----------------------------
INSERT INTO `tm_content` VALUES ('1471604449848006', '0', 'http://localhost:8080/wap/content/1471604449848006.shtml', '<html readabilitycontentscore=\"1\">\n <head> \n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />           \n  <link href=\"http://localhost:8080/mobile/css/common_mobile.css\" rel=\"stylesheet\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> <script src=\"http://localhost:8080/mobile/scripts/jquery-2.1.4.min.js\"></script> <script src=\"http://localhost:8080/mobile/scripts/jquery.lazyload.js\"></script> <script src=\"http://localhost:8080/mobile/scripts/jquery.imgAutoSize.js\"></script> <script type=\"text/javascript\"> $(function(){ $(\"img\").lazyload({ placeholder : \"http://localhost:8080/mobile/img/grey-default.png\", effect : \"fadeIn\" }); $(\"body\").imgAutoSize(); }); </script></head> \n <body readabilitycontentscore=\"3\">\n  <div>\n   <div>\n    <h1></h1>\n    <div id=\"readability-content\">\n     <div readabilitycontentscore=\"5\"> \n      <p>H5URI不正确,您也可以在此处重新编辑您的H5内容！</p> \n      <p><img src=\"/../ueditor/jsp/upload/image/20160819/1471605563166003547.jpg\" title=\"1471605563166003547.jpg\" alt=\"Jellyfish.jpg\" /></p> \n     </div>\n    </div>\n   </div>\n  </div>\n </body>\n</html>', 'admin', '2016-08-19 19:05:33', 'admin', '2016-08-19 19:25:03', '');

-- ----------------------------
-- Table structure for tm_school
-- ----------------------------
DROP TABLE IF EXISTS `tm_school`;
CREATE TABLE `tm_school` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `type` varchar(3) DEFAULT NULL COMMENT '学校类型',
  `name` varchar(100) DEFAULT NULL COMMENT '学校名称',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`),
  UNIQUE KEY `IDX_UK_TM_USER_UID` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校信息';

-- ----------------------------
-- Records of tm_school
-- ----------------------------
INSERT INTO `tm_school` VALUES ('1471030187448000', '0', '第一幼儿园', 'admin', '2016-08-13 03:31:38', 'admin', '2016-08-13 03:31:38', '');
INSERT INTO `tm_school` VALUES ('1471030187448001', '1', '第二小学', 'admin', '2016-08-13 03:31:50', 'admin', '2016-08-13 03:31:50', '');
INSERT INTO `tm_school` VALUES ('1471030187448002', '2', '第二实验中学', 'admin', '2016-08-13 03:32:10', 'admin', '2016-08-13 03:32:10', '');
INSERT INTO `tm_school` VALUES ('1471030187448003', '4', '成人夜校', 'admin', '2016-08-13 03:32:25', 'admin', '2016-08-13 03:32:25', '');
INSERT INTO `tm_school` VALUES ('1471030187448004', '4', '新东方职业技能学院', 'admin', '2016-08-13 03:32:45', 'admin', '2016-08-13 03:32:45', '');

-- ----------------------------
-- Table structure for tm_student
-- ----------------------------
DROP TABLE IF EXISTS `tm_student`;
CREATE TABLE `tm_student` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `card_type` varchar(3) NOT NULL COMMENT '证件类型',
  `idcard_no` varchar(100) NOT NULL COMMENT '身份证号码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(3) NOT NULL COMMENT '性别',
  `is_local_or_not` varchar(50) NOT NULL COMMENT '是否石狮本地',
  `parent_name` varchar(20) NOT NULL COMMENT '家长姓名',
  `school_id` varchar(50) NOT NULL COMMENT '所在学校id',
  `grade` varchar(10) NOT NULL COMMENT '就读年级',
  `picture_url` varchar(500) NOT NULL COMMENT '头像',
  `birth_date` varchar(200) DEFAULT NULL COMMENT '出生日期',
  `address` varchar(500) DEFAULT NULL COMMENT '家庭地址',
  `student_code` varchar(50) DEFAULT NULL COMMENT '学籍号',
  `is_lowincome` varchar(15) DEFAULT NULL COMMENT '是否是低保户',
  `home_phone` varchar(15) DEFAULT NULL COMMENT '家庭电话',
  `account_id` varchar(20) DEFAULT NULL COMMENT '账号id',
  `is_blacklist` varchar(3) DEFAULT NULL COMMENT '是否黑名单',
  `blacklist_reason` varchar(255) DEFAULT NULL COMMENT '黑名单原因',
  `blacklist_reason_url` varchar(255) DEFAULT NULL COMMENT '黑名单原因图片',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`),
  KEY `IDX_FOR_ACCOUNT_ID` (`account_id`) USING BTREE,
  KEY `IDX_FOR_SCHOOL_ID` (`school_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生资料表';

-- ----------------------------
-- Records of tm_student
-- ----------------------------
INSERT INTO `tm_student` VALUES ('1471099706035002', '0', '582222222', '三冰1', '1', '1', '1', '1471030187448000', '1', '', '1', '普陀', '1', '0', '1', '1471102211862000', '', '', '', null, '2016-08-13 22:54:07', 'admin', '2016-08-22 23:23:36', '');
INSERT INTO `tm_student` VALUES ('1471103366481002', '0', '12300000', '小明', '1', '1', '', '1', '1', '', '', '浦东新区', '', '0', '', '1470989545816001', '', '', '', null, '2016-08-14 05:44:53', 'admin', '2016-08-14 10:03:32', '');
INSERT INTO `tm_student` VALUES ('1471232879625000', '1', '1', '宝强兄弟挺住啊', '0', '1', '111', '1471030187448003', '1', 'http://localhost:8080\\upload\\20160819\\1471592780791.jpg', '1', '111asdfasdfsd', '1111', '0', '1111', '1471228224262000', null, null, null, null, '2016-08-15 11:51:27', null, '2016-08-20 00:58:42', '');
INSERT INTO `tm_student` VALUES ('1471360412884000', '0', '', '', '1', '1', '', '1', '1', '', '', '', '', '0', '', '1471291743616000', null, null, null, null, '2016-08-16 23:16:18', null, '2016-08-16 23:16:18', '');
INSERT INTO `tm_student` VALUES ('1471458784422001', '0', '210682198809011859', '北京-回首', '1', '1', '111', '1471030187448000', '1', 'http://localhost:8080\\upload\\20160821\\1471772241339.jpg', '19880901', '朝阳区 朝阳小区', '111', '0', '111', '1471458784422000', null, null, null, null, '2016-08-18 02:44:26', null, '2016-08-21 19:30:59', '');
INSERT INTO `tm_student` VALUES ('1471753464994004', '0', '210682198809011859', 'a', '0', '0', '1', '', '1', 'http://localhost:8080\\upload\\20160821\\1471753744818.jpg', '19880901', '1', '1', '0', '1', '1471743314619000', null, null, null, null, '2016-08-21 12:29:23', null, '2016-08-21 12:29:23', '');
INSERT INTO `tm_student` VALUES ('1471778193263004', '0', '210682198809011859', '1', '0', '0', '1', '', '1', 'http://localhost:8080\\upload\\20160821\\1471779308153.jpg', '19880901', '1', '1', '0', '1', '1471458784422000', null, null, null, null, '2016-08-21 19:35:20', null, '2016-08-21 19:35:20', '');

-- ----------------------------
-- Table structure for tm_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tm_teacher`;
CREATE TABLE `tm_teacher` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `idcard_no` varchar(20) DEFAULT NULL COMMENT '身份证编号',
  `phonenumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(3) DEFAULT NULL COMMENT '性别',
  `picture_url` varchar(500) DEFAULT NULL COMMENT '头像',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`),
  UNIQUE KEY `IDX_UK_TM_USER_UID` (`idcard_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授课老师信息';

-- ----------------------------
-- Records of tm_teacher
-- ----------------------------
INSERT INTO `tm_teacher` VALUES ('1471028944881000', '123456789123465789', '13815522244', '张老师', '0', '', 'admin', '2016-08-13 03:10:33', 'admin', '2016-08-13 03:10:33', '');
INSERT INTO `tm_teacher` VALUES ('1471028944881001', '987654321987654321', '13755550000', '宋老师', '1', '', 'admin', '2016-08-13 03:11:01', 'admin', '2016-08-13 03:11:01', '');
INSERT INTO `tm_teacher` VALUES ('1471028944881002', '8888888888888', '13955552222', '李老师', '1', '', 'admin', '2016-08-13 03:11:36', 'admin', '2016-08-13 03:11:36', '');
INSERT INTO `tm_teacher` VALUES ('1471028944881003', '2151888887777720000', '13566660000', '伍声2009', '0', '', 'admin', '2016-08-13 03:12:27', 'admin', '2016-08-13 03:12:27', '');
INSERT INTO `tm_teacher` VALUES ('1471028944881004', '3564888877700000', '13688887777', '甜甜', '1', '', 'admin', '2016-08-13 03:13:25', 'admin', '2016-08-13 03:13:25', '');

-- ----------------------------
-- Table structure for tm_term
-- ----------------------------
DROP TABLE IF EXISTS `tm_term`;
CREATE TABLE `tm_term` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `name` varchar(50) DEFAULT NULL COMMENT '学期名称',
  `begin_date` varchar(20) DEFAULT NULL COMMENT '学期开始时间',
  `end_date` varchar(20) DEFAULT NULL COMMENT '学期结束时间',
  `status` varchar(500) DEFAULT NULL COMMENT '学期状态',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_USER_ID` (`id`),
  UNIQUE KEY `IDX_UK_TM_USER_UID` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学期信息';

-- ----------------------------
-- Records of tm_term
-- ----------------------------
INSERT INTO `tm_term` VALUES ('1471028496235000', '2016 夏季招生', '8月13', '11月13', '2', 'admin', '2016-08-13 03:02:39', 'admin', '2016-08-22 16:41:49', '');
INSERT INTO `tm_term` VALUES ('1471857938972000', '2016 秋季招生', '', '', '2', 'admin', '2016-08-22 17:26:15', 'admin', '2016-08-22 17:26:15', '');
INSERT INTO `tm_term` VALUES ('1471858358072002', '2016 冬季', '', '', '1', 'admin', '2016-08-22 17:34:14', 'admin', '2016-08-22 17:34:14', '');
INSERT INTO `tm_term` VALUES ('1471891338577000', '2016 秋季招生 1', '', '', '0', 'admin', '2016-08-23 02:52:54', 'admin', '2016-08-23 02:52:54', '');

-- ----------------------------
-- Table structure for ts_config_quartz
-- ----------------------------
DROP TABLE IF EXISTS `ts_config_quartz`;
CREATE TABLE `ts_config_quartz` (
  `id` varchar(64) NOT NULL COMMENT '流水id',
  `trigger_name` varchar(20) NOT NULL COMMENT '任务名称',
  `cron_expression` varchar(20) DEFAULT NULL COMMENT '触发时间(CRON表达式)',
  `job_detail_name` varchar(64) DEFAULT NULL COMMENT '任务详细说明',
  `target_object` varchar(500) DEFAULT NULL COMMENT '任务java对象',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `concurrent` varchar(1) DEFAULT NULL COMMENT '并发',
  `state` varchar(1) DEFAULT NULL COMMENT '任务状态',
  `is_spring_bean` varchar(1) DEFAULT NULL COMMENT '是spring组件',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计划任务表';

-- ----------------------------
-- Records of ts_config_quartz
-- ----------------------------

-- ----------------------------
-- Table structure for ts_menu
-- ----------------------------
DROP TABLE IF EXISTS `ts_menu`;
CREATE TABLE `ts_menu` (
  `menu_id` varchar(32) NOT NULL,
  `parent_menu_id` varchar(32) DEFAULT NULL,
  `menu_name` varchar(20) NOT NULL,
  `menu_href` varchar(100) DEFAULT NULL,
  `jsp_name` varchar(100) DEFAULT NULL COMMENT 'jsp名',
  `js_sel` varchar(200) DEFAULT '' COMMENT 'js选择器',
  `is_menu` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否为菜单',
  `sort` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `IDX_PK_TS_MENU_MENU_ID` (`menu_id`),
  KEY `IDX_TS_MENU_PARENT_MENU_ID` (`parent_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of ts_menu
-- ----------------------------
INSERT INTO `ts_menu` VALUES ('00f0101cda5447dea8872db8a36acb52', null, '系统管理', '', '', 'glyphicon glyphicon-cog', '1', '0');
INSERT INTO `ts_menu` VALUES ('02ae957c0d3a4fbb831730081fd878f2', 'fdbd411e15494323a93f3982f99e12a7', '编辑权限Json获取', '/admin/auth/authJson.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('0ea5a3ac14a040eb875713313c81efa1', 'bab085692ba54f1ba2363d5996266fec', '保存', '/admin/message/add.shtml', '', '', '0', '0');
INSERT INTO `ts_menu` VALUES ('0febc6a9053f4ffebdda6f78e26334c7', '8569be064ea14eeaa123d8c4cfec68f6', '渠道信息管理', '/admin/ghchannel/list.shtml', '', '', '1', '1');
INSERT INTO `ts_menu` VALUES ('1441884555190003', '1441884555190002', '太阳系管理', '', '', '', '1', '0');
INSERT INTO `ts_menu` VALUES ('1442288105842000', null, '业务流水', '', '', 'glyphicon glyphicon-transfer', '1', '7');
INSERT INTO `ts_menu` VALUES ('1442288105842001', '', '支付管理', '', '', '', '1', '0');
INSERT INTO `ts_menu` VALUES ('1442288105842002', '', '支付管理', '', '', '', '1', '0');
INSERT INTO `ts_menu` VALUES ('1442288105842003', null, '统计报表', '', '', 'glyphicon glyphicon-stats', '1', '6');
INSERT INTO `ts_menu` VALUES ('1442288105842006', '1442288105842003', '学生情况分析', '/report/studentsinfo.shtml', '', '', '1', '9');
INSERT INTO `ts_menu` VALUES ('1442288658793000', '1442288105842000', '结算收款', '/user/order.shtml', '', '', '1', '10');
INSERT INTO `ts_menu` VALUES ('1442288658793001', '1442288105842000', '本学期订单明细查看', '/user/orderdetail.shtml', '', '', '1', '9');
INSERT INTO `ts_menu` VALUES ('1442308625377000', null, '首页', '/login.shtml', '', 'fa fa-home', '1', '10');
INSERT INTO `ts_menu` VALUES ('1443592097157000', '231e364b229c4bfe950081e0b1c9a377', '课程类型', '/operation/classtype.shtml', '', '', '1', '8');
INSERT INTO `ts_menu` VALUES ('1445852844084000', null, '运营', '', '', 'fa fa-cogs', '0', '4');
INSERT INTO `ts_menu` VALUES ('1445852844084004', '1445852844084000', '上学期开班查看', '/history/lastclass.shtml', '', '', '1', '6');
INSERT INTO `ts_menu` VALUES ('1446452883905000', '00f0101cda5447dea8872db8a36acb52', '计划任务', '/sys/configquartz.shtml', '', '', '1', '7');
INSERT INTO `ts_menu` VALUES ('1447749263080000', '1445852844084000', '学校管理', '/operation/school.shtml', '', '', '1', '9');
INSERT INTO `ts_menu` VALUES ('1448507957487000', '1445852844084000', '教室管理', '/operation/classroom.shtml', '', '', '1', '8');
INSERT INTO `ts_menu` VALUES ('1452135731549003', '1445852844084000', '内容管理', '/operation/content.shtml', '', '', '1', '7');
INSERT INTO `ts_menu` VALUES ('1452135731549004', null, '会员管理', '', '', 'fa fa-group', '0', '8');
INSERT INTO `ts_menu` VALUES ('1452135731549005', '1452135731549004', '账号管理', '/operation/account.shtml', '', '', '1', '11');
INSERT INTO `ts_menu` VALUES ('1452135731549006', '1445852844084000', '授课老师管理', '/operation/teacher.shtml', '', 'fa fa-cogs', '1', '10');
INSERT INTO `ts_menu` VALUES ('1452141152902001', '1445852844084000', '学期管理', '/operation/term.shtml', '', '', '1', '12');
INSERT INTO `ts_menu` VALUES ('1452141152902003', '1452135731549004', '学生报班查看', '/user/classstudent.shtml', '', '', '1', '9');
INSERT INTO `ts_menu` VALUES ('1452141152902004', '1452135731549004', '学生选课车查看', '/user/shoppingcart.shtml', '', '', '1', '8');
INSERT INTO `ts_menu` VALUES ('1452141152902005', '1452135731549004', '推荐审核', '/user/recommend.shtml', '', '', '1', '7');
INSERT INTO `ts_menu` VALUES ('1452142587830001', '1442288105842003', '招生情况分析', '/report/enrolstudents.shtml', '', '', '1', '8');
INSERT INTO `ts_menu` VALUES ('1452150329252000', '1452135731549004', '学生资料管理', '/operation/student.shtml', '', '', '1', '10');
INSERT INTO `ts_menu` VALUES ('1453193084488000', '1442288105842000', '历史订单查看', '/zip/allorder.shtml', '', '', '1', '8');
INSERT INTO `ts_menu` VALUES ('1470745211941000', '1442288105842000', '历史订单明细查看', '/zip/allorderdetail.shtml', '', '', '1', '7');
INSERT INTO `ts_menu` VALUES ('1470745211941001', '1445852844084000', '上学期报班情况查看(旧生推荐)', '/history/lastclassstudent.shtml', '', '', '1', '5');
INSERT INTO `ts_menu` VALUES ('1470745211941002', '1445852844084000', '历史开班查看', '/zip/allclass.shtml', '', '', '1', '4');
INSERT INTO `ts_menu` VALUES ('1470745211941003', '1445852844084000', '历史报班情况查看', '/zip/allclassstudent.shtml', '', '', '1', '3');
INSERT INTO `ts_menu` VALUES ('1471889190425000', '1452135731549004', '现场报名', '/login.shtml', '', '', '1', '6');
INSERT INTO `ts_menu` VALUES ('183f7f23fd624ced8eca121f7515b618', '00f0101cda5447dea8872db8a36acb52', '角色管理', '/sys/role.shtml', '', '', '1', '9');
INSERT INTO `ts_menu` VALUES ('19e23b82202e449bb0b9d3cc894ada55', '13386fb31b674b328256d98425ccfbf8', 'CP信息管理', '/admin/ghcp/list.shtml', '', '', '1', '0');
INSERT INTO `ts_menu` VALUES ('1b9ed566dc164f958b3d9e78afcf3b7d', 'e5dd60435e694d3597654fb3f25a4370', '修改密码', '/admin/user/initModifyPage.shtml', '', '', '0', '0');
INSERT INTO `ts_menu` VALUES ('220f5fdff5d5449dac0d8360361878dc', 'bab085692ba54f1ba2363d5996266fec', '编辑/新增', '/admin/message/toAdd.shtml', '/jsp/admin/message/list.jsp', '.btn', '0', '0');
INSERT INTO `ts_menu` VALUES ('231e364b229c4bfe950081e0b1c9a377', null, '基础数据', '', '', 'glyphicon glyphicon-book', '0', '5');
INSERT INTO `ts_menu` VALUES ('2bf6803e6579446aacee24d3e51dc88f', '5f6132e421e4478786527d77374f1bb9', '发送邮件', '/admin/email/toSend.shtml', '/jsp/admin/email/list.jsp', '.btn', '0', '0');
INSERT INTO `ts_menu` VALUES ('2d3b2a8469504de596ec23f8badcc8f3', '54360fb5e57d4cb4893df6a8c24ed069', '保存', '/admin/user/modifyAll.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('2f1718d6b5b84d0289d5a8f39315156e', '8569be064ea14eeaa123d8c4cfec68f6', '游戏管理', '/admin/ghchannel/game/list.shtml', '', '', '1', '10');
INSERT INTO `ts_menu` VALUES ('2f41b2a8ff3042a78717797bd62455ef', '5f6132e421e4478786527d77374f1bb9', '查询', '', '', '', '0', '0');
INSERT INTO `ts_menu` VALUES ('33f26d851cd5445f816fc3de763209b7', '13386fb31b674b328256d98425ccfbf8', '用户管理', '/admin/ghcp/user/list.shtml', '', '', '1', '5');
INSERT INTO `ts_menu` VALUES ('3f4755a162214e7da1774ce21274c508', 'fdbd411e15494323a93f3982f99e12a7', '保存', '/admin/auth/authUpdate.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('3ff68c719a4e4276bae65f32cef2dd25', 'bab085692ba54f1ba2363d5996266fec', '查询', '', '', '', '0', '1');
INSERT INTO `ts_menu` VALUES ('49e1aa37e6a14438a7af974371aff5f5', 'e5dd60435e694d3597654fb3f25a4370', '主界面', '/admin/menu/index.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('7291fa93566846b6ad87b13e693f1962', '00f0101cda5447dea8872db8a36acb52', '菜单管理', '/sys/menu.shtml', '', '', '1', '8');
INSERT INTO `ts_menu` VALUES ('7a8e058119f740cebf9ecff9cd206d57', '23589e18c3dc41f3b863e068372dc6d8', '保存', '/admin/user/addUser.shtml', '', '', '0', '0');
INSERT INTO `ts_menu` VALUES ('8314b1186d3544fb8cda350b8e089045', '231e364b229c4bfe950081e0b1c9a377', '码表管理', '/basedata/code.shtml', '', '', '1', '10');
INSERT INTO `ts_menu` VALUES ('853a442fe3dc41afa2975a395b166e7d', '13386fb31b674b328256d98425ccfbf8', '游戏管理', '/admin/ghcp/game/list.shtml', '', '', '1', '6');
INSERT INTO `ts_menu` VALUES ('8daf7dd4e2824c8795f30c4a61f8e766', 'd071ff33bb0f436b94cb2d8f93e548eb', '支付通道信息管理', '/admin/ghpayway/list.shtml', '', '', '1', '0');
INSERT INTO `ts_menu` VALUES ('92de0088eb4b4e4db618466447fde106', 'e5dd60435e694d3597654fb3f25a4370', '上方菜单', '/admin/menu/topMenu.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('984193ee23cf4c3d8a305d21f772ea5a', null, '开班管理', '/operation/class.shtml', '', 'glyphicon glyphicon-shopping-cart', '1', '9');
INSERT INTO `ts_menu` VALUES ('9936be5785ff46518b1957ccad8f2888', '1c013727a7b74f8f8cfd7c474e6c7c59', '保存', '/admin/auth/addRole.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('a0bc9e6517474c59979236d16ded0027', '00f0101cda5447dea8872db8a36acb52', '用户管理', '/sys/user.shtml', '', '', '1', '10');
INSERT INTO `ts_menu` VALUES ('aede04da13994b43afbb66443b8d0e3e', '8569be064ea14eeaa123d8c4cfec68f6', '用户管理', '/admin/ghchannel/user/list.shtml', '', '', '1', '1');
INSERT INTO `ts_menu` VALUES ('b5ad51fb5dec4c3081b039f48455e1b6', 'bab085692ba54f1ba2363d5996266fec', '修改', '/admin/message/update.shtml', '/jsp/admin/message/list.jsp', '.edit', '0', '2');
INSERT INTO `ts_menu` VALUES ('bfdda1c71c914edb87ef58139a201474', 'd071ff33bb0f436b94cb2d8f93e548eb', '支付结果查询', '/admin/ghpayway/report/list.shtml', '', '', '1', '0');
INSERT INTO `ts_menu` VALUES ('cef00da093f748a393744b623e0ca7d6', 'e5dd60435e694d3597654fb3f25a4370', '左方菜单', '/admin/menu/leftMenu.shtml', '', null, '0', '0');
INSERT INTO `ts_menu` VALUES ('f3b3ee6ce9c248e4ba5a4c53e63ed55c', '2bf6803e6579446aacee24d3e51dc88f', '发送', '/admin/email/send.shtml', '/jsp/admin/email/toSend.jsp', '.btn', '0', '0');
INSERT INTO `ts_menu` VALUES ('fabec13caf094ab7abe41f1aa4798222', '5f6132e421e4478786527d77374f1bb9', '删除', '/admin/email/del.shtml', '/jsp/admin/email/list.jsp', '.edit', '0', '0');

-- ----------------------------
-- Table structure for ts_role
-- ----------------------------
DROP TABLE IF EXISTS `ts_role`;
CREATE TABLE `ts_role` (
  `role_id` varchar(32) NOT NULL DEFAULT '',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `IDX_PK_TS_ROLE_ROLE_ID` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of ts_role
-- ----------------------------
INSERT INTO `ts_role` VALUES ('1470677643769000', '系统管理员', 'admin', '2016-08-09 01:39:38', 'admin', '2016-08-09 02:33:16');
INSERT INTO `ts_role` VALUES ('1471617938833000', '授课老师', 'admin', '2016-08-19 23:35:32', 'admin', '2016-08-22 00:24:07');

-- ----------------------------
-- Table structure for ts_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `ts_role_auth`;
CREATE TABLE `ts_role_auth` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户权限关联表';

-- ----------------------------
-- Records of ts_role_auth
-- ----------------------------
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '00f0101cda5447dea8872db8a36acb52');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1442288105842000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1442288658793000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1442288658793001');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1442308625377000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1443592097157000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1445852844084000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1445852844084004');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1446452883905000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1447749263080000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1448507957487000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452135731549003');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452135731549004');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452135731549005');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452135731549006');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452141152902001');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452141152902003');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452141152902004');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452141152902005');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1452150329252000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1453193084488000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1470745211941000');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1470745211941001');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1470745211941002');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '1470745211941003');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '183f7f23fd624ced8eca121f7515b618');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '231e364b229c4bfe950081e0b1c9a377');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '7291fa93566846b6ad87b13e693f1962');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '8314b1186d3544fb8cda350b8e089045');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', '984193ee23cf4c3d8a305d21f772ea5a');
INSERT INTO `ts_role_auth` VALUES ('1470677643769000', 'a0bc9e6517474c59979236d16ded0027');
INSERT INTO `ts_role_auth` VALUES ('1471617938833000', '1442308625377000');
INSERT INTO `ts_role_auth` VALUES ('1471617938833000', '1445852844084000');
INSERT INTO `ts_role_auth` VALUES ('1471617938833000', '1445852844084004');
INSERT INTO `ts_role_auth` VALUES ('1471617938833000', '1470745211941001');

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `username` varchar(20) NOT NULL COMMENT '管理员用户名',
  `password` varchar(50) NOT NULL COMMENT '管理员密码',
  `real_name` varchar(20) DEFAULT '' COMMENT '真实姓名',
  `phone` varchar(50) DEFAULT '' COMMENT '联系手机',
  `up_user` varchar(20) DEFAULT '' COMMENT '上级用户',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  PRIMARY KEY (`username`),
  UNIQUE KEY `IDX_PK_TS_USER_USERNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of ts_user
-- ----------------------------
INSERT INTO `ts_user` VALUES ('1', 'RQUV\0PW\0]RTV]]TT\\VRQV\0', '1', '1', 'admin', '1471617938833000', 'admin', '2016-08-20 03:33:46', 'admin', '2016-08-20 03:33:46');
INSERT INTO `ts_user` VALUES ('13566660000', ']]SS\\]QRT]]]SSRQW]PW\\U', '伍声2009', '13566660000', null, '1471617938833000', 'admin', '2016-08-22 06:08:57', 'admin', '2016-08-22 06:08:57');
INSERT INTO `ts_user` VALUES ('13688887777', 'W\\P]R\0]\0P\0RP]TR]U]TWWS', '甜甜', '13688887777', null, '1471617938833000', 'admin', '2016-08-22 06:08:57', 'admin', '2016-08-22 06:08:57');
INSERT INTO `ts_user` VALUES ('13755550000', 'PRUWRT\\R\\W\0\\\0WVQ\\\0\\WWRV\\PWPT', '宋老师', '13755550000', null, '1471617938833000', 'admin', '2016-08-22 06:08:57', 'admin', '2016-08-22 06:08:57');
INSERT INTO `ts_user` VALUES ('13815522244', 'QV\0VW\\UU\0\0T]\0UPT\0RTQ\\', '张老师', '13815522244', null, '1471617938833000', 'admin', '2016-08-22 06:08:57', 'admin', '2016-08-22 06:08:57');
INSERT INTO `ts_user` VALUES ('13955552222', '\\WQSS\\WSQQS]VURRSR\\Q\\Q', '李老师', '13955552222', null, '1471617938833000', 'admin', '2016-08-22 06:08:57', 'admin', '2016-08-22 06:08:57');
INSERT INTO `ts_user` VALUES ('admin', 'TU]VTVWS\0SWVQTQURTR]\0U\\QTT', 'admin', '13800000000', 'admin', '1470677643769000', 'admin', '2016-08-09 01:27:25', 'admin', '2016-08-09 19:42:20');

-- ----------------------------
-- Table structure for tt_class_student
-- ----------------------------
DROP TABLE IF EXISTS `tt_class_student`;
CREATE TABLE `tt_class_student` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `class_id` varchar(20) DEFAULT NULL COMMENT '班级id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TT_SHOPPING_CART_ID` (`id`),
  KEY `IDX_TT_SHOPPING_CART_UID` (`student_id`),
  KEY `IDX_TT_SHOPPING_CART_COMMODITY_NO` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级学生关联表';

-- ----------------------------
-- Records of tt_class_student
-- ----------------------------

-- ----------------------------
-- Table structure for tt_order
-- ----------------------------
DROP TABLE IF EXISTS `tt_order`;
CREATE TABLE `tt_order` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `total_money` varchar(50) DEFAULT NULL COMMENT '订单总金额',
  `status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TLR_GRAB_RECORDS_ID` (`id`),
  KEY `IDX_TLR_GRAB_RECORDS_UID` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of tt_order
-- ----------------------------
INSERT INTO `tt_order` VALUES ('1471915232900004', '1471915232900001', '211.0', '1', 'studentId:1471915232900001', '2016-08-23 10:08:36', 'studentId:1471915232900001', '2016-08-23 10:08:36', null);
INSERT INTO `tt_order` VALUES ('1471919130318002', '1471919130318000', '200.0', '1', 'studentId:1471919130318000', '2016-08-23 10:28:27', 'studentId:1471919130318000', '2016-08-23 10:28:27', null);
INSERT INTO `tt_order` VALUES ('1471919130318005', '1471232879625000', '3.0', '1', 'studentId:1471232879625000', '2016-08-23 10:29:53', 'studentId:1471232879625000', '2016-08-23 10:29:53', null);
INSERT INTO `tt_order` VALUES ('1471919130318006', '1471919130318000', '3.0', '1', 'studentId:1471919130318000', '2016-08-23 10:34:08', 'studentId:1471919130318000', '2016-08-23 10:34:08', null);
INSERT INTO `tt_order` VALUES ('1471922665979001', '1471232879625000', '200.0', '1', 'studentId:1471232879625000', '2016-08-23 11:25:23', 'studentId:1471232879625000', '2016-08-23 11:25:23', null);

-- ----------------------------
-- Table structure for tt_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tt_order_detail`;
CREATE TABLE `tt_order_detail` (
  `id` varchar(20) NOT NULL COMMENT '订单id',
  `class_id` varchar(20) NOT NULL COMMENT '班级id',
  PRIMARY KEY (`id`,`class_id`),
  KEY `IDX_TLR_GRAB_RECORDS_UID` (`class_id`),
  KEY `IDX_PK_TLR_GRAB_RECORDS_ID` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细表';

-- ----------------------------
-- Records of tt_order_detail
-- ----------------------------
INSERT INTO `tt_order_detail` VALUES ('1471915232900004', '1');
INSERT INTO `tt_order_detail` VALUES ('1471919130318002', '1');
INSERT INTO `tt_order_detail` VALUES ('1471922665979001', '1');
INSERT INTO `tt_order_detail` VALUES ('1471919130318005', '2');
INSERT INTO `tt_order_detail` VALUES ('1471919130318006', '2');
INSERT INTO `tt_order_detail` VALUES ('1471915232900004', '5');

-- ----------------------------
-- Table structure for tt_recommend
-- ----------------------------
DROP TABLE IF EXISTS `tt_recommend`;
CREATE TABLE `tt_recommend` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `class_id` varchar(20) DEFAULT NULL COMMENT '班级id',
  `teacher_id` varchar(20) DEFAULT NULL COMMENT '被推荐老师id',
  `is_through_approval` varchar(3) DEFAULT NULL COMMENT '是否通过审批',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TT_RECOMMEND_ID` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='旧生课程推荐表';

-- ----------------------------
-- Records of tt_recommend
-- ----------------------------
INSERT INTO `tt_recommend` VALUES ('1471798387835002', '1471099706035002', '1471644901259000', '1471028944881000', '1', 'admin', '2016-08-22 01:03:30', 'admin', '2016-08-23 02:03:26', '');
INSERT INTO `tt_recommend` VALUES ('1471880712115000', '1471458784422001', '1470938179177003', '1471028944881003', '1', 'admin', '2016-08-22 23:49:51', 'admin', '2016-08-22 23:50:45', '');
INSERT INTO `tt_recommend` VALUES ('1471885348089000', '1471458784422001', '1470938179177004', '1471028944881004', '1', 'admin', '2016-08-23 01:03:34', 'admin', '2016-08-23 01:05:59', '');
INSERT INTO `tt_recommend` VALUES ('1471912166630000', '1471889803146000', '1471889803146000', '1471028944881003', '0', '13566660000', '2016-08-23 08:30:55', '13566660000', '2016-08-23 08:30:55', null);
INSERT INTO `tt_recommend` VALUES ('1471912863391000', '1471458784422001', '1471889803146000', '1471028944881003', '0', '13566660000', '2016-08-23 08:41:57', '13566660000', '2016-08-23 08:41:57', null);
INSERT INTO `tt_recommend` VALUES ('1471913303965000', '1471458784422001', '1471889803146000', '1471028944881003', '0', '13566660000', '2016-08-23 08:49:14', '13566660000', '2016-08-23 08:49:14', null);
INSERT INTO `tt_recommend` VALUES ('1471913590730000', '1471458784422001', '2', '1471028944881003', '0', '13566660000', '2016-08-23 08:54:55', '13566660000', '2016-08-23 08:54:55', null);
INSERT INTO `tt_recommend` VALUES ('1471913590730001', '1471458784422001', '4', '1471028944881003', '0', '13566660000', '2016-08-23 08:54:55', '13566660000', '2016-08-23 08:54:55', null);

-- ----------------------------
-- Table structure for tt_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `tt_shopping_cart`;
CREATE TABLE `tt_shopping_cart` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `class_id` varchar(20) DEFAULT NULL COMMENT '班级id',
  `operation_teacher_id` varchar(20) DEFAULT NULL COMMENT '加入购物车授课老师id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TT_SHOPPING_CART_ID` (`id`),
  KEY `IDX_TT_SHOPPING_CART_UID` (`student_id`),
  KEY `IDX_TT_SHOPPING_CART_COMMODITY_NO` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购课车表';

-- ----------------------------
-- Records of tt_shopping_cart
-- ----------------------------
INSERT INTO `tt_shopping_cart` VALUES ('1471915232900005', '1471915232900001', '2', null, null, '2016-08-23 10:11:08', null, '2016-08-23 10:11:08', null);

-- ----------------------------
-- Table structure for tz_all_class
-- ----------------------------
DROP TABLE IF EXISTS `tz_all_class`;
CREATE TABLE `tz_all_class` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `code` varchar(10) DEFAULT NULL COMMENT '班级代码',
  `min_age` varchar(6) DEFAULT NULL COMMENT '限制最小年龄',
  `is_only_old_student` varchar(6) DEFAULT NULL COMMENT '是否只可以旧生报名',
  `target_group` varchar(50) DEFAULT NULL COMMENT '招生对象',
  `limited_amount` varchar(10) DEFAULT NULL COMMENT '招生人数',
  `default_amount` varchar(10) DEFAULT NULL COMMENT '预设名额',
  `remain_amount` varchar(10) DEFAULT NULL COMMENT '剩余人数',
  `paid_amount` varchar(10) DEFAULT NULL COMMENT '已付款人数',
  `status` varchar(3) DEFAULT NULL COMMENT '上下架状态',
  `tuition_fee` varchar(10) DEFAULT NULL COMMENT '学费',
  `class_time_week` varchar(50) DEFAULT NULL COMMENT '上课时间（星期）',
  `class_time_begin` varchar(50) DEFAULT NULL COMMENT '商品图片1',
  `class_time_end` varchar(50) DEFAULT NULL COMMENT '商品图片1',
  `picture` varchar(1000) DEFAULT NULL COMMENT '乐器或老师照片',
  `class_type_id` varchar(20) DEFAULT NULL COMMENT '课程类别',
  `teacher_id` varchar(20) DEFAULT NULL COMMENT '授课老师id',
  `classroom_id` varchar(20) DEFAULT NULL COMMENT '教室id',
  `term_id` varchar(20) DEFAULT NULL COMMENT '学期id',
  `lock` varchar(1) DEFAULT NULL COMMENT '乐观锁标识(更新，删除时使用)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TM_COMMODITY_ID` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级归档表';

-- ----------------------------
-- Records of tz_all_class
-- ----------------------------

-- ----------------------------
-- Table structure for tz_all_class_student
-- ----------------------------
DROP TABLE IF EXISTS `tz_all_class_student`;
CREATE TABLE `tz_all_class_student` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `class_id` varchar(20) DEFAULT NULL COMMENT '班级id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TT_SHOPPING_CART_ID` (`id`),
  KEY `IDX_TT_SHOPPING_CART_UID` (`student_id`),
  KEY `IDX_TT_SHOPPING_CART_COMMODITY_NO` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级学生关联归档表';

-- ----------------------------
-- Records of tz_all_class_student
-- ----------------------------
INSERT INTO `tz_all_class_student` VALUES ('1471798387835000', '1', '1', 'admin', '2016-08-22 00:55:12', 'admin', '2016-08-22 00:55:12', '');

-- ----------------------------
-- Table structure for tz_all_order
-- ----------------------------
DROP TABLE IF EXISTS `tz_all_order`;
CREATE TABLE `tz_all_order` (
  `id` varchar(20) NOT NULL COMMENT '流水id',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生id',
  `total_money` varchar(50) DEFAULT NULL COMMENT '订单总金额',
  `status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `create_by` varchar(64) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '变更人',
  `update_date` datetime DEFAULT NULL COMMENT '变更时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PK_TLR_GRAB_RECORDS_ID` (`id`),
  KEY `IDX_TLR_GRAB_RECORDS_UID` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单归档表';

-- ----------------------------
-- Records of tz_all_order
-- ----------------------------
INSERT INTO `tz_all_order` VALUES ('1471798387835003', '1471099706035002', '1', '1', 'admin', '2016-08-22 01:04:25', 'admin', '2016-08-22 01:04:25', '');
INSERT INTO `tz_all_order` VALUES ('1471858358072000', '1471099706035002', '1', '1', 'admin', '2016-08-22 17:33:38', 'admin', '2016-08-22 17:33:38', '');
INSERT INTO `tz_all_order` VALUES ('1471880712115002', '1471458784422001', '4.0', '2', 'studentId:1471458784422001', '2016-08-22 23:52:50', 'admin', '2016-08-23 02:10:50', '');
INSERT INTO `tz_all_order` VALUES ('1471885348089003', '1471458784422001', '16.0', '2', 'studentId:1471458784422001', '2016-08-23 01:08:38', 'admin', '2016-08-23 02:19:16', '');

-- ----------------------------
-- Table structure for tz_all_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tz_all_order_detail`;
CREATE TABLE `tz_all_order_detail` (
  `id` varchar(20) NOT NULL COMMENT '订单id',
  `class_id` varchar(20) NOT NULL COMMENT '班级id',
  PRIMARY KEY (`id`,`class_id`),
  KEY `IDX_TLR_GRAB_RECORDS_UID` (`class_id`),
  KEY `IDX_PK_TLR_GRAB_RECORDS_ID` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细归档表';

-- ----------------------------
-- Records of tz_all_order_detail
-- ----------------------------
INSERT INTO `tz_all_order_detail` VALUES ('1471858358072001', '1');
INSERT INTO `tz_all_order_detail` VALUES ('1471880712115002', '1470938179177003');
INSERT INTO `tz_all_order_detail` VALUES ('1471885348089003', '1470938179177004');
INSERT INTO `tz_all_order_detail` VALUES ('1471885348089003', '1471458784422003');

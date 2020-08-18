CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `sex` varchar(2) NOT NULL COMMENT '性别',
  `age` int(3) NOT NULL COMMENT '年龄',
  `phone` varchar(11) NOT NULL DEFAULT '0' COMMENT '手机',
  `deliveryaddress` varchar(200) DEFAULT NULL COMMENT '收货地址',
  `adddate` int(11) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `user_id` bigint(20) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'menu', '0');
INSERT INTO `menu` VALUES ('2', 'submenu', '1');

DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `card_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES ('1', 'this is cardno');

-- ----------------------------
-- Function structure for `fristPinyin`
-- ----------------------------
DROP FUNCTION IF EXISTS `fristPinyin`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `fristPinyin`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
    DECLARE V_RETURN VARCHAR(255);
    SET V_RETURN = ELT(INTERVAL(CONV(HEX(left(CONVERT(P_NAME USING gbk),1)),16,10),
        0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,
        0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,
        0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),   
    'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z');
    RETURN V_RETURN;
END
;;
DELIMITER ;

CREATE TABLE if not exists `statistics` (
  `usertotal` bigint(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 触发器测试
DROP TRIGGER IF EXISTS tri_countUserTotal;
CREATE TRIGGER tri_countUserTotal AFTER
INSERT ON USER
FOR EACH ROW BEGIN DECLARE c int;
SET c =
  (SELECT count(*)
   FROM USER);
UPDATE STATISTICS
SET usertotal = c; END;
update statistics set usertotal=0;

CREATE DEFINER=`root`@`%` PROCEDURE `insert_touser`(in start int(10),in max_num int(10))
begin
declare i int default 0;
 -- set autocommit =0 把autocommit设置成0 不自动提交，循环完统一提交
 set autocommit = 0;  
 repeat
 set i = i + 1;
 insert into user(name,phone,adddate) values ((start+i),'test',UNIX_TIMESTAMP(NOW()));
  until i = max_num
 end repeat;
   commit;
 end
 -- 添加1w个用户
call insert_touser(1,10000);
-- 锁表
lock tables user read;
lock tables user write;
unlock tables;

-- 添加card的外键约束
ALTER TABLE `card` DROP FOREIGN KEY `user_id_fk`;
ALTER TABLE `card` ADD CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- 测试onetoone关系表
CREATE TABLE `t_crud_demo` (
  `id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `sub_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_crud_demosub` (
  `id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `user`
ADD COLUMN `version`  int UNSIGNED NULL comment "乐观锁版本控制用" AFTER `comments`;

-- order by field自定义排序
select * from user where id in(6,1,5,2,3,4) order by FIELD(id,6,1,5,2,3,4);

-- 联合主键测试
DROP TABLE IF EXISTS `user_favorites`;
CREATE TABLE `user_favorites` (
  `user_id` bigint(20) NOT NULL,
  `goods_id` bigint(20) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`goods_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 同一会话下先执行insert aaa
begin;
insert into user(name) values("aaa");
-- 再begin开事务执行insert bbb，那么此时之前的事务会自动提交
begin;
insert into user(name) values("bbb");
rollback;
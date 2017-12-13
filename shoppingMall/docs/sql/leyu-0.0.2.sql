ALTER TABLE `mutual_dev`.`ly_u_user`
ADD COLUMN `wx_open_id` VARCHAR(45) NULL AFTER `birthday`;

>> huanglei 2017/8/8 17:03
CREATE TABLE `ly_u_user_policy_relation` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`user_id`  int(11) NULL DEFAULT NULL COMMENT '注册用户id' ,
`policy_code`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '保单码' ,
`product_code`  varchar(100) NULL ,
`relation`  int(3) NULL DEFAULT NULL COMMENT '关系(0本人、1爸爸、2妈妈、3爱人、4哥哥、5姐姐、6弟弟、7妹妹、8朋友、9亲人)' ,
`relation_way`  int(3) NULL DEFAULT NULL COMMENT '关联方式0购买1关联' ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`modify_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `ly_u_user_policy`
ADD COLUMN ` invite_user`  int(11) NULL COMMENT '邀请人' AFTER `deleted`;


CREATE TABLE `ly_u_invite` (
`id`  int(11) NOT NULL  AUTO_INCREMENT COMMENT 'id' ,
`user_id`  int(11) NOT NULL COMMENT '用户id' ,
`product_code`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品code' ,
`type`  int(3) NOT NULL DEFAULT 1 COMMENT '类型：1互助' ,
` invite_code`  varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邀请码（8位uuid)' ,
`expiring_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间' ,
`status`  int(2) NOT NULL DEFAULT 1 COMMENT '0不可用1可用' ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
`modify_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `ly_u_user`
ADD COLUMN `wx_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信昵称' AFTER `wx_openId`;




ALTER TABLE `ly_u_finance_recharge`
CHANGE COLUMN `io_order_id` `io_order_id` VARCHAR(64) NULL DEFAULT NULL COMMENT '外部订单号' ;

ALTER TABLE `ly_u_finance_account`
ADD COLUMN `redbag_money` DOUBLE(15,6) not NULL DEFAULT '0.000000'  COMMENT '累计使用红包' AFTER `deposit_money`;


ALTER TABLE `ly_u_user_policy_flow`
ADD COLUMN `redbag` DOUBLE(15,6) NULL COMMENT '使用的红包金额' AFTER `money`;

ALTER TABLE `ly_u_user_policy_account`
ADD COLUMN `deposit_money` DOUBLE(15,6) not null DEFAULT '0.00' COMMENT '累计充值金额' AFTER `modify_time`,
ADD COLUMN `redbag_money` DOUBLE(15,6) not null DEFAULT '0.00' COMMENT '累计充值的红包金额' AFTER `deposit_money`;


ALTER TABLE `ly_p_mutual_product_account`
ADD COLUMN `redbag_money` DOUBLE(15,6) not NULL DEFAULT '0.000000' COMMENT '累计红包充值金额' AFTER `deposit_money`;

ALTER TABLE `ly_p_mutual_product_account_flow`
ADD COLUMN `redbag` DOUBLE(15,6) NULL COMMENT '红包金额' AFTER `money`;


>> huanglei 2017/8/15 10：40
ALTER TABLE `ly_u_user_policy`
MODIFY COLUMN `deleted`  int(1) NOT NULL DEFAULT 1 COMMENT '删除0未删,1已删' AFTER `modify_time`;
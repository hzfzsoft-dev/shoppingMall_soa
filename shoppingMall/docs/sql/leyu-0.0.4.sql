>> fangchao 2017/09/06 11:30
DROP TABLE IF EXISTS ly_h_help_photo;
CREATE TABLE `ly_h_help_photo`
(
  `id`                        INT(11) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `target_code`          		  VARCHAR(200) NOT NULL COMMENT '关联目标code',
  `target_type`           		INT(1) NOT NULL COMMENT '关联目标类型1,申请2,公示',
  `photo_type`           		  INT(1) NOT NULL COMMENT '图片类型1,证件照2,门诊病历3,住院病历4,其他材料',
  `path`                      VARCHAR(200) NOT NULL COMMENT '图片路径地址',
  `photo_name`        		    VARCHAR(200) NOT NULL COMMENT '图片名称',
  `create_time`               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   PRIMARY KEY (`id`)
  )comment '互助图片';


DROP TABLE IF EXISTS ly_h_help_apply_audit;
CREATE TABLE `ly_h_help_apply_audit`
(
  `id`                          		    INT(11) NOT NULL AUTO_INCREMENT COMMENT '审核id',
  `apply_code`                          VARCHAR(200) NOT NULL COMMENT '互助申请id',
  `audit_type`                          INT(1) NULL COMMENT '类型1平台出审核2tpa审核3公式4划款',
  `status`                          	  INT(1) NULL COMMENT '0不通过1通过',
  `opinion`                             VARCHAR(2000) NULL COMMENT '审核意见',
  `remark`                          	  VARCHAR(500) NULL COMMENT '备注',
  `create_time`                         TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
  )comment '互助申请审核';

  DROP TABLE IF EXISTS ly_h_help_apply;
  CREATE TABLE  `ly_h_help_apply` (
  `id` 											  INT NOT NULL AUTO_INCREMENT COMMENT '互助申请表id',
  `apply_code` 							  VARCHAR(200) NOT NULL COMMENT '互助编号',
  `product_code` 							VARCHAR(200) NOT NULL COMMENT '计划编号',
  `policy_code` 							VARCHAR(200) NOT NULL COMMENT '表单编号',
  `user_id` 									INT(11) NULL COMMENT '申请人用户id',
  `phone` 										VARCHAR(45) NOT NULL COMMENT '申请人手机号',
  `policy_phone`              VARCHAR(45) NULL COMMENT '受益人手机号',
  `join_age` 									INT(3) NULL COMMENT '申请年龄',
  `join_day_age`              INT(3) NULL COMMENT '加入年龄不满一岁的天数',
  `card_holder` 							VARCHAR(200) NOT NULL COMMENT '持卡人',
  `bank_name` 							  VARCHAR(200) NOT NULL COMMENT '银行名称',
  `card_number` 							VARCHAR(200) NOT NULL COMMENT '银行卡号',
  `account_bank` 						  VARCHAR(200) NOT NULL COMMENT '开户行',
  `apply_describe` 						VARCHAR(2000) NOT NULL COMMENT '互助申请描述',
  `status` 										INT(1) NOT NULL DEFAULT 1 COMMENT '申请互助状态0,不通过,1,已提交,2,平台审核通过3,tpa审核通过4,公示通过5,划款',
  `create_time` 							TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` 							TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` 									INT(1) NOT NULL DEFAULT 0 COMMENT '0,未删1,已删',
  `remark` 									  VARCHAR(500) NULL COMMENT '备注',
  `create_type` 							INT(1) NOT NULL DEFAULT 0 COMMENT '创建类型0用户创建1运营创建',
  `actor_id` 							    INT(11) NULL COMMENT '运营后台创建人操作人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_apply_code` (`apply_code`)
  )comment '互助申请';



>> huanglei 2017/09/06 11:30


ALTER TABLE `ly_u_relation` ADD COLUMN `sex` int(2) DEFAULT 3 COMMENT '1女2男3未知' AFTER `identity_card`;


update ly_u_relation set relation =1 where relation in(1,2,3,4,5,6,7,9);
update ly_u_relation set relation=2 where relation=8;

update ly_u_user_policy_relation set relation=1 where relation in(1,2,3,4,5,6,7,9);
update ly_u_user_policy_relation set relation=2 where relation =8;

ALTER TABLE `ly_p_mutual_product` ADD COLUMN `scope` int DEFAULT 3 COMMENT '1女2男3不限制' AFTER `seq`;

update ly_u_relation set sex =1 where (SUBSTR(identity_card,17,1)%2)=0;
update ly_u_relation set sex=2 where (SUBSTR(identity_card,17,1)%2)=1;

update ly_p_mutual_product set scope=3 where product_code ='A1';
update ly_p_mutual_product set scope=1 where product_code='B0';
update ly_p_mutual_product set scope=1 where product_code='C0';

ALTER TABLE `ly_u_user_policy` ADD COLUMN `birthday` datetime DEFAULT NULL COMMENT '出生日期' AFTER `pause_time`;
UPDATE ly_u_user_policy set birthday=(SUBSTR(identity_card,7,8)) where length(identity_card)=18;
ALTER TABLE ly_u_user_policy_account ADD COLUMN `total_annual_fee` double(15,6) NOT NULL DEFAULT 0.00000 COMMENT '总计年费扣除' ;
ALTER TABLE `ly_p_mutual_product_account` ADD COLUMN `total_annual_fee` double(15,6) NOT NULL DEFAULT 0.00000 COMMENT '总计年费扣除';


>> chengqing 2017/09/14 16:00

CREATE TABLE `ly_h_case_publicity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公示id',
  `apply_code` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '互助凭证编号',
  `policy_code` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '用户保单编号',
  `assessed_amount` double(15,6) DEFAULT NULL COMMENT '分摊金额',
  `help_amount` double(15,6) DEFAULT NULL COMMENT '互助金额',
  `join_count` int(10) DEFAULT NULL COMMENT '参与人数',
  `help_brief` text CHARACTER SET utf8 COMMENT '互助事件概况',
  `survey_conclusion` text CHARACTER SET utf8 COMMENT '调查过程及结论',
  `survey_conclusion_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'TPA调查报告',
  `publicity_feedback` text CHARACTER SET utf8 COMMENT '公示反馈',
  `publicity_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '公示状态(0待公示,1公示中,2结束公示，3不予互助，4已划款）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作人',
  `debit_status` tinyint(1) DEFAULT '0' COMMENT '扣款状态（0代扣款，1扣款中，2已扣款）',
  `real_name` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `user_age` int(3) DEFAULT NULL COMMENT '年龄',
  `user_phone` varchar(11) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `user_card` varchar(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号码',
  `bank_card` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行卡号',
  `card_holder` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '持卡人姓名',
  `bank_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '所属银行',
  `bank_open_name` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT '开户行名称',
  `product_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '互助计划',
  `join_time` timestamp NULL DEFAULT NULL COMMENT '加入时间',
  `job_time` timestamp NULL DEFAULT NULL COMMENT '生效时间',
  `apply_time` timestamp NULL DEFAULT NULL COMMENT '申请时间',
  `real_help_amount` double(15,6) DEFAULT NULL COMMENT '实际扣款总金额',
  `debit_amount` double(15,6) DEFAULT NULL COMMENT '实际欠款总金额',
  `join_day_age` int(3) DEFAULT NULL COMMENT '加入年龄不满一岁的天数',
  `debit_product_amount_status` int(1) DEFAULT '0' COMMENT '扣除计划总账的状态(0未扣款，1已扣款)',
  `submit_time` timestamp NULL DEFAULT NULL,
  `wechat_brief` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `ly_h_policy_debit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `policy_code` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '保单编号',
  `apply_code` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '互助申请编号',
  `member_id` int(11) DEFAULT NULL COMMENT '会员编号',
  `rest_money` double(15,6) DEFAULT NULL COMMENT '保单余额',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `debit_status` tinyint(1) DEFAULT NULL COMMENT '扣款状态(0未扣款，1扣款）',
  `debit_remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '扣款备注',
  PRIMARY KEY (`id`)
);

ALTER TABLE `ly_u_user_policy_flow`
ADD COLUMN `debit_apply_code`  varchar(500) NULL DEFAULT NULL COMMENT '互助申请编号' ;

ALTER TABLE `ly_u_user_policy`
ADD COLUMN `pause_time`  timestamp NULL DEFAULT NULL COMMENT '暂停时间' ;

ALTER ALTER `ly_f_fishpond_task`
ADD COLUMN `random_grams_max`  int(5) NULL DEFAULT NULL COMMENT '随机克数上限' ,
ADD COLUMN `random_grams_min`  int(5) NULL DEFAULT NULL COMMENT '随机克数下限' ;

ALTER TABLE `ly_h_case_publicity`
ADD UNIQUE INDEX `idx_helpcode_applycode` (`apply_code`) USING BTREE ;

>>rqq 2017/09/15 10:00
>>>微信菜单维护
ALTER  TABLE 'ly_wx_wechat_menu'
Add COLUMN 'WeChat_content' varchar(256)  COLLATE utf8mb4_bin DEFAULT NULL COMMENT '保存微信推送过来的数据'
Add COLUMN 'deleted' int(1) DEFAULT '0' COMMENT '0：未删除， 1：删除'

ALTER TABLE ly_wx_wechat_menu CHANGE  key  key_click  varchar(128);

>>>图文推送消息
ALTER  TABLE 'ly_wx_article'
Add COLUMN 'deleted' int(1) DEFAULT '0' COMMENT '0：未删除， 1：删除'

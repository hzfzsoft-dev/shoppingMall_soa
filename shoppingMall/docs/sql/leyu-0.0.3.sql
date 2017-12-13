>> fangchao 2017/8/29 16:30
ALTER TABLE `ly_p_mutual_product`
ADD COLUMN `seq` INT(5) NOT NULL DEFAULT 0 COMMENT '排序字段' AFTER `annual_fee`;


>> huanglei 2017/8/30
update dream_m_sms_template set is_delete=0 where type=3 and template_type=1;
INSERT INTO dream_m_sms_template (name,type,template_type,template,extra_param,is_delete)VALUES('支付通道通知',3,4,'[乐鱼]支付通道#name#连续#time#失败,请及时处理','{"templateId":43918}',1);
INSERT INTO dream_m_sms_template (name,type,template_type,template,extra_param,is_delete)VALUES('注册验证码',3,1,'#code#为您的验证码,十分钟内有效','{"templateId":"43977"}',1);

>> chengqing 2017/8/30
CREATE TABLE `ly_wx_wechat_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 NOT NULL COMMENT '菜单标题',
  `type` varchar(5) CHARACTER SET utf8 DEFAULT NULL COMMENT '菜单的响应动作类型，目前有click、view两种类型',
  `key` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT 'click类型必须(消息接口推送)',
  `url` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT 'view类型必须(网页链接)',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `parent_id` tinyint(4) DEFAULT NULL COMMENT '父类id',
  `menu_sort` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `ly_wx_wechat_menu` VALUES (1, '加入互助', 'view', NULL, 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89ad5de8e51c10aa&redirect_uri=http://ly.leyuzhu.com/user/weixin/callback.do?callback=/&response_type=code&scope=snsapi_base&state=4563#wechat_redirect', '2017-8-29 17:34:37', NULL, 1);
INSERT INTO `ly_wx_wechat_menu` VALUES (2, '鱼塘', 'view', NULL, 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89ad5de8e51c10aa&redirect_uri=http://ly.leyuzhu.com/user/weixin/callback.do?callback=/pond&response_type=code&scope=snsapi_base&state=4563#wechat_redirect', '2017-8-29 17:34:38', NULL, 2);
INSERT INTO `ly_wx_wechat_menu` VALUES (3, '联系我们', 'click', 'CONTACT_US', NULL, '2017-8-29 17:35:59', 8, 1);
INSERT INTO `ly_wx_wechat_menu` VALUES (4, '我的红包', 'view', NULL, 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89ad5de8e51c10aa&redirect_uri=http://ly.leyuzhu.com/user/weixin/callback.do?callback=/mobi/myRedPocket/myRedPocket&response_type=code&scope=snsapi_base&state=4563#wechat_redirect', '2017-8-29 17:36:00', 8, 2);
INSERT INTO `ly_wx_wechat_menu` VALUES (5, '邀请有奖', 'view', NULL, 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89ad5de8e51c10aa&redirect_uri=http://ly.leyuzhu.com/user/weixin/callback.do?callback=/mobi/requestGift/requestGifts&response_type=code&scope=snsapi_base&state=4563#wechat_redirect', '2017-8-29 17:36:01', 8, 3);
INSERT INTO `ly_wx_wechat_menu` VALUES (6, '我的计划', 'view', NULL, 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89ad5de8e51c10aa&redirect_uri=http://ly.leyuzhu.com/user/weixin/callback.do?callback=/mine&response_type=code&scope=snsapi_base&state=4563#wechat_redirect', '2017-8-29 17:36:02', 8, 4);
INSERT INTO `ly_wx_wechat_menu` VALUES (7, '充值', 'view', NULL, 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89ad5de8e51c10aa&redirect_uri=http://ly.leyuzhu.com/user/weixin/callback.do?callback=/mobi/myPlan/myPlans&response_type=code&scope=snsapi_base&state=4563#wechat_redirect', '2017-8-29 17:36:03', 8, 5);
INSERT INTO `ly_wx_wechat_menu` VALUES (8, '个人中心', NULL, NULL, NULL, '2017-8-30 18:58:42', NULL, 3);






## 前言

-   所有的表都需要遵循 http://dev.esongbai.xyz/#!standard/code.md 数据库规范
-   dream 开头 是之前系统遗留的表接口
        dream_a 是管理后台的相关表
        dream_m 是短信,消息登录相关的表
-   ly 开头的是 业务表,也是我们新表.目的是缩写,检查表的长度 ly 是 leyu 的缩写.
        ly_a :  管理后台相关. a = admin
        ly_f :  f = fish , 是鱼塘相关的表
        ly_p :  p = product ,是计划相关的表
        ly_u :  u = user  是用户 相关的表
            ly_u_finance:   用户资金相关的.
            ly_u_user_policy : 用户保单相关的.
        ly_wx : wx 微信的功能.




## 表

|  表名称  |  表的作用  |
| ------ | ------ |
|  dream_a_actor   |  后台用户表  |
|  dream_a_appversion  |  APP版本控制表  |
|  dream_a_authority   |  角色表  |
|  dream_a_authorization   |  用户角色表 |
|  dream_a_dictionary  | 字典表  |
|  dream_a_security_resource   |  菜单页面元素表  |
|  dream_a_security_resource_assignmen     | 角色权限表  |
|  dream_a_security_resource_v     |  待删  |
|  dream_a_system_config   |  系统参数表  |
|  dream_m_msg     |  消息表  |
|  dream_m_msg_shield  |  屏蔽消息表  |
|  dream_m_sms_channel     |  消息渠道表  |
|  dream_m_sms_template    |  消息模板表  |
|  dream_m_sms_template_sender     |  短信模板发送配置  |
|  ly_a_wx_templat     |  微信模板消息配置表  |
|  ly_f_feed_food_flow     |  鱼塘喂养记录流水  |
|  ly_f_fish   |  乐鱼配置  | 
|  ly_f_fishpond_task  |  鱼塘任务初始化配置  |
|  ly_f_fish_stage     |  乐鱼阶段  |
|  ly_f_fish_talk  |  乐鱼文案  |
|  ly_f_food_details   |  用户任务获取鱼粮详情表  |
|  ly_f_food_statistics    |  鱼塘用户鱼粮统计表  |
|  ly_f_user_fish  |  用户乐鱼鱼塘  |
|  ly_p_mutual_product     |  互助计划产品  |
|  ly_p_mutual_product_account     |  互助计划产品账户  |
|  ly_p_mutual_product_account_flow    |  互助计划产品账户流水  |
|  ly_p_mutual_product_account_stat    |  互助计划产品账户统计  |
|  ly_u_finance_account    |  用户账户  |
|  ly_u_finance_bank   | 银行信息  |
|  ly_u_finance_channel_pay    |  渠道支付方式表  |
|  ly_u_finance_count  |  用户资金统计表  |
|  ly_u_finance_flow   |  用户流水表  |
|  ly_u_finance_io     | 	支付外部记录IO表  |
|  ly_u_finance_pay_platform   |  支付平台表 |
|  ly_u_finance_pay_platform_bank  |  支付平台与银行关系表  |
|  ly_u_finance_recharge   |  用户充值表  |
|  ly_u_finance_recharge_excption  |  用户充值异常表  |
|  ly_u_invite     |  用户邀请码表  |
|  ly_u_member     |  会员表  |
|  ly_u_relation   |  联系人表  |
|  ly_u_user   |  用户表  |
|  ly_u_user_policy    |  用户保单  |
|  ly_u_user_policy_account    |  保单账户  |
|  ly_u_user_policy_flow   |  保单流水  |
|  ly_u_user_policy_relation   |  关联保单关系表  |
|  ly_u_user_statistics    |  用户统计表-后台统计用  |
|  ly_wx_templat_message   |  微信模板消息配置表  |
|  ly_wx_user  |  微信用户OPENID 记录表  |
|  ly_wx_user_message  |  微信消息表 |
SELECT * FROM ybjf.t_user_info t where t.phone_no="18767158994";

ALTER TABLE `t_user_info`
ADD COLUMN `show_red_bag`  int(1)  NULL DEFAULT 0 COMMENT '展示新用户注册登入红包0,未展示1,展示' ;

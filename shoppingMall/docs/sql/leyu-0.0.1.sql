>> huanglei 2017/7/24 17:49
ALTER TABLE `ly_u_user_policy`
ADD COLUMN `deleted`  int(2) NULL DEFAULT 1 COMMENT '是否删除 0删除1可见' AFTER `modify_time`;
## 模块分类.
|  模块  |  服务名  |  端口  |  作用  |
| -------- | -------- | -------- | -------- |
| mutual-config |  |  |  用于添加一些系统配置用的参数  |
| mutual-model |  |  | 常用的model类写入的地方 |
| mutual-util |  |  | 常用的帮助类|
| mutual-service |  |  | 常用的service 类写入的地方 |
| mutual-admin | admin | 3001 | 后台管理系统相关的功能 |
| mutual-help |  help | 3003 | 互助项目的主项目, 用户是互助功能的实现. |
| mutual-user | user | 3002 | 用户相关的功能写入的地方 |
| mutual-msg | msg | 3004 | 用户相关的功能写入的地方 |
| mutual-wx | wx | 3005 | 微信推送相关的功能 |
| mutual-fish | fish | 3006 | 鱼塘 |


## 表
-   参见 docs/tables.md 表说明.


## 其他
#### mutual-service 中主要是写一些常用的service ,也就是说可能被其他模块调用到得service , 如果不能被外部调用不要写在里面
-   这里模块里面的所有的dao 不能直接被外部调用, 只能提供service 调用的方式
-   这里的所有的类型需要以Com开头, 表示功能的service或者dao, 

#### mutual-config 中主要是写一个主要的配置文件, 
-   这里的配置文件不能写入系统属性配置.
-   这里的配置禁止项目管理人员之外的人员修改.如果修改了禁止上传.


#### docs 中写入一些上线主要注意的注意事项 或者 上线需要准备的sql,数据修复的sql 等 
-   参见 docs/README.md
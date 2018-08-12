

-- 数据库初始化脚本


-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
use seckill;


--创建秒杀表

CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '商品数量',
`start_time` timestamp NOT NULL COMMENT '秒杀开启时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据

insert into seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphoneX',100,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
  ('500元秒杀Ipad',200,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
  ('200元秒杀小米8',300,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
  ('100元秒杀meizu16',400,'2018-11-01 00:00:00','2018-11-02 00:00:00');

-- 秒杀成功明细表
-- 用户登录相关的信息
CREATE TABLE success_kill(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1:无效 0：成功 1：已付款 2：已发货',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_iphone),
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';


-- 在数据库中建表失败时提示第二列timestamp出错的话，执行虾苗这条语句
SET @@sql_mode = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION'

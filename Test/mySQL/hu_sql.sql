--在shop表的substation字段后添加year_fee字段
alter table shop add column year_fee int(11) DEFAULT '0' COMMENT '年费' after substation; 

--新加表
--100.161 测试库中 DB为order.marry_order --
drop table if exists `marry_order`; 
CREATE TABLE `marry_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增量',
  `order_id` int(11) NOT NULL DEFAULT '0' COMMENT 'groupOrder表中ID',
  `marry_id` int(11) DEFAULT '0' COMMENT 'php传过来订单ID',
  `marry_type` int(2) DEFAULT '0' COMMENT '订单类型1=album(相册),2=video(视屏)',
  `marry_type_id` int(11) DEFAULT '0' COMMENT '相册ID或是视屏ID',
  `marry_status` int(2) DEFAULT '0' COMMENT '预约单状态 1-下定 2-有效 3-无效',
  `user_sex` int(2) DEFAULT '0' COMMENT '预约用户性别 0=male,1=female',
  `pmt_title` varchar(500) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '促销标题多个的话用#号分开',
  `pmt_content` varchar(500) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '促销内容多个的话用#号分开',
  `is_shop_see` int(2) DEFAULT '0' COMMENT '商家是否已经查看过1=Y-是 0=N-否（主要是手机号码 ,为以后收费准备）',
  `tjjid2` varchar(900) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'BI统计字段',
  `tjjr1` varchar(900) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'BI统计字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结婚预约单表'


--在 order库中 booking_order表的order_id字段后添加category_id字段
alter table booking_order add column category_id int(11) DEFAULT '0' COMMENT '类目ID' after order_id;

--预约单日志表
drop table if exists `booking_log`;
CREATE TABLE `booking_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) not null DEFAULT 0 COMMENT 'groupOrder表中ID',
  `category_id` int(11) DEFAULT 0 COMMENT '预约单-类目id',
  `opt_type` int(2) DEFAULT 0 COMMENT  '操作类型ID:1=报名,2=客服响应,3=客服外呼,4=侍分配,5=分配,6=商户接单',
  `opt_time` timestamp NULL DEFAULT NULL COMMENT '预约单操作时间',
  `shop_id` int(11) DEFAULT 0 COMMENT  '店铺ID',
  `new_ordergroup_id` varchar(50) DEFAULT "" COMMENT  '分单后订单ID',
  PRIMARY KEY (`id`),
  KEY `opt_type` (`opt_type`),
  KEY `opt_time` (`opt_time`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
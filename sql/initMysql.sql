CREATE TABLE `TPL_USER_T` (
  `id` bigint(20) NOT null AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  `user_name` varchar(50) DEFAULT  null COMMENT '用户名',
  `full_name` varchar(50) DEFAULT  null COMMENT '用户名',
  `pwd` VARBINARY(255) DEFAULT null  COMMENT '密码',
   `salt` VARBINARY(255) DEFAULT null  COMMENT '盐值',
  `email` varchar(100) DEFAULT null  COMMENT '电子邮箱',
  `phone` varchar(100) DEFAULT null  COMMENT '电话',
  `state` int(8) DEFAULT null  COMMENT '状态',
  `creation_date` datetime DEFAULT null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT null COMMENT '创建人',
  `last_update_date` datetime DEFAULT null  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_updated_by` varchar(100) DEFAULT null COMMENT '更新人',
  UNIQUE KEY `idx_username` (`user_name`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

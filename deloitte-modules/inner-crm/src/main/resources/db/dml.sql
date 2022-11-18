-- 港股信息表stock_thk_info
alter table stock_thk_info add column `company_english` varchar(1000) DEFAULT NULL COMMENT '公司英文名称';
alter table stock_thk_info add column `statutory_capital` decimal(32,16) DEFAULT NULL COMMENT '法定股本(百万元)';
alter table stock_thk_info add column `currency` varchar(50) DEFAULT NULL COMMENT '币种';
alter table stock_thk_info add column `ssuing_equity` decimal(32,16) DEFAULT NULL COMMENT '发行股本(百万元)';
alter table stock_thk_info add column  `chairman_group` varchar(100) DEFAULT NULL COMMENT '集团主席';
alter table stock_thk_info add column `company_secretary` varchar(100) DEFAULT NULL COMMENT '公司秘书';
alter table stock_thk_info add column `office_address` varchar(255) DEFAULT NULL COMMENT '办公地址';
alter table stock_thk_info add column `company_web_site` varchar(255) DEFAULT NULL COMMENT '公司网址';
alter table stock_thk_info add column  `email_address` varchar(255) DEFAULT NULL COMMENT '电邮地址';
alter table stock_thk_info add column `phone` varchar(20) DEFAULT NULL COMMENT '电话';
alter table stock_thk_info add column `fax` varchar(100) DEFAULT NULL COMMENT '传真';
alter table stock_thk_info add column `junction` varchar(50) DEFAULT NULL COMMENT '年结日';
alter table stock_thk_info add column `bel_wind` varchar(50) DEFAULT NULL COMMENT '所属Wind行业';
alter table stock_thk_info add column  `delisting_date` date DEFAULT NULL COMMENT '摘牌日期';
alter table stock_thk_info add column  `termination_type` varchar(20) DEFAULT NULL COMMENT '终止上市类型';
alter table stock_thk_info add column  `hs_lndustry` varchar(20) DEFAULT NULL COMMENT '所属行业(HS)';




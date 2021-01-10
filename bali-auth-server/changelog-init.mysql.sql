--liquibase formatted sql

--changeset Petty:1610268206696-1
CREATE TABLE uc_auth_client_details (id VARCHAR(128) NOT NULL, client_id VARCHAR(32) NOT NULL COMMENT '客户端名称', resource_ids VARCHAR(256) NULL COMMENT '客户端资源集', client_secret VARCHAR(256) NULL COMMENT '客户端密钥', scope VARCHAR(256) NULL COMMENT '域', authorized_grant_types VARCHAR(256) NULL COMMENT '授权模式', web_server_redirect_uri VARCHAR(256) NULL COMMENT 'Web Client回调URL', authorities VARCHAR(256) NULL COMMENT '权限信息', access_token_validity INT NULL COMMENT 'token有效时间，单位毫秒', refresh_token_validity INT NULL COMMENT '刷新token有效时间，单位毫秒', additional_information VARCHAR(4096) NULL COMMENT '附加信息', auto_approve VARCHAR(1000) NULL COMMENT '自动授权', del_flag TINYINT(3) DEFAULT 0 NULL COMMENT '删除标记 0 未删除 1 删除', creator VARCHAR(128) NULL COMMENT '创建人', create_time datetime NULL COMMENT '创建时间', modifier VARCHAR(128) NULL COMMENT '修改人', modify_time datetime NULL COMMENT '修改时间', tenant_id VARCHAR(255) NULL COMMENT '租户ID', CONSTRAINT PK_UC_AUTH_CLIENT_DETAILS PRIMARY KEY (id)) COMMENT='终端信息';
ALTER TABLE uc_auth_client_details COMMENT = '终端信息';

--changeset Petty:1610268206696-2
CREATE TABLE uc_role (id VARCHAR(128) NOT NULL, `role` VARCHAR(128) NOT NULL COMMENT '角色', role_name VARCHAR(128) NOT NULL COMMENT '角色名称', `description` VARCHAR(400) NULL COMMENT '描述', sort SMALLINT NULL COMMENT '排序号', status TINYINT(3) DEFAULT 1 NOT NULL COMMENT '状态 1有效 0无效 默认为1', tenant_id VARCHAR(255) NULL COMMENT '租户ID', del_flag TINYINT(3) UNSIGNED DEFAULT 0 NULL COMMENT '删除标记 0 未删除 1 删除', creator VARCHAR(128) NULL COMMENT '创建人', create_time datetime NULL COMMENT '创建时间', modifier VARCHAR(128) NULL COMMENT '修改人', modify_time datetime NULL COMMENT '修改时间', CONSTRAINT PK_UC_ROLE PRIMARY KEY (id)) COMMENT='用户角色';
ALTER TABLE uc_role COMMENT = '用户角色';

--changeset Petty:1610268206696-3
CREATE TABLE uc_tenant (id VARCHAR(128) DEFAULT '' NOT NULL, tenant_id VARCHAR(128) NOT NULL COMMENT '租户ID', tenant_name VARCHAR(128) NOT NULL COMMENT '租户名', link_man VARCHAR(32) NOT NULL COMMENT '联系人', contact_number VARCHAR(32) NOT NULL COMMENT '联系电话', address VARCHAR(255) NULL COMMENT '地址', remark VARCHAR(255) NULL COMMENT '备注', email VARCHAR(128) NULL COMMENT '电子邮件', status TINYINT(3) DEFAULT 0 NOT NULL COMMENT '是否有效 0 无效 1 有效', del_flag TINYINT(3) UNSIGNED DEFAULT 0 NULL COMMENT '删除标记 0 未删除 1 删除', creator VARCHAR(128) NULL COMMENT '创建人', create_time datetime NULL COMMENT '创建时间', modifier VARCHAR(128) NULL COMMENT '修改人', modify_time datetime NULL COMMENT '修改时间', CONSTRAINT PK_UC_TENANT PRIMARY KEY (id)) COMMENT='租户信息';
ALTER TABLE uc_tenant COMMENT = '租户信息';

--changeset Petty:1610268206696-4
CREATE TABLE uc_user (id VARCHAR(128) DEFAULT '' NOT NULL, login_id VARCHAR(255) NULL COMMENT '用户登录名', password VARCHAR(255) NULL COMMENT '账号密码', tenant_id VARCHAR(255) NULL COMMENT '租户ID', open_id VARCHAR(255) NULL COMMENT '第三方OpenID', union_id VARCHAR(255) NULL COMMENT '第三方组织ID', user_channel VARCHAR(255) DEFAULT 'web' NOT NULL COMMENT '用户所属渠道', status TINYINT(3) DEFAULT 0 NOT NULL COMMENT '是否有效 0：无效 1：有效 8：凭证无效 9：锁定', del_flag TINYINT(3) UNSIGNED DEFAULT 0 NULL COMMENT '删除标记 0 未删除 1 删除', creator VARCHAR(128) NULL COMMENT '创建人', create_time datetime NULL COMMENT '创建时间', modifier VARCHAR(128) NULL COMMENT '修改人', modify_time datetime NULL COMMENT '修改时间', CONSTRAINT PK_UC_USER PRIMARY KEY (id)) COMMENT='用户注册信息';
ALTER TABLE uc_user COMMENT = '用户注册信息';

--changeset Petty:1610268206696-5
CREATE TABLE uc_user_info (id VARCHAR(128) DEFAULT '' NOT NULL, user_id VARCHAR(128) NOT NULL COMMENT '用户ID', user_name VARCHAR(128) NULL COMMENT '姓名', nick_name VARCHAR(128) NULL COMMENT '用户昵称', user_sex TINYINT(3) NULL COMMENT '性别', user_born datetime NULL COMMENT '生日', user_avatar VARCHAR(128) NULL COMMENT '用户头像', email VARCHAR(128) NULL COMMENT '电子邮件', user_address VARCHAR(400) NULL COMMENT '居住地址', mobile_tel VARCHAR(128) NULL COMMENT '移动电话', phone_tel VARCHAR(96) NULL COMMENT '用户联系电话', user_iden_type VARCHAR(64) NULL COMMENT '用户证件类型', user_iden VARCHAR(128) NULL COMMENT '证件ID', `organization` VARCHAR(255) NULL COMMENT '工作单位', education VARCHAR(255) NULL COMMENT '学历', positional_title VARCHAR(255) NULL COMMENT '职称', political_status VARCHAR(64) NULL COMMENT '政治面貌', graduated_school VARCHAR(255) NULL COMMENT '毕业学校', graduated_year VARCHAR(128) NULL COMMENT '毕业年份', major VARCHAR(128) NULL COMMENT '所学专业', verified TINYINT(3) DEFAULT 0 NULL COMMENT '是否实名认证', region_id VARCHAR(96) NULL COMMENT '行政区划CODE', tenant_id VARCHAR(255) NULL COMMENT '租户ID', del_flag TINYINT(3) UNSIGNED DEFAULT 0 NULL COMMENT '删除标记 0 未删除 1 删除', creator VARCHAR(128) NULL COMMENT '创建人', create_time datetime NULL COMMENT '创建时间', modifier VARCHAR(128) NULL COMMENT '修改人', modify_time datetime NULL COMMENT '修改时间', CONSTRAINT PK_UC_USER_INFO PRIMARY KEY (id)) COMMENT='用户信息';
ALTER TABLE uc_user_info COMMENT = '用户信息';

--changeset Petty:1610268206696-6
CREATE TABLE uc_user_role (id VARCHAR(128) NOT NULL, user_id VARCHAR(128) NOT NULL, role_id VARCHAR(128) NOT NULL, del_flag TINYINT(3) UNSIGNED DEFAULT 0 NULL COMMENT '删除标记 0 未删除 1 删除', creator VARCHAR(128) NULL COMMENT '创建人', create_time datetime NULL COMMENT '创建时间', modifier VARCHAR(128) NULL COMMENT '修改人', modify_time datetime NULL COMMENT '修改时间', CONSTRAINT PK_UC_USER_ROLE PRIMARY KEY (id)) COMMENT='用户角色关联信息';
ALTER TABLE uc_user_role COMMENT = '用户角色关联信息';

--changeset Petty:1610268206696-7
ALTER TABLE uc_auth_client_details ADD CONSTRAINT client_id_del_flag_unique UNIQUE (client_id, del_flag);

--changeset Petty:1610268206696-8
ALTER TABLE uc_user ADD CONSTRAINT login_ld_del_flag_unique UNIQUE (login_id, del_flag);

--changeset Petty:1610268206696-9
ALTER TABLE uc_user ADD CONSTRAINT open_id_union_id_del_flag_unique UNIQUE (open_id, union_id, del_flag);

--changeset Petty:1610268206696-10
ALTER TABLE uc_role ADD CONSTRAINT role_del_flag_unique UNIQUE (`role`, del_flag);

--changeset Petty:1610268206696-11
ALTER TABLE uc_tenant ADD CONSTRAINT tenant_id_del_flag_unique UNIQUE (tenant_id, del_flag);

--changeset Petty:1610268206696-12
ALTER TABLE uc_user_info ADD CONSTRAINT user_id_del_flag_unique UNIQUE (user_id, user_iden, del_flag);

--changeset Petty:1610268206696-13
CREATE INDEX role_id_index ON uc_user_role(role_id);

--changeset Petty:1610268206696-14
CREATE INDEX user_id_index ON uc_user_role(user_id);


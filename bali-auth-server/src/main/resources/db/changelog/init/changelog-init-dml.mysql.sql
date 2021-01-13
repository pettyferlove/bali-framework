--liquibase formatted sql

--changeset Petty:1610535142257-1
INSERT INTO uc_auth_client_details (id, client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, auto_approve, del_flag, creator, create_time, modifier, modify_time, tenant_id) VALUES ('0000000000000000001', 'bali', NULL, '{noop}123456', 'user.read,resource.read', 'password,refresh_token,authorization_code,client_credentials,mobile,wechat', 'http://localhost:9001/login/oauth2/code/bali', NULL, 36000, 36000, NULL, NULL, 0, NULL, '2021-01-08 21:26:13', NULL, '2021-01-08 21:26:18', '0000000001');

--changeset Petty:1610535142257-2
INSERT INTO uc_role (id, `role`, role_name, `description`, sort, status, tenant_id, del_flag, creator, create_time, modifier, modify_time) VALUES ('0000000000000000001', 'SUPER_ADMIN', '超级管理员', '系统超级管理员，只允许进行租户操作等一系列系统设置', 99, 1, '0000000001', 0, '0000000000000000001', '2021-01-13 09:06:30', '0000000000000000001', '2021-01-13 10:56:14');
INSERT INTO uc_role (id, `role`, role_name, `description`, sort, status, tenant_id, del_flag, creator, create_time, modifier, modify_time) VALUES ('0000000000000000002', 'TENANT_ADMIN', '租户管理员', '租户的管理员，可用于控制租户下的数据，改角色不归属于任何租户，属于全局共享角色，只能由超级管理员进行统一维护', 98, 1, NULL, 0, '0000000000000000001', '2021-01-13 10:55:45', '0000000000000000001', '2021-01-13 10:57:54');

--changeset Petty:1610535142257-3
INSERT INTO uc_tenant (id, tenant_id, tenant_name, link_man, contact_number, address, remark, email, status, del_flag, creator, create_time, modifier, modify_time) VALUES ('0000000000000000001', '0000000001', '系统管理方', 'Alex Pettyfer', '13094186549', '广州', '', 'pettyferlove@live.cn', 1, 0, '0000000000000000001', '2019-07-13 11:58:05', '0000000000000000001', '2021-01-13 09:55:21');

--changeset Petty:1610535142257-4
INSERT INTO uc_user (id, login_id, password, tenant_id, open_id, union_id, user_channel, status, del_flag, creator, create_time, modifier, modify_time) VALUES ('0000000000000000001', 'administrator', '{bcrypt}$2a$10$GX3zqMtiaO1ywII/XxAj/eJI8j9xyxAGr8PgcxWbSkuz.v2CfSBte', '0000000001', NULL, NULL, 'web', 1, 0, '0000000000000000001', NULL, '0000000000000000001', '2021-01-13 15:32:28');

--changeset Petty:1610535142257-5
INSERT INTO uc_user_info (id, user_id, user_name, nick_name, user_sex, user_born, user_avatar, email, user_address, mobile_tel, phone_tel, user_iden_type, user_iden, `organization`, education, positional_title, political_status, graduated_school, graduated_year, major, verified, region_id, tenant_id, del_flag, creator, create_time, modifier, modify_time) VALUES ('0000000000000000001', '0000000000000000001', '超级管理员', '超级管理员', NULL, '2020-12-08 00:00:00', 'https://bali-attachment.oss-cn-shanghai.aliyuncs.com/bali/avatar/430ae478c2e74ee1b563caca5fbf0349.png', 'pettyferlove@live.cn', '', '13094186549', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '0000000001', 0, '0000000000000000001', NULL, '0000000000000000001', '2021-01-13 15:32:28');

--changeset Petty:1610535142257-6
INSERT INTO uc_user_role (id, user_id, role_id, del_flag, creator, create_time, modifier, modify_time) VALUES ('0000000000000000001', '0000000000000000001', '0000000000000000001', 0, '0000000000000000001', NULL, NULL, NULL);


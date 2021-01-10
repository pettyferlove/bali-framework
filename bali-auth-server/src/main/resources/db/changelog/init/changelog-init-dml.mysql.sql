--liquibase formatted sql

--changeset Petty:1610264051415-1
INSERT INTO uc_auth_client_details (id, client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, auto_approve, tenant_id, del_flag, creator, create_time, modifier, modify_time) VALUES ('1347517937222574085', 'bali', NULL, '123456', 'user.read,resource.read', 'password,refresh_token,authorization_code,client_credentials,mobile,wechat', 'http://localhost:9001/login/oauth2/code/bali', NULL, 36000, 36000, NULL, NULL, '0000000001', 0, NULL, '2021-01-08 21:26:13', NULL, '2021-01-08 21:26:18');

--changeset Petty:1610264051415-2
INSERT INTO uc_tenant (id, tenant_id, tenant_name, link_man, contact_number, address, remark, email, status, del_flag, creator, create_time, modifier, modify_time) VALUES ('1250754808025112580', '0000000001', '系统管理方', 'Alex Pettyfer', '13094186549', '广州', NULL, 'pettyferlove@live.cn', 1, 0, '1250752809141460993', '2019-07-13 11:58:05', '1250752809141460993', '2020-04-16 20:08:56');

--changeset Petty:1610264051415-3
INSERT INTO uc_user (id, login_id, password, tenant_id, open_id, union_id, user_channel, status, del_flag, creator, create_time, modifier, modify_time) VALUES ('1347774994585444353', 'admin', '{bcrypt}$2a$10$GX3zqMtiaO1ywII/XxAj/eJI8j9xyxAGr8PgcxWbSkuz.v2CfSBte', '0000000001', NULL, NULL, 'web', 1, 0, NULL, NULL, NULL, NULL);

--changeset Petty:1610264051415-4
INSERT INTO uc_user_info (id, user_id, user_name, nick_name, user_sex, user_born, user_avatar, email, user_address, mobile_tel, phone_tel, user_iden_type, user_iden, `organization`, education, positional_title, political_status, graduated_school, graduated_year, major, verified, region_id, tenant_id, del_flag, creator, create_time, modifier, modify_time) VALUES ('1348169137282019330', '1347774994585444353', '超级管理员', NULL, NULL, NULL, 'https://bali-attachment.oss-cn-shanghai.aliyuncs.com/bali/avatar/430ae478c2e74ee1b563caca5fbf0349.png', 'pettyferlove@live.cn', NULL, '13094186549', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '0000000001', 0, NULL, NULL, NULL, NULL);


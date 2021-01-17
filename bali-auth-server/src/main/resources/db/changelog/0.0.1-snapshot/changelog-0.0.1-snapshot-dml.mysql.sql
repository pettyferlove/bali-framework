--liquibase formatted sql

--changeset Petty:uc-0.0.1-snapshot-dml-1
UPDATE uc_auth_client_details t SET t.application_name = 'Bali客户端' WHERE t.id = '0000000000000000001';

--changeset Petty:uc-0.0.1-snapshot-dml-2
insert into uc_auth_client_details_scope (id, details_id, scope_id) values ('0000000000000000001', '0000000000000000001', '0000000000000000001');

insert into uc_auth_client_scope (id, scope, auto, description, del_flag, creator, create_time, modifier, modify_time, tenant_id) values ('0000000000000000001', 'user.read', 0, '读取用户基础信息', 0, '0000000000000000001', '2021-01-14 20:22:12', null, null, '0000000001');

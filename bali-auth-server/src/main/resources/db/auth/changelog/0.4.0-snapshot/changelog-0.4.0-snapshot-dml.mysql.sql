--liquibase formatted sql

--changeset Petty:uc-0.4.0-snapshot-dml-1
insert into uc_auth_client_scope (id, scope, auto, description, del_flag, creator, create_time, modifier, modify_time, tenant_id) values ('0000000000000000002', 'resource.read', 0, '读取资源信息', 0, '0000000000000000001', '2021-03-05 20:22:12', null, null, '0000000001');

insert into uc_auth_client_details_scope (id, details_id, scope_id) values ('0000000000000000002', '0000000000000000001', '0000000000000000002');


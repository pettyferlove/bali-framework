--liquibase formatted sql

--changeset Petty:uc-1.0.1-release-ddl-1
alter table uc_auth_client_details add client_secret_original varchar(256) null comment '客户端密钥原文' after client_secret;

--changeset Petty:uc-1.0.1-release-ddl-2
create index user_channel_index on uc_user (user_channel);
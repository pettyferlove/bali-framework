--liquibase formatted sql

--changeset Petty:0.0.1-snapshot-1
alter table uc_user_info modify user_avatar varchar(1000) null comment '用户头像';


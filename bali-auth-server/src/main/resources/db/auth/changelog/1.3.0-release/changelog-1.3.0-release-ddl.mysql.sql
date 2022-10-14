--liquibase formatted sql

--changeset Petty:uc-1.3.0-release-ddl-1
alter table uc_user add login_fail_num tinyint default 0 null comment '登录失败次数' after user_channel;

alter table uc_user add last_login_fail_time datetime null comment '最后一次登录失败时间' after login_fail_num;
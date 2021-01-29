--liquibase formatted sql

--changeset Petty:uc-0.0.1-snapshot-ddl-1
create table uc_auth_client_scope
(
    id          varchar(128)      not null
        primary key,
    scope       varchar(255)      not null comment '域名，用于客户端进行权限判断',
    auto        tinyint(1)        null comment '是否自动授权',
    description varchar(1000)     null comment '描述信息',
    del_flag    tinyint default 0 null comment '删除标记 0 未删除 1 删除',
    creator     varchar(128)      null comment '创建人',
    create_time datetime          null comment '创建时间',
    modifier    varchar(128)      null comment '修改人',
    modify_time datetime          null comment '修改时间',
    tenant_id   varchar(255)      null comment '租户ID',
    constraint uc_auth_client_scope_scope_tenant_id_del_flag_index
        unique (scope, tenant_id, del_flag)
)
    comment '客户端域';

--changeset Petty:uc-0.0.1-snapshot-ddl-2
drop index client_id_del_flag_unique on uc_auth_client_details;

create unique index client_id_del_flag_unique
    on uc_auth_client_details (client_id, del_flag, tenant_id);

--changeset Petty:uc-0.0.1-snapshot-ddl-3
drop index role_del_flag_unique on uc_role;

create unique index role_del_flag_unique
    on uc_role (role, del_flag, tenant_id);

--changeset Petty:uc-0.0.1-snapshot-ddl-4
drop index login_ld_del_flag_unique on uc_user;

create unique index login_ld_del_flag_unique
    on uc_user (login_id, del_flag, tenant_id);

drop index open_id_union_id_del_flag_unique on uc_user;

create unique index open_id_union_id_del_flag_unique
    on uc_user (open_id, union_id, del_flag, tenant_id);

--changeset Petty:uc-0.0.1-snapshot-ddl-5
alter table uc_user_info drop key user_id_del_flag_unique;

alter table uc_user_info
    add constraint user_id_del_flag_unique
        unique (user_id, user_iden, del_flag, tenant_id);

--changeset Petty:uc-0.0.1-snapshot-ddl-6
create table uc_auth_client_details_scope
(
    id          varchar(128)                 not null
        primary key,
    details_id  varchar(128)                 not null,
    scope_id    varchar(128)                 not null,
    del_flag    tinyint unsigned default '0' null comment '删除标记 0 未删除 1 删除',
    creator     varchar(128)                 null comment '创建人',
    create_time datetime                     null comment '创建时间',
    modifier    varchar(128)                 null comment '修改人',
    modify_time datetime                     null comment '修改时间'
);

create index uc_auth_client_details_scope_details_id_del_flag_index
    on uc_auth_client_details_scope (details_id, del_flag);

create index uc_auth_client_details_scope_scope_id_del_flag_index
    on uc_auth_client_details_scope (scope_id, del_flag);

--changeset Petty:uc-0.0.1-snapshot-ddl-7
alter table uc_auth_client_details
    add application_name varchar(255) null comment '应用名称' after id;

--changeset Petty:uc-0.0.1-snapshot-ddl-8
alter table uc_auth_client_details
    add description varchar(1000) null comment '描述信息' after application_name;

--changeset Petty:uc-0.0.1-snapshot-ddl-9

alter table uc_auth_client_details drop column scope;

alter table uc_auth_client_details drop column auto_approve;

--changeset Petty:uc-0.0.1-snapshot-ddl-10

alter table uc_auth_client_details_scope drop column creator;

alter table uc_auth_client_details_scope drop column create_time;

alter table uc_auth_client_details_scope drop column modifier;

alter table uc_auth_client_details_scope drop column modify_time;

alter table uc_auth_client_details_scope drop column del_flag;

drop index uc_auth_client_details_scope_details_id_del_flag_index on uc_auth_client_details_scope;

drop index uc_auth_client_details_scope_scope_id_del_flag_index on uc_auth_client_details_scope;

create index uc_auth_client_details_scope_details_id_index
    on uc_auth_client_details_scope (details_id);

create index uc_auth_client_details_scope_scope_id_index
    on uc_auth_client_details_scope (scope_id);


--changeset Petty:uc-0.0.1-snapshot-ddl-11

alter table uc_user_role drop column del_flag;

alter table uc_user_role drop column creator;

alter table uc_user_role drop column create_time;

alter table uc_user_role drop column modifier;

alter table uc_user_role drop column modify_time;

--changeset Petty:uc-0.0.1-snapshot-ddl-12
create view v_role_view as
select `uc_role`.`role`        AS `role`,
       `uc_role`.`role_name`   AS `role_name`,
       `uc_role`.`description` AS `description`,
       `uc_role`.`sort`        AS `sort`,
       `uc_role`.`status`      AS `status`,
       `uc_role`.`tenant_id`   AS `tenant_id`,
       `uc_role`.`create_time` AS `create_time`,
       `uc_role`.`modify_time` AS `modify_time`
from `uc_role`;

--changeset Petty:uc-0.0.1-snapshot-ddl-13
drop view v_role_view;
create view v_role_view as
select `uc_role`.`role`        AS `role`,
       `uc_role`.`role_name`   AS `role_name`,
       `uc_role`.`description` AS `description`,
       `uc_role`.`sort`        AS `sort`,
       `uc_role`.`status`      AS `status`,
       `uc_role`.`tenant_id`   AS `tenant_id`,
       `uc_role`.`create_time` AS `create_time`,
       `uc_role`.`modify_time` AS `modify_time`
from `uc_role` where `del_flag` = 0;


--changeset Petty:uc-0.0.1-snapshot-ddl-14
alter table uc_user_info
    add client_id varchar(128) null comment '所属客户端' after tenant_id;

--changeset Petty:uc-0.0.1-snapshot-ddl-15
alter table uc_user
    add client_id varchar(128) null comment '所属客户端' after tenant_id;



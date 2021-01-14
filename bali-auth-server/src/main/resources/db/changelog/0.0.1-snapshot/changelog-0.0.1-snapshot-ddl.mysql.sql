--liquibase formatted sql

--changeset Petty:0.0.1-snapshot-1
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

--changeset Petty:0.0.1-snapshot-2
drop index client_id_del_flag_unique on uc_auth_client_details;

create unique index client_id_del_flag_unique
    on uc_auth_client_details (client_id, del_flag, tenant_id);

--changeset Petty:0.0.1-snapshot-3
drop index role_del_flag_unique on uc_role;

create unique index role_del_flag_unique
    on uc_role (role, del_flag, tenant_id);

--changeset Petty:0.0.1-snapshot-4
drop index login_ld_del_flag_unique on uc_user;

create unique index login_ld_del_flag_unique
    on uc_user (login_id, del_flag, tenant_id);

drop index open_id_union_id_del_flag_unique on uc_user;

create unique index open_id_union_id_del_flag_unique
    on uc_user (open_id, union_id, del_flag, tenant_id);

--changeset Petty:0.0.1-snapshot-5
alter table uc_user_info drop key user_id_del_flag_unique;

alter table uc_user_info
    add constraint user_id_del_flag_unique
        unique (user_id, user_iden, del_flag, tenant_id);

--changeset Petty:0.0.1-snapshot-6
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

--changeset Petty:0.0.1-snapshot-7
alter table uc_auth_client_details
    add application_name varchar(255) null comment '应用名称' after id;

--changeset Petty:0.0.1-snapshot-8
alter table uc_auth_client_details modify application_name varchar(255) not null comment '应用名称';

alter table uc_auth_client_details
    add description varchar(1000) null comment '描述信息' after application_name;

--changeset Petty:0.0.1-snapshot-9
alter table uc_auth_client_details modify application_name varchar(255) null comment '应用名称';

alter table uc_auth_client_details drop column scope;

alter table uc_auth_client_details drop column auto_approve;


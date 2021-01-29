--liquibase formatted sql

--changeset Petty:extension-attachment-init-ddl-1
create table extension_attachment_info
(
    id           varchar(128)                 not null
        primary key,
    file_id      varchar(255)                 null,
    file_name    varchar(255)                 not null comment '文件名',
    file_type    varchar(255)                 not null comment '文件类型',
    size         bigint                       not null comment '文件大小',
    storage_type int                          not null comment '储存类型',
    md5          varchar(255)                 not null comment 'MD5值',
    path         varchar(255)                 not null comment '地址',
    del_flag     tinyint unsigned default '0' null comment '删除标记 0 未删除 1 删除',
    creator      varchar(128)                 null comment '创建人',
    create_time  datetime                     null comment '创建时间',
    modifier     varchar(128)                 null comment '修改人',
    modify_time  datetime                     null comment '修改时间',
    tenant_id    varchar(255)                 null
);
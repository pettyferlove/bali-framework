--liquibase formatted sql

--changeset Petty:0.0.1-snapshot-1
alter table uc_user_info modify user_avatar varchar(1000) null comment '用户头像';

--changeset Petty:0.0.1-snapshot-2
create view v_user_info_view as
select user.id,
       user.tenant_id,
       user.user_channel,
       user.status,
       user_info.user_name,
       user_info.nick_name,
       user_info.user_sex,
       user_info.user_born,
       user_info.user_avatar,
       user_info.email,
       user_info.user_address,
       user_info.mobile_tel,
       user_info.phone_tel,
       user_info.user_iden_type,
       user_info.user_iden,
       user_info.organization,
       user_info.education,
       user_info.positional_title,
       user_info.political_status,
       user_info.graduated_school,
       user_info.graduated_year,
       user_info.major,
       user_info.verified,
       user_info.region_id,
       user_info.creator,
       (select IFNULL(u.user_name, u.nick_name)
        from uc_user_info u
        where (user_info.creator = u.user_id)) AS creator_name,
       user_info.create_time,
       user_info.modifier,
       (select IFNULL(u.user_name, u.nick_name)
        from uc_user_info u
        where (user_info.modifier = u.user_id)) AS modifier_name,
       user_info.modify_time
from uc_user user
         left join uc_user_info user_info on user.id = user_info.user_id where user.del_flag = 0;

--liquibase formatted sql

--changeset Petty:uc-0.1.0-snapshot-ddl-1
drop view v_user_info_view;
create view v_user_info_view as
select `user`.`id`                                      AS `id`,
       `user`.`tenant_id`                               AS `tenant_id`,
       `user`.`client_id`                               AS `client_id`,
       `user`.`user_channel`                            AS `user_channel`,
       `user`.`status`                                  AS `status`,
       `user_info`.`user_name`                          AS `user_name`,
       `user_info`.`nick_name`                          AS `nick_name`,
       `user_info`.`user_sex`                           AS `user_sex`,
       `user_info`.`user_born`                          AS `user_born`,
       `user_info`.`user_avatar`                        AS `user_avatar`,
       `user_info`.`email`                              AS `email`,
       `user_info`.`user_address`                       AS `user_address`,
       `user_info`.`mobile_tel`                         AS `mobile_tel`,
       `user_info`.`phone_tel`                          AS `phone_tel`,
       `user_info`.`user_iden_type`                     AS `user_iden_type`,
       `user_info`.`user_iden`                          AS `user_iden`,
       `user_info`.`organization`                       AS `organization`,
       `user_info`.`education`                          AS `education`,
       `user_info`.`positional_title`                   AS `positional_title`,
       `user_info`.`political_status`                   AS `political_status`,
       `user_info`.`graduated_school`                   AS `graduated_school`,
       `user_info`.`graduated_year`                     AS `graduated_year`,
       `user_info`.`major`                              AS `major`,
       `user_info`.`verified`                           AS `verified`,
       `user_info`.`region_id`                          AS `region_id`,
       `user_info`.`creator`                            AS `creator`,
       (select ifnull(`u`.`user_name`, `u`.`nick_name`)
        from `uc_user_info` `u`
        where (`user_info`.`creator` = `u`.`user_id`))  AS `creator_name`,
       `user_info`.`create_time`                        AS `create_time`,
       `user_info`.`modifier`                           AS `modifier`,
       (select ifnull(`u`.`user_name`, `u`.`nick_name`)
        from `uc_user_info` `u`
        where (`user_info`.`modifier` = `u`.`user_id`)) AS `modifier_name`,
       `user_info`.`modify_time`                        AS `modify_time`
from (`uc_user` `user`
         left join `uc_user_info` `user_info` on ((`user`.`id` = `user_info`.`user_id`)))
where (`user`.`del_flag` = 0);



--changeset Petty:uc-0.1.0-snapshot-ddl-2
drop view v_user_info_view;
create view v_user_info_view as
select `user`.`id`                                      AS `id`,
       `user`.`tenant_id`                               AS `tenant_id`,
       `user`.`client_id`                               AS `client_id`,
       (select `u`.`application_name`
        from `uc_auth_client_details` `u`
        where (`user`.`client_id` = `u`.`client_id`))  AS `application_name`,
       `user`.`user_channel`                            AS `user_channel`,
       `user`.`status`                                  AS `status`,
       `user_info`.`user_name`                          AS `user_name`,
       `user_info`.`nick_name`                          AS `nick_name`,
       `user_info`.`user_sex`                           AS `user_sex`,
       `user_info`.`user_born`                          AS `user_born`,
       `user_info`.`user_avatar`                        AS `user_avatar`,
       `user_info`.`email`                              AS `email`,
       `user_info`.`user_address`                       AS `user_address`,
       `user_info`.`mobile_tel`                         AS `mobile_tel`,
       `user_info`.`phone_tel`                          AS `phone_tel`,
       `user_info`.`user_iden_type`                     AS `user_iden_type`,
       `user_info`.`user_iden`                          AS `user_iden`,
       `user_info`.`organization`                       AS `organization`,
       `user_info`.`education`                          AS `education`,
       `user_info`.`positional_title`                   AS `positional_title`,
       `user_info`.`political_status`                   AS `political_status`,
       `user_info`.`graduated_school`                   AS `graduated_school`,
       `user_info`.`graduated_year`                     AS `graduated_year`,
       `user_info`.`major`                              AS `major`,
       `user_info`.`verified`                           AS `verified`,
       `user_info`.`region_id`                          AS `region_id`,
       `user_info`.`creator`                            AS `creator`,
       (select ifnull(`u`.`user_name`, `u`.`nick_name`)
        from `uc_user_info` `u`
        where (`user_info`.`creator` = `u`.`user_id`))  AS `creator_name`,
       `user_info`.`create_time`                        AS `create_time`,
       `user_info`.`modifier`                           AS `modifier`,
       (select ifnull(`u`.`user_name`, `u`.`nick_name`)
        from `uc_user_info` `u`
        where (`user_info`.`modifier` = `u`.`user_id`)) AS `modifier_name`,
       `user_info`.`modify_time`                        AS `modify_time`
from (`uc_user` `user`
         left join `uc_user_info` `user_info` on ((`user`.`id` = `user_info`.`user_id`)))
where (`user`.`del_flag` = 0);
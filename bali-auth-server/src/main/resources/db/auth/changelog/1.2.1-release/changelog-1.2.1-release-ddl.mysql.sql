--liquibase formatted sql

--changeset Petty:uc-1.2.1-release-ddl-1
drop view v_role_view;
create view v_role_view as
select `uc_role`.`id`        AS `id`,
       `uc_role`.`role`        AS `role`,
       `uc_role`.`role_name`   AS `role_name`,
       `uc_role`.`description` AS `description`,
       `uc_role`.`sort`        AS `sort`,
       `uc_role`.`status`      AS `status`,
       `uc_role`.`tenant_id`   AS `tenant_id`,
       `uc_role`.`create_time` AS `create_time`,
       `uc_role`.`modify_time` AS `modify_time`
from `uc_role` where `del_flag` = 0;
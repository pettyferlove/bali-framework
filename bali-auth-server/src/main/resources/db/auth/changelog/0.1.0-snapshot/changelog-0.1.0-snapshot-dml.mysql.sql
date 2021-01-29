--liquibase formatted sql

--changeset Petty:uc-0.1.0-snapshot-dml-1
update uc_user t set t.user_channel = 'maintainer' where t.id = '0000000000000000001'
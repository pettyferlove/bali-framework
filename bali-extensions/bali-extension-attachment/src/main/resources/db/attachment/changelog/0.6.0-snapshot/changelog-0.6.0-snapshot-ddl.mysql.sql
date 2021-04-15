--liquibase formatted sql

--changeset Petty:extension-attachment-0.6.0-snapshot-ddl-1
alter table extension_attachment_info drop column file_id;

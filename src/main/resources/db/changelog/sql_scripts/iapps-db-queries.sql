-- liquibase formatted sql
-- changeset yunus:1 runOnChange:true endDelimiter:#
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) FROM information_schema.tables where table_schema = DATABASE() AND table_name = "epaper";

create table epaper (id bigint not null auto_increment, created_date datetime(6), dpi bigint not null, file_name varchar(500), height bigint not null, news_paper_name varchar(100), width bigint not null, primary key (id));#
alter table epaper add constraint UK_nt6j1tcfdhjfaha6ai7huywc0h unique (file_name);#
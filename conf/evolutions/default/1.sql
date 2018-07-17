# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table hunt (
  huntid                    varchar(255) not null,
  huntname                  varchar(255),
  constraint pk_hunt primary key (huntid))
;

create table hunter (
  hunter_id                 varchar(255) not null,
  hunter_name               varchar(255),
  subscibed_hunts           varchar(255),
  constraint pk_hunter primary key (hunter_id))
;

create table progress (
  progress_id               varchar(255) not null,
  hunter_id                 varchar(255),
  hunt_id                   varchar(255),
  all_scores                varchar(255),
  constraint pk_progress primary key (progress_id))
;

create table question (
  question_id               varchar(255) not null,
  question_text             varchar(255),
  options                   varchar(255),
  answer_index              integer,
  hunt_id                   varchar(255),
  constraint pk_question primary key (question_id))
;

create sequence hunt_seq;

create sequence hunter_seq;

create sequence progress_seq;

create sequence question_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists hunt;

drop table if exists hunter;

drop table if exists progress;

drop table if exists question;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists hunt_seq;

drop sequence if exists hunter_seq;

drop sequence if exists progress_seq;

drop sequence if exists question_seq;


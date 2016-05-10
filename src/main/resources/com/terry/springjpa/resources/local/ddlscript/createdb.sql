create memory table boardtype(
	idx bigint primary key
	, name varchar(255) not null
	, url varchar(255) not null
	, insertdt timestamp not null
	, updatedt timestamp
);
create memory table member(
	idx bigint primary key
	, loginid varchar(255) not null
	, password varchar(255) not null
	, name varchar(255) not null
	, email varchar(255) not null
	, insertdt timestamp not null
	, updatedt timestamp
);
create memory table unitedboard(
	idx bigint primary key
	, boardtype_idx bigint not null
	, member_idx bigint not null
	, title varchar(255) not null
	, contents clob not null
	, viewcnt int not null default 0
	, insertdt timestamp not null
	, updatedt timestamp
);
create memory table unitedboard_comment(
	idx bigint primary key
	, unitedboard_idx bigint not null
	, member_idx bigint not null
	, comment clob not null
	, insertdt timestamp not null
);

create memory table authority (
  idx 				bigint 			primary key,
  authority_name    varchar(50)		not null, 
  authority_desc    varchar(50)         null
);

create memory table member_authority (
  member_idx		bigint		    not null, 
  authority_idx     bigint		    not null,
  primary key(member_idx, authority_idx)
);

create memory table groups (
  idx           bigint				primary key, 
  group_name    varchar(50)         not null
);

create memory table member_groups (
  member_idx	bigint         		not null,
  groups_idx	bigint         		not null, 
  primary key(member_idx, groups_idx)
);

create memory table groups_authority (
  groups_idx   		bigint         	not null, 
  authority_idx  	bigint         	not null,
  primary key(groups_idx, authority_idx)
);

create memory table secured_resources (
  idx        		bigint      	primary key, 
  resource_name     varchar(50)     null, 
  resource_pattern  varchar(100)    not null, 
  resource_type     varchar(10)     null, 
  sort_order        int				null
);

create memory table secured_resources_authority (
  resources_idx		bigint			not null, 
  authority_idx     bigint			not null,
  primary key(resources_idx, authority_idx)
);

alter table unitedboard add foreign key(boardtype_idx) references boardtype(idx);
alter table unitedboard add foreign key(member_idx) references member(idx);
alter table unitedboard_comment add foreign key(unitedboard_idx) references unitedboard(idx);
alter table unitedboard_comment add foreign key(member_idx) references member(idx);
alter table member_authority add foreign key(member_idx) references member(idx);
alter table member_authority add foreign key(authority_idx) references authority(idx);
alter table member_groups add foreign key(member_idx) references member(idx);
alter table member_groups add foreign key(groups_idx) references groups(idx);
alter table groups_authority add foreign key(groups_idx) references groups(idx);
alter table groups_authority add foreign key(authority_idx) references authority(idx);
alter table secured_resources_authority add foreign key(resources_idx) references secured_resources(idx);
alter table secured_resources_authority add foreign key(authority_idx) references authority(idx);

create sequence boardtype_sequence start with 1 increment by 1;
create sequence member_sequence start with 1 increment by 1;
create sequence unitedboard_sequence start with 1 increment by 1;
create sequence unitedboard_comment_sequence start with 1 increment by 1;
create sequence authority_sequence start with 1 increment by 1;
create sequence groups_sequence start with 1 increment by 1;
create sequence secured_resources_sequence start with 1 increment by 1;

-- test data insert query boardtype_sequence.nextval, ?, ?, current_timestamp(), null
insert into boardtype(idx, name, url, insertdt) values(boardtype_sequence.nextval, '테스트 게시판1', '/unitedBoard/unitedBoardList.do?boardType=1', current_timestamp());
insert into boardtype(idx, name, url, insertdt) values(boardtype_sequence.nextval, '테스트 게시판2', '/unitedBoard/unitedBoardList.do?boardType=2', current_timestamp());

insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId1', 'password1', '가나다1', 'a1@b.com', current_timestamp());
insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId2', 'password2', '가나다2', 'a2@b.com', current_timestamp());

insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt) values(unitedboard_sequence.nextval, 1, 1, '테스트 게시판1 제목1', '테스트 게시판1 내용1', 0, current_timestamp());
insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt) values(unitedboard_sequence.nextval, 1, 2, '테스트 게시판1 제목2', '테스트 게시판1 내용2', 0, current_timestamp());
insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt) values(unitedboard_sequence.nextval, 2, 1, '테스트 게시판2 제목1', '테스트 게시판2 내용1', 0, current_timestamp());
insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt) values(unitedboard_sequence.nextval, 2, 2, '테스트 게시판2 제목2', '테스트 게시판2 내용2', 0, current_timestamp());



-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid2', '2password', '가나다2', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid3', '3password', '가나라', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid4', '4password', '가나마', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid5', '5password', '사나다', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid6', '6password', '아나다', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid7', '7password', '가자다', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid8', '8password', '가타다', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginid9', '9password', '가파다', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, '1loginid', 'password1', '가나하', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, '2loginid', 'password2', '1가나다', 'a@b.com', current_timestamp());
-- insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, '3loginid', 'password3', '2가나다', 'a@b.com', current_timestamp());

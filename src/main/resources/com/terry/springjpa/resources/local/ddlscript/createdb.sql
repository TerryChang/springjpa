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
  idx        			bigint      	primary key, 
  resource_name     	varchar(50)     not null, 
  resource_pattern  	varchar(100)    not null,		-- ANT 패턴, REGEX 패턴, 메소드 이름
  resource_match_type	varchar(100)    null,			-- ANT, REGEX(정규식)
  sort_order        	int				not null		-- 정규표현식이 ANT 식보다 더 우선순위에 오게끔 한다(일치되는 타입은 그냥 정규표현식으로 할것)
);

create memory table secured_resources_authority (
  resources_idx		bigint			not null, 
  authority_idx     bigint			not null,
  primary key(resources_idx, authority_idx)
);

create memory table authority_hierarchy (
	parent_authority_idx	bigint		not null,
	child_authority_idx		bigint		not null,
	primary key(parent_authority_idx, child_authority_idx)
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
alter table authority_hierarchy add foreign key(parent_authority_idx) references authority(idx);
alter table authority_hierarchy add foreign key(child_authority_idx) references authority(idx);

create sequence boardtype_sequence start with 1 increment by 1;
create sequence member_sequence start with 1 increment by 1;
create sequence unitedboard_sequence start with 1 increment by 1;
create sequence unitedboard_comment_sequence start with 1 increment by 1;
create sequence authority_sequence start with 1 increment by 1;
create sequence groups_sequence start with 1 increment by 1;
create sequence secured_resources_sequence start with 1 increment by 1;

-- test data insert query boardtype_sequence.nextval, ?, ?, current_timestamp(), null
insert into boardtype(idx, name, url, insertdt) values(boardtype_sequence.nextval, '관리자 게시판', '/unitedBoard/unitedBoardList.do?boardTypeIdx=1', current_timestamp());
insert into boardtype(idx, name, url, insertdt) values(boardtype_sequence.nextval, '회원 게시판', '/unitedBoard/unitedBoardList.do?boardTypeIdx=2', current_timestamp());
insert into boardtype(idx, name, url, insertdt) values(boardtype_sequence.nextval, '까페 게시판', '/unitedBoard/unitedBoardList.do?boardTypeIdx=3', current_timestamp());
insert into boardtype(idx, name, url, insertdt) values(boardtype_sequence.nextval, '질문 게시판', '/unitedBoard/unitedBoardList.do?boardTypeIdx=4', current_timestamp());

insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId1', '$2a$10$uKMo1/K3k5e1r.pkBjiJs.ZYJSR5Flxhgojjs7r578NvaGOenQqhm', '관리자', 'a1@b.com', current_timestamp());
insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId2', '$2a$10$TOggES.7yhqi95L/z8j4j.tSIxjPYuYaQaZQD8Lm6VzgVAdGwEPQi', '까페회원', 'a2@b.com', current_timestamp());
insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId3', '$2a$10$E92nTJndyp8jLU5/IDnzn.c7fySRkiGHewL0fP9Vjc6gMWcNGr/bm', '일반회원', 'a3@b.com', current_timestamp());

/*
insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId1', 'password1', '관리자', 'a1@b.com', current_timestamp());
insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId2', 'password2', '까페회원', 'a2@b.com', current_timestamp());
insert into member(idx, loginid, password, name, email, insertdt) values(member_sequence.nextval, 'loginId3', 'password3', '일반회원', 'a3@b.com', current_timestamp());
*/

insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'ADMIN', '관리자');
insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'CAFEMEMBER', '까페회원');
insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'MEMBER', '회원');
insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'ANONYMOUS', '비회원');

insert into authority_hierarchy(parent_authority_idx, child_authority_idx) values(1, 2);
insert into authority_hierarchy(parent_authority_idx, child_authority_idx) values(1, 3);
insert into authority_hierarchy(parent_authority_idx, child_authority_idx) values(2, 4);
insert into authority_hierarchy(parent_authority_idx, child_authority_idx) values(3, 4);

insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '공지사항 목록', '/unitedBoard/unitedBoardList.do(([\\?&].+)*([\\?&]boardTypeIdx=1)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '공지사항 등록/상세조회/수정', '/unitedBoard/unitedBoardInsertUpdate.do(([\\?&].+)*([\\?&]boardTypeIdx=1)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '공지사항 삭제', '/unitedBoard/unitedBoardDelete.do(([\\?&].+)*([\\?&]boardTypeIdx=1)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '회원 게시판 목록', '/unitedBoard/unitedBoardList.do(([\\?&].+)*([\\?&]boardTypeIdx=2)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '회원 게시판 등록/상세조회/수정', '/unitedBoard/unitedBoardInsertUpdate.do(([\\?&].+)*([\\?&]boardTypeIdx=2)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '회원 게시판 삭제', '/unitedBoard/unitedBoardDelete.do(([\\?&].+)*([\\?&]boardTypeIdx=2)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '까페 게시판 목록', '/unitedBoard/unitedBoardList.do(([\\?&].+)*([\\?&]boardTypeIdx=3)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '까페 게시판 등록/상세조회/수정', '/unitedBoard/unitedBoardInsertUpdate.do(([\\?&].+)*([\\?&]boardTypeIdx=3)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '까페 게시판 삭제', '/unitedBoard/unitedBoardDelete.do(([\\?&].+)*([\\?&]boardTypeIdx=3)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '질문 게시판 목록', '/unitedBoard/unitedBoardList.do(([\\?&].+)*([\\?&]boardTypeIdx=4)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '질문 게시판 등록/상세조회/수정', '/unitedBoard/unitedBoardInsertUpdate.do(([\\?&].+)*([\\?&]boardTypeIdx=4)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '질문 게시판 삭제', '/unitedBoard/unitedBoardDelete.do(([\\?&].+)*([\\?&]boardTypeIdx=4)(&.+)*)', 'REGEX', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '회원 게시판 목록', '/member/memberList.do', 'ANT', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '회원 게시판 등록/상세조회/수정', '/member/memberInsertUpdate.do', 'ANT', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '회원 게시판 삭제', '/member/memberDelete.do', 'ANT', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_match_type, sort_order) values(secured_resources_sequence.nextval, '로그인 아이디 체크', '/member/checkLoginId.do', 'ANT', 1);


insert into member_authority(member_idx, authority_idx) values(1, 1);
insert into member_authority(member_idx, authority_idx) values(2, 2);
insert into member_authority(member_idx, authority_idx) values(3, 3);

insert into secured_resources_authority(resources_idx, authority_idx) values(1, 1);		-- 관리자 게시판 목록, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(2, 1);		-- 관리자 게시판 등록/상세조회/수정, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(3, 1);		-- 관리자 게시판 삭제, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(4, 3);		-- 회원 게시판 목록, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(5, 3);		-- 회원 게시판 등록/상세조회/수정, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(6, 3);		-- 회원 게시판 삭제, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(7, 2);		-- 까페 게시판 목록, 까페회원
insert into secured_resources_authority(resources_idx, authority_idx) values(8, 2);		-- 까페 게시판 등록/상세조회/수정, 까페회원
insert into secured_resources_authority(resources_idx, authority_idx) values(9, 2);		-- 까페 게시판 삭제, 까페회원
insert into secured_resources_authority(resources_idx, authority_idx) values(10, 3);	-- 질문 게시판 목록, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(11, 3);	-- 질문 게시판 등록/상세조회/수정, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(12, 3);	-- 질문 게시판 삭제, 회원

insert into secured_resources_authority(resources_idx, authority_idx) values(13, 1);	-- 회원 게시판 목록, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(14, 1);	-- 회원 게시판 등록/상세조회/수정, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(15, 1);	-- 회원 게시판 삭제, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(16, 1);	-- 로그인 아이디 체크, 관리자
insert into secured_resources_authority(resources_idx, authority_idx) values(14, 2);	-- 회원 게시판 등록/상세조회/수정, 까페회원
insert into secured_resources_authority(resources_idx, authority_idx) values(15, 2);	-- 회원 게시판 삭제, 까페회원
insert into secured_resources_authority(resources_idx, authority_idx) values(14, 3);	-- 회원 게시판 등록/상세조회/수정, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(15, 3);	-- 회원 게시판 삭제, 회원
insert into secured_resources_authority(resources_idx, authority_idx) values(14, 4);	-- 회원 게시판 등록/상세조회/수정, 비회원
insert into secured_resources_authority(resources_idx, authority_idx) values(16, 4);	-- 로그인 아이디 체크, 비회원


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
alter table unitedboard add foreign key(boardtype_idx) references boardtype(idx);
alter table unitedboard add foreign key(member_idx) references member(idx);
alter table unitedboard_comment add foreign key(unitedboard_idx) references unitedboard(idx);
alter table unitedboard_comment add foreign key(member_idx) references member(idx);
create sequence boardtype_sequence start with 1 increment by 1;
create sequence member_sequence start with 1 increment by 1;
create sequence unitedboard_sequence start with 1 increment by 1;
create sequence unitedboard_comment_sequence start with 1 increment by 1;
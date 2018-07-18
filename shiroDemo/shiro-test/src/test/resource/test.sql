#用户表
create table users(
	id int not null auto_increment,
	username varchar(20) not null,
	password varchar(20) not null,
	primary key(id)
)
#用户角色表
create table user_roles(
	id int not null auto_increment,
	username varchar(20) not null,
	role_name varchar(20) not null,
	primary key(id)
)
#角色权限表
create table roles_permissions(
	id int not null auto_increment,
	role_name varchar(20) not null,
	permission varchar(20) not null,
	primary key(id)
)

#自定义用户表
create table own_user(
	id int not null auto_increment,
	username varchar(20) not null,
	password varchar(20) not null,
	primary key(id)
)

#自定义角色表
create table own_user_roles(
	id int not null auto_increment,
	own_user_id int not null,
	role_name varchar(20) not null,
	primary key(id)
)

#自定义权限表
create table own_roles_permissions(
	id int not null auto_increment,
	own_role_name varchar(20) not null,
	own_permission varchar(20) not null,
	primary key(id)
)

insert into users (username,password) values ('jiarui','123456');
insert into user_roles (username,role_name) values ('jiarui','admin');
insert into roles_permissions (role_name,permission) values ('admin','delete-user');
insert into own_user (username,password) values ('xiaoming','654321');
insert into own_user_roles (own_user_id,role_name) values (1,'admin');
insert into own_roles_permissions (own_role_name,own_permission) values ('admin','delete-user');
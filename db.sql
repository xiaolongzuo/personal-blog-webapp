ALTER SCHEMA `blog`  DEFAULT CHARACTER SET utf8mb4 ;

DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS article_id_visitor_ip;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS access_log;

CREATE TABLE articles
(
  id INT NOT NULL AUTO_INCREMENT,
  username CHAR(15) NOT NULL CHECK(username !=''),
  resource_id VARCHAR(200) NOT NULL,
  subject VARCHAR (100) NOT NULL,
  html LONGTEXT NOT NULL,
  content LONGTEXT NOT NULL,
  icon VARCHAR (200) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  modify_date TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00',
  access_times INT DEFAULT 0,
  comment_times INT DEFAULT 0,
  good_times INT DEFAULT 0,
  touch_times INT DEFAULT 0,
  funny_times INT DEFAULT 0,
  happy_times INT DEFAULT 0,
  anger_times INT DEFAULT 0,
  bored_times INT DEFAULT 0,
  water_times INT DEFAULT 0,
  surprise_times INT DEFAULT 0,
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

alter table articles add unique(resource_id);

CREATE TABLE comments
(
  id INT NOT NULL AUTO_INCREMENT,
  content TEXT NOT NULL,
  visitor_ip char(20) NOT NULL,
  city char(20) NOT NULL,
  article_id INT NOT NULL ,
  create_date TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  modify_date TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

ALTER TABLE comments ADD CONSTRAINT `COMMENTS_FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);

create table article_id_visitor_ip (  
  article_id INT NOT NULL,
	visitor_ip char(20) NOT NULL, 
	primary key (article_id,visitor_ip)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 ;

ALTER TABLE article_id_visitor_ip ADD CONSTRAINT `ARTICLE_ID_VISITOR_IP_FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);

create table access_log (
  id INT NOT NULL AUTO_INCREMENT,
	visitor_ip char(20) NOT NULL,
	url VARCHAR (200) NOT NULL,
	access_date TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
	city CHAR (20) NOT NULL,
	params LONGTEXT ,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS hero;

create table matches (
	id INT NOT NULL AUTO_INCREMENT,
	attack VARCHAR(50) NOT NULL,
	defend VARCHAR(50) NOT NULL,
	result TINYINT NOT NULL,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

create table hero (
	full_name VARCHAR(20) NOT NULL,
	aliases VARCHAR(200),
	primary key (full_name)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

alter table matches add count int(11) default 1;
alter table matches add record_date timestamp default '0000-00-00 00:00:00';

alter table articles add status tinyint default 1;

create table html_page (
	id INT NOT NULL AUTO_INCREMENT,
	url VARCHAR(500),
	is_push tinyint,
	push_date timestamp default '0000-00-00 00:00:00',
	create_date timestamp default '0000-00-00 00:00:00',
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

create table users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR (40) NOT NULL,
  password VARCHAR (40) ,
  nick_name VARCHAR (40) ,
  qq_open_id VARCHAR (40) ,
  qq_nick_name VARCHAR (40),
  qq_avatar_url_30 VARCHAR(200),
  primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

alter table users add unique(username);

alter table comments add username VARCHAR (40);
alter table comments add nick_name VARCHAR (40);

create table tags (
	id INT NOT NULL AUTO_INCREMENT,
	tag_name VARCHAR(20),
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

create table article_tag (
	article_id INT NOT NULL,
	tag_id INT NOT NULL,
	primary key (article_id,tag_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

ALTER TABLE article_tag ADD CONSTRAINT `ARTICLE_TAG_FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);
ALTER TABLE article_tag ADD CONSTRAINT `ARTICLE_TAG_FK_TAG_ID` FOREIGN KEY (`tag_id`) REFERENCES tags(`id`);


create table categories (
	id INT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(20),
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

create table article_category (
	article_id INT NOT NULL,
	category_id INT NOT NULL,
	primary key (article_id,category_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

ALTER TABLE article_category ADD CONSTRAINT `ARTICLE_CATEGORY_FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);
ALTER TABLE article_category ADD CONSTRAINT `ARTICLE_CATEGORY_FK_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES categories(`id`);

ALTER TABLE comments add reference_comment_id INT ;
ALTER TABLE comments add resource_id VARCHAR(40) ;
ALTER TABLE comments add good_times INT DEFAULT 0;
ALTER TABLE comments add bad_times INT DEFAULT 0;

ALTER TABLE comments ADD CONSTRAINT `COMMENTS_FK_REFERENCE_COMMENT_ID` FOREIGN KEY (`reference_comment_id`) REFERENCES comments(`id`);

create table comment_id_visitor_ip (
  	comment_id INT NOT NULL,
	visitor_ip char(20) NOT NULL,
	primary key (comment_id,visitor_ip)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 ;

ALTER TABLE comment_id_visitor_ip ADD CONSTRAINT `COMMENT_ID_VISITOR_IP_FK_ARTICLE_ID` FOREIGN KEY (`comment_id`) REFERENCES comments(`id`);

ALTER TABLE comment_id_visitor_ip add username VARCHAR(40) ;
ALTER TABLE article_id_visitor_ip add username VARCHAR(40) ;

create table images (
	id INT NOT NULL AUTO_INCREMENT,
	path VARCHAR(200) NOT NULL,
	resource_url VARCHAR(200),
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

ALTER TABLE users add create_date TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00';

create table dictionaries (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(200) NOT NULL,
	type VARCHAR(10),
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

CREATE TABLE dictionary_province (  
  id int(10) unsigned NOT NULL default 0,  
  name varchar(30) NOT NULL,  
  type varchar(1) default NULL COMMENT '1 - 直辖市\r\n2 - 行政省\r\n3 - 自治区\r\n4 - 特别行政区\r\n5 - 其他国家\r\n见全局数据字典[省份类型] \r\n',  
  state varchar(1) default NULL COMMENT '0 - 禁用\r\n1 - 启用',  
  PRIMARY KEY  (id)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;  
   
CREATE TABLE dictionary_city (  
  id int(10) unsigned NOT NULL default '0',  
  name varchar(30) NOT NULL,  
  province_id int(10) unsigned NOT NULL,  
  state varchar(1) default '1' COMMENT '0 - 禁用\r\n1 - 启用 \r\n',  
  PRIMARY KEY  (id)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 

alter table users add province VARCHAR (200);
alter table users add city VARCHAR (200);
alter table users add language_id int(11);

alter table users add CONSTRAINT `USERS_FK_LANGUAGE_ID` FOREIGN KEY (`language_id`) REFERENCES dictionaries(`id`);

alter table users drop qq_nick_name ;
alter table users change qq_avatar_url_30 image_path VARCHAR(200);

--到此

create table questions (
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(200) NOT NULL,
	description VARCHAR(1500),
	username VARCHAR (40),
  	nick_name VARCHAR (40),
  	create_date DATE,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

create table answers (
	id INT NOT NULL AUTO_INCREMENT,
	question_id INT NOT NULL,
	answer VARCHAR(1500),
	visitor_ip char(20) NOT NULL,
  	city char(20) NOT NULL,
  	username VARCHAR (40),
  	nick_name VARCHAR (40),
  	answer_date DATE,
  	reference_comment_id INT,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;

alter table answers add CONSTRAINT `ANSWERS_FK_QUESTION_ID` FOREIGN KEY (`question_id`) REFERENCES questions(`id`);

ALTER TABLE articles CHANGE COLUMN resource_id resource_id VARCHAR(200) NULL ;


create table chats (
	id INT NOT NULL AUTO_INCREMENT,
	content VARCHAR(1500),
	visitor_ip char(20) NOT NULL,
  	city char(20) NOT NULL,
  	username VARCHAR (40),
  	nick_name VARCHAR (40),
  	reference_comment_id INT,
  	chat_date DATE,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT = 1;
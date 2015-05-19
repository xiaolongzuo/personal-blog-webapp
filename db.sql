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
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

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
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

ALTER TABLE comments ADD CONSTRAINT `COMMENTS_FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);

create table article_id_visitor_ip (  
  article_id INT NOT NULL,
	visitor_ip char(20) NOT NULL, 
	primary key (article_id,visitor_ip)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;

ALTER TABLE article_id_visitor_ip ADD CONSTRAINT `ARTICLE_ID_VISITOR_IP_FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);

create table access_log (
  id INT NOT NULL AUTO_INCREMENT,
	visitor_ip char(20) NOT NULL,
	url VARCHAR (200) NOT NULL,
	access_date TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
	city CHAR (20) NOT NULL,
	params LONGTEXT ,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

--以下为刀塔传奇sql
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS hero;

create table matches (
	id INT NOT NULL AUTO_INCREMENT,
	attack VARCHAR(50) NOT NULL,
	defend VARCHAR(50) NOT NULL,
	result TINYINT NOT NULL,
	primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

create table hero (
	full_name VARCHAR(20) NOT NULL,
	aliases VARCHAR(200),
	primary key (full_name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

alter table matches add count int(11) default 1;
alter table matches add record_date timestamp default '0000-00-00 00:00:00';
DROP TABLE comments;
DROP TABLE articles;
DROP TABLE users;
DROP TABLE visitors;

CREATE TABLE users
(
  username CHAR(15) NOT NULL CHECK(username !=''),
  password CHAR(15) NOT NULL,
  email VARCHAR(20) NOT NULL UNIQUE,
  nickname VARCHAR(20) NOT NULL UNIQUE,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  PRIMARY KEY(username)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE articles
(
  id INT NOT NULL AUTO_INCREMENT,
  username CHAR(15) NOT NULL CHECK(username !=''),
  subject VARCHAR (40) NOT NULL,
  content TEXT NOT NULL,
  icon VARCHAR (200) NOT NULL,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  access_times INT ,
  good_times INT ,
  touch_times INT ,
  funny_times INT ,
  comment_times INT ,
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

ALTER TABLE articles ADD CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`) REFERENCES users(`username`);

CREATE TABLE visitors
(
  username CHAR(15) NOT NULL CHECK(username !=''),
  password CHAR(15) NOT NULL,
  nickname VARCHAR(20) NOT NULL UNIQUE,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  PRIMARY KEY(username)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;

CREATE TABLE comments
(
  id INT NOT NULL AUTO_INCREMENT,
  content BLOB NOT NULL,
  article_id INT NOT NULL ,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

ALTER TABLE comments ADD CONSTRAINT `FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);

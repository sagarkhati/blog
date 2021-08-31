DROP TABLE IF EXISTS posts;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  user_name varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  password varchar(45) DEFAULT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE posts (
  post_id int(11) NOT NULL AUTO_INCREMENT,
  post_title varchar(455) DEFAULT NULL,
  post_body longtext,
  published_by int(11) DEFAULT NULL,
  created_on datetime DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  is_deleted tinyint(1) DEFAULT NULL,
  PRIMARY KEY (post_id),
  CONSTRAINT userdId FOREIGN KEY (published_by) REFERENCES users (user_id) ON DELETE CASCADE
); 
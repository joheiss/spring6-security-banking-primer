create table if not exists users (
  username varchar(50) not null primary key,
  password varchar(500) not null,
  enabled boolean not null
);

create table if not exists authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index if not exists ix_auth_username on authorities (username, authority);

INSERT IGNORE INTO `users`
VALUES
  (
    'user',
    '{bcrypt}$2a$12$9CVTmglu7SXoLhXTRv1Dv.26fo0ZVbtF2tkviEZtzk1pFo//Qc.3u',
    '1'
  );

INSERT IGNORE INTO `authorities`
VALUES
  ('user', 'read');

INSERT IGNORE INTO `users`
VALUES
  (
    'admin',
    '{bcrypt$2a$12$kfg0mphXmIGS3.fmbWqA5OSGUNokElSVkSJzhZWX/HNTxxpjclllS',
    '1'
  );

INSERT IGNORE INTO `authorities`
VALUES
  ('admin', 'admin');

CREATE TABLE IF NOT EXISTS `customer` (
  `id` mediumint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT IGNORE INTO
  `customer` (`email`, `pwd`, `role`)
VALUES
  (
    'happy@example.com',
    '{bcrypt}$2a$12$9CVTmglu7SXoLhXTRv1Dv.26fo0ZVbtF2tkviEZtzk1pFo//Qc.3u',
    'read'
  );

INSERT IGNORE INTO
  `customer` (`email`, `pwd`, `role`)
VALUES
  (
    'admin@example.com',
    '{bcrypt}$2a$12$kfg0mphXmIGS3.fmbWqA5OSGUNokElSVkSJzhZWX/HNTxxpjclllS',
    'admin'
  );
DELETE FROM `authorities`;

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
  (1, 'ROLE_USER');

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
  (1, 'ROLE_ADMIN');
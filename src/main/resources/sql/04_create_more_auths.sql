CREATE TABLE IF NOT EXISTS `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
);

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
  (1, 'VIEWACCOUNT');

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
  (1, 'VIEWCARDS');

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
  (1, 'VIEWLOANS');

INSERT IGNORE INTO `authorities` (`customer_id`, `name`)
VALUES
  (1, 'VIEWBALANCE');

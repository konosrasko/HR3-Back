DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `leave_request`;
DROP TABLE IF EXISTS `leave_balance`;
DROP TABLE IF EXISTS `leave_category`;
DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mobile_number` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `is_enabled` tinyint DEFAULT NULL,
  `supervisor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_supervisor` (`supervisor_id`),
  CONSTRAINT `fk_employee_supervisor` FOREIGN KEY (`supervisor_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `leave_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `leave_balance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `leave_category_id` int DEFAULT NULL,
  `days` int DEFAULT NULL,
  `days_taken` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id_idx` (`employee_id`),
  KEY `leave_category_id_idx` (`leave_category_id`),
  CONSTRAINT `fk_2_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_2_leave_balance` FOREIGN KEY (`leave_category_id`) REFERENCES `leave_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `leave_request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `leave_category_id` int DEFAULT NULL,
  `submit_date` datetime DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id_idx` (`employee_id`),
  KEY `leave_category_id_idx` (`leave_category_id`),
  CONSTRAINT `fk_3_employee_id ` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_3_leave_category` FOREIGN KEY (`leave_category_id`) REFERENCES `leave_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_enabled` tinyint DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id_idx` (`employee_id`),
  CONSTRAINT `fk_4_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `mobile_number`, `address`, `hire_date`, `is_enabled`) VALUES (1, 'Stamatis', 'Chatzis', 'schatzis@ots.gr', 'Panadreoy 164 Neapoli', '2310632434', '2023-04-01', true);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `mobile_number`, `address`, `hire_date`, `is_enabled`) VALUES (2, 'Paylos', 'Tsigaros', 'ptsigar@ots.gr', 'Magnisias 23 Stayroypoli', '6983957541', '2023-03-01', true);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `mobile_number`, `address`, `hire_date`, `is_enabled`) VALUES (3, 'Aretoympa', 'Aretoulidou', 'adimatati@ots.gr', 'Melenikoy 20', '6987142133', '2023-05-01', true);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `mobile_number`, `address`, `hire_date`, `is_enabled`) VALUES (4, 'Mixail', 'Thief', 'klevwotivrw@ots.gr', 'Sikelianoy 51 Dendropotamos', '6969696969', '2023-04-01', true);

INSERT INTO `leave_category` (`id`, `title`) VALUES (1, 'Kanoniki');
INSERT INTO `leave_category` (`id`, `title`) VALUES (2, 'Egkymonsinis');
INSERT INTO `leave_category` (`id`, `title`) VALUES (3, 'Aimodosias');

INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (1, 1, 1, 15, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (2, 1, 3, 5, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (3, 2, 2, 25, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (4, 3, 1, 10, 0);

INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (1, 1, 1, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (2, 2, 2, '2023-07-01 10:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (3, 3, 3, '2023-07-13 12:40:30', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (4, 4, 2, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (5, 1, 1, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');

INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`) VALUES (1, 'schatzis', 'katselis123', true, 1, 'Admin');
INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`) VALUES (2, 'pablo', 'tsigaro_anameno', true, 2, 'HR');
INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`) VALUES (3, 'areti', 'mex', true, 3, 'Employee');
INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`) VALUES (4, 'kleftis', 'kleftis', true, 1, 'Admin');
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `leave_request`;
DROP TABLE IF EXISTS `leave_balance`;
DROP TABLE IF EXISTS `leave_category`;
DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int NOT NULL,
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
  `id` int NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `leave_balance` (
  `id` int NOT NULL,
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
  `id` int NOT NULL,
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
  `id` int NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_enabled` tinyint DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id_idx` (`employee_id`),
  CONSTRAINT `fk_4_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
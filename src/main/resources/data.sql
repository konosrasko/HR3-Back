INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`)
VALUES (1, 'Admin', 'Administrator', null, null, null, null, true, null)
ON DUPLICATE KEY UPDATE `id` = `id`;

INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`, `is_supervisor`, `logged`)
VALUES (1, 'admin', '$2a$12$zTpqqS.3RuqmCBvMwPqs.uQc9sGNvXB8hvXKRtn8w2lG51g8Lpj.m', true, 1, 'Admin', true, 0)
ON DUPLICATE KEY UPDATE `id` = `id`;

INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`)
VALUES (2, 'HR', 'Human Resources', null, null, null, null, true, null)
ON DUPLICATE KEY UPDATE `id` = `id`;

INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`, `is_supervisor`, `logged`)
VALUES (2, 'hr', '$2a$12$6oPPWxm2Da95bU7NbPqTseWYz1aLn7Q5CNER37VbmKTxxjSLj9a4C', true, 2, 'HR', true, 0)
ON DUPLICATE KEY UPDATE `id` = `id`;
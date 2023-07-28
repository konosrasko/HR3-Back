INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`) VALUES (1, 'Stamatis', 'Chatzis', 'schatzis@ots.gr', 'Panadreoy 164 Neapoli', '2310632434', '2023-04-01', true);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`) VALUES (2, 'Paylos', 'Tsigaros', 'ptsigar@ots.gr', 'Magnisias 23 Stayroypoli', '6983957541', '2023-03-01', true);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`) VALUES (3, 'Aretoympa', 'Aretoulidou', 'adimatati@ots.gr', 'Melenikoy 20', '6987142133', '2023-05-01', true);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`) VALUES (4, 'Mixail', 'Thief', 'klevwotivrw@ots.gr', 'Sikelianoy 51 Dendropotamos', '6969696969', '2023-04-01', true);

INSERT INTO `leave_category` (`id`, `title`) VALUES (1, 'Kanoniki');
INSERT INTO `leave_category` (`id`, `title`) VALUES (2, 'Egkymonsinis');
INSERT INTO `leave_category` (`id`, `title`) VALUES (3, 'Aimodosias');

INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (1, 1, 1, 15, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (2, 1, 3, 5, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (3, 2, 2, 25, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (4, 3, 1, 10, 0);

INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (1, 1, 1, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (2, 2, 2, '2023-05-02 10:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (3, 3, 3, '2023-04-13 12:40:30', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (4, 4, 2, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (5, 1, 1, '2023-04-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'PENDING');

INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`, `is_supervisor`) VALUES (1, 'schatzis', 'katselis123', true, 1, 'ADMIN', false);
INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`, `is_supervisor`) VALUES (2, 'pablo', 'tsigaro_anameno', true, 2, 'HR', false);
INSERT INTO `user` (`id`, `username`, `password`, `is_enabled`, `employee_id`, `role`, `is_supervisor`) VALUES (3, 'areti', 'mex', true, 3, 'EMPLOYEE', false);
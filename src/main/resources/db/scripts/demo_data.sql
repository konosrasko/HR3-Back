INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`) VALUES (1, 'Σταμάτης', 'Χατζής', 'schatzis@ots.gr', 'Παπανδρέου 164 ', '2310632434', '2023-04-01', true, 1);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`) VALUES (2, 'Βλοσπα', 'Γάρος', 'ptsigar@ots.gr', 'Στη στιγμή', '6983957541', '2023-03-01', true, 1);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`) VALUES (5, 'Ευη', 'Αγχωμένη', 'evi.stressed@ots.gr', 'Μοναστηρίου 60', '6969696969', '2021-04-01', true,2);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`) VALUES (3, 'Αρετούλα', 'Αρετουλίδου', 'adimatati@ots.gr', 'Μελενίνου 20', '6987142133', '2023-05-01', true,2);
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`, `address`, `mobile_number`, `hire_date`, `is_enabled`,`supervisor_id`) VALUES (4, 'Μιχαήλ', 'Ελβενός', 'klevwotivrw@ots.gr', 'Σπίτι σου', '6969696969', '2023-04-01', true,5);



INSERT INTO `leave_category` (`id`, `title`) VALUES (1, 'Κανονική');
INSERT INTO `leave_category` (`id`, `title`) VALUES (2, 'Γάμου');
INSERT INTO `leave_category` (`id`, `title`) VALUES (3, 'Αιμοδοσίας');



INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (1, 1, 1, 15, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (2, 1, 2, 15, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (3, 2, 1, 15, 0);
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_category_id`, `days`, `days_taken`) VALUES (4, 2, 2, 15, 5);




INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (1, 1, 1, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'DENIED');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (2, 2, 2, '2023-05-02 10:00:00', '2023-06-01', '2023-07-01', 30, 'DENIED');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (3, 3, 3, '2023-04-13 12:40:30', '2023-06-01', '2023-07-01', 30, 'DENIED');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (4, 4, 2, '2023-05-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'DENIED');
INSERT INTO `leave_request` (`id`, `employee_id`, `leave_category_id`, `submit_date`, `start_date`, `end_date`, `duration`, `status`) VALUES (5, 1, 1, '2023-04-30 12:00:00', '2023-06-01', '2023-07-01', 30, 'DENIED');


INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`,`is_supervisor`) VALUES (1, 'schatzis', '$2a$12$HbC4Qa3XDqoUIxtia5o19OptfHIeQccF1kWapk9yRztFlafnwoE4i', true, 1, 'Admin',1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`,`is_supervisor`) VALUES (2, 'pablo', '$2a$12$HbC4Qa3XDqoUIxtia5o19OptfHIeQccF1kWapk9yRztFlafnwoE4i', true, 2, 'HR',1);
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`,`is_supervisor`) VALUES (5, 'evihr', '$2a$12$HbC4Qa3XDqoUIxtia5o19OptfHIeQccF1kWapk9yRztFlafnwoE4i', true, 5, 'HR',1 );
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`,`is_supervisor`) VALUES (3, 'areti', '$2a$12$HbC4Qa3XDqoUIxtia5o19OptfHIeQccF1kWapk9yRztFlafnwoE4i', true, 3, 'Employee',0 );
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `employee_id`, `role`,`is_supervisor`) VALUES (4, 'kleftis', '$2a$12$HbC4Qa3XDqoUIxtia5o19OptfHIeQccF1kWapk9yRztFlafnwoE4i', true, 4, 'Employee',0 );

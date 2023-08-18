ALTER TABLE `open3hr`.`leave_request`
CHANGE COLUMN `start_date` `start_date` VARCHAR(40) NULL DEFAULT NULL ,
CHANGE COLUMN `end_date` `end_date` VARCHAR(40) NULL DEFAULT NULL ;

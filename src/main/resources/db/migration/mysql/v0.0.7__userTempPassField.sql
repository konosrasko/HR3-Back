ALTER TABLE `open3hr`.`user`
ADD COLUMN `is_pass_temp` TINYINT NULL DEFAULT '1' AFTER `is_supervisor`;

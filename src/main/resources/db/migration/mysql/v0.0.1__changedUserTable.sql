ALTER TABLE `open3hr`.`user`
DROP COLUMN `is_supervisor`,
CHANGE COLUMN `is_enabled` `enabled` TINYINT NULL DEFAULT NULL;

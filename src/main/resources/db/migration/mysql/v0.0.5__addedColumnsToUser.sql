ALTER TABLE `open3hr`.`user`
ADD COLUMN `latest_token` VARCHAR(256) NULL AFTER `is_supervisor`,
ADD COLUMN `is_logged_in` TINYINT NOT NULL DEFAULT 0 AFTER `latest_token`,
ADD UNIQUE INDEX `latest_token_UNIQUE` (`latest_token` ASC) VISIBLE;

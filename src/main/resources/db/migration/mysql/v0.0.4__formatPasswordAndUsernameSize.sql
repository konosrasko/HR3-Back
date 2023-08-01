ALTER TABLE `open3hr`.`user`
    CHANGE COLUMN `username` `username` VARCHAR(128) NULL DEFAULT NULL ,
    CHANGE COLUMN `password` `password` VARCHAR(128) NULL DEFAULT NULL ;

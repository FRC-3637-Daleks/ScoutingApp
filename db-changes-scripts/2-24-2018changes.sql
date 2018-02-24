ALTER TABLE `scoutingtags`.`tags` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`tag`, `year`, `type`);

ALTER TABLE `scoutingtags`.`teams` 
CHANGE COLUMN `name` `name` VARCHAR(1024) NULL DEFAULT NULL ;
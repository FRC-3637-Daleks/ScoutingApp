CREATE TABLE `scoutingtags`.`teams_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `team` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `team_UNIQUE` (`team` ASC));

ALTER TABLE `scoutingtags`.`teams_list` 
CHANGE COLUMN `team` `team_number` INT(11) NOT NULL ,
ADD COLUMN `city` VARCHAR(128) NULL AFTER `team_number`;

ALTER TABLE `scoutingtags`.`teams_list` 
ADD COLUMN `name` VARCHAR(1024) NULL AFTER `city`,
ADD COLUMN `country` VARCHAR(128) NULL AFTER `name`,
ADD COLUMN `rookie_year` INT(11) NULL AFTER `country`;

ALTER TABLE `scoutingtags`.`teams_list` 
CHANGE COLUMN `name` `name` VARCHAR(128) NULL DEFAULT NULL AFTER `team_number`;
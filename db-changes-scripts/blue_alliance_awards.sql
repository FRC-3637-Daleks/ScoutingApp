CREATE TABLE `scoutingtags`.`blue_alliance_awards` (
  `id` INT NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  `event_key` VARCHAR(128) NOT NULL,
  `year` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  `team` VARCHAR(128) NOT NULL;

ALTER TABLE `scoutingtags`.`blue_alliance_awards` 
CHANGE COLUMN `team` `team` INT NOT NULL ;

ALTER TABLE `scoutingtags`.`blue_alliance_awards` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;


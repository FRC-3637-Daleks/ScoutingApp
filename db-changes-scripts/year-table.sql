CREATE TABLE `scoutingtags`.`competition_year` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `year` INT NOT NULL,
  `active` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `year_UNIQUE` (`year` ASC));

  INSERT INTO `scoutingtags`.`competition_year` (`year`, `active`) VALUES ('2017', '0');
INSERT INTO `scoutingtags`.`competition_year` (`year`, `active`) VALUES ('2018', '1');

ALTER TABLE `scoutingtags`.`tags` 
CHANGE COLUMN `year` `year` INT(11) NOT NULL ;

ALTER TABLE `scoutingtags`.`tags` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`tag`, `year`);
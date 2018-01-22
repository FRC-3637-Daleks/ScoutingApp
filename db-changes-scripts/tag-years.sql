ALTER TABLE `scoutingtags`.`event` 
ADD COLUMN `year` INT NULL AFTER `year`;
ALTER TABLE `scoutingtags`.`tags` 
ADD COLUMN `year` INT NULL AFTER `year`;
update scoutingtags.event set year = 2017;
update scoutingtags.tags set year = 2017;
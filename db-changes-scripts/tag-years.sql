ALTER TABLE `scoutingtags`.`event` 
ADD COLUMN `year` INT NULL;
ALTER TABLE `scoutingtags`.`tags` 
ADD COLUMN `year` INT NULL;
update scoutingtags.event set year = 2017;
update scoutingtags.tags set year = 2017;
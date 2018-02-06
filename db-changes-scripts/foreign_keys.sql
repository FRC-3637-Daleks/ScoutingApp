ALTER TABLE `scoutingtags`.`teamtags` 
DROP FOREIGN KEY `fk_teamtags_1`;
ALTER TABLE `scoutingtags`.`teamtags` 
DROP INDEX `fk_teamtags_1_idx` ;

ALTER TABLE `scoutingtags`.`matchtags` 
DROP FOREIGN KEY `fk_matchtags_2`,
DROP FOREIGN KEY `fk_matchtags_1`;
ALTER TABLE `scoutingtags`.`matchtags` 
DROP INDEX `fk_matchtags_1_idx` ,
DROP INDEX `index4` ;
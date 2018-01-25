ALTER TABLE `scoutingtags`.`event` 
ADD COLUMN `event_date` DATE NULL AFTER `year`;

UPDATE `scoutingtags`.`event` SET `event_date`='2017-03-17' WHERE `id`='1';
UPDATE `scoutingtags`.`event` SET `event_date`='2017-04-02' WHERE `id`='2';
UPDATE `scoutingtags`.`event` SET `event_date`='2017-04-05' WHERE `id`='3';

INSERT INTO `scoutingtags`.`event` (`event_id`, `active`, `year`, `event_date`) VALUES ('2018njfla', '0', '2018', '2018-03-11');
INSERT INTO `scoutingtags`.`event` (`event_id`, `active`, `year`, `event_date`) VALUES ('2018njski', '0', '2018', '2018-03-25');
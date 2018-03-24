ALTER TABLE `scoutingtags`.`teams` 
ADD COLUMN `alliance` INT(11) AFTER `event_id`,
ADD COLUMN `alliance_selection_order` INT(11) AFTER `alliance`;
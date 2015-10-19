DROP PROCEDURE IF EXISTS getTagsForTeam;
delimiter //
CREATE PROCEDURE getTagsForTeam (
	IN teamNum INT
)
BEGIN
    SET @cols = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches') - 4;
    SET @i = 0;
    SET @makeCol = '';
    WHILE @i < @cols DO
		SET @makeCol = CONCAT(@makeCol, ' SELECT DISTINCT `tag', @i, '` FROM matches WHERE `team`=', teamNum, ' AND `tag', @i, '` IS NOT NULL UNION ALL');
		SET @i = @i + 1;
    END WHILE;
    SET @makeCol = LEFT(@makeCol, LENGTH(@makeCol)-LENGTH('UNION ALL'));
    SELECT @makeCol;
    PREPARE stmt FROM @makeCol;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END//
delimiter ;
CALL getTagsForTeam(3637);
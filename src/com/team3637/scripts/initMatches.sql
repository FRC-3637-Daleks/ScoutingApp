DROP PROCEDURE IF EXISTS addCols#
CREATE PROCEDURE addCols(IN newCols INTEGER)
BEGIN
	SET @cols = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches') - 4;
	WHILE @cols < newCols DO
		SET @makeCol = CONCAT('ALTER TABLE matches ADD COLUMN `tag', @cols, '` VARCHAR(45);');
        prepare stmt from @makeCol;
		execute stmt;
		SET @cols = @cols + 1;
	END WHILE;
END #
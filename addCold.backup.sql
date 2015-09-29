DROP PROCEDURE IF EXISTS addCols;
delimiter //
CREATE PROCEDURE addCols(
  IN tableName VARCHAR(20),
  IN newCols INTEGER
)
BEGIN
	SET @cols = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches') - 4;
	WHILE @cols < newCols DO
		SET @makeCol = CONCAT('ALTER TABLE ',tableName , ' ADD COLUMN `tag', @cols, '` VARCHAR(45);');
		PREPARE stmt FROM @makeCol;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
		SET @cols = @cols + 1;
	END WHILE;
END //
delimiter ;
CALL addCols('matches', 1);
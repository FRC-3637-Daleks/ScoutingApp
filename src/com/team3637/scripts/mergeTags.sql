DROP PROCEDURE IF EXISTS mergeTags|
CREATE PROCEDURE mergeTags(
  IN tableName VARCHAR(20),
  IN noTagCols INT,
  IN oldTag    VARCHAR(45),
  IN newTag    VARCHAR(45)
)
  BEGIN
    SET @cols = (SELECT COUNT(*)
                 FROM INFORMATION_SCHEMA.COLUMNS
                 WHERE TABLE_SCHEMA = 'scoutingtags' AND table_name = tableName) - noTagCols;
    SET @i = 0;
    WHILE @i < @cols DO
      SET @makeCol = CONCAT('UPDATE ', tableName, ' SET `tag', @i, '`=\'', newTag, '\' WHERE `tag', @i, '`=\'', oldTag,
                            '\'');
      PREPARE stmt FROM @makeCol;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
      SET @i = @i + 1;
    END WHILE;
  END |
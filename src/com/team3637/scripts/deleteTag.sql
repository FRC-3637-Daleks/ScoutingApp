DROP PROCEDURE IF EXISTS deleteTag|
CREATE PROCEDURE deleteTag(
  IN tagName VARCHAR(45)
)
  BEGIN
    SET @cols = (SELECT COUNT(*)
                 FROM INFORMATION_SCHEMA.COLUMNS
                 WHERE TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches') - 4;
    SET @i = 0;
    WHILE @i < @cols DO
      SET @makeCol = CONCAT('UPDATE matches SET `tag', @i, '`=null WHERE `tag', @i, '`=\'', tagName, '\'');
      PREPARE stmt FROM @makeCol;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
      SET @i = @i + 1;
    END WHILE;
    SET @cols = (SELECT COUNT(*)
                 FROM INFORMATION_SCHEMA.COLUMNS
                 WHERE TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams') - 4;
    SET @i = 0;
    WHILE @i < @cols DO
      SET @makeCol = CONCAT('UPDATE teams SET `tag', @i, '`=null WHERE `tag', @i, '`=\'', tagName, '\'');
      PREPARE stmt FROM @makeCol;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
      SET @i = @i + 1;
    END WHILE;
  END |
DROP PROCEDURE IF EXISTS addTag;
delimiter //
CREATE PROCEDURE addTag (
	IN tableName VARCHAR(20),
	IN tagName VARCHAR(45)
)
BEGIN
    #SELECT tagName;
    #SET @isTag = IFNULL((SELECT tag FROM tags WHERE tag = tagName), 0);
    #SELECT @isTag;
    #SET @tagName = (SELECT tag FROM tags WHERE tag = tagName);
    #SELECT @tagName;
    IF (SELECT tag FROM tags WHERE tag = tagName) IS NULL THEN
      SET @addTag = CONCAT('INSERT INTO tags (tag, type) VALUES (\'', tagName, '\',\'', tableName, '\');');
      SELECT @addTag;
      PREPARE stmt FROM @addTag;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
      SELECT CONCAT('Added tag \'', tagName, '\'');
	ELSE
		SELECT CONCAT('Tag \'',tagName ,'\' already exists');
    END IF;
END//
delimiter ;
CALL addTag('matches', 'Zee');
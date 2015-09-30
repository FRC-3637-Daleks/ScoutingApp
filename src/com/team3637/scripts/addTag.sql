DROP PROCEDURE IF EXISTS addTag|
CREATE PROCEDURE addTag (
  IN tagName VARCHAR(45)
)
  BEGIN
    IF (SELECT tag FROM tags WHERE tag = tagName) IS NULL THEN
      SET @addTag = CONCAT('INSERT INTO tags (tag) VALUES (\'', tagName, '\');');
      SELECT @addTag;
      PREPARE stmt FROM @addTag;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
    END IF;
  END |
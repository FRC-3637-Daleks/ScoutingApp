DROP PROCEDURE IF EXISTS addTags#
CREATE PROCEDURE addTags (
  IN _id INT,
  in colNum INT
)
  BEGIN
    SET @getCol = CONCAT('SELECT @col := tag',colNum ,' FROM matches WHERE `id` = ?;');
    PREPARE stmt FROM @getCol;
    SET @id = _id;
    EXECUTE stmt USING @id;
    DEALLOCATE PREPARE stmt;
    SELECT @col;
    SET @isTag = IFNULL((SELECT tag FROM tags WHERE tag = @col), 0);
    SELECT @isTag;
    IF @isTag = 0 THEN
      SET @addTag = CONCAT('INSERT INTO tags (tag) VALUES (\'', @col, '\');');
      SELECT @addTag;
      PREPARE stmt FROM @addTag;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
      SELECT CONCAT('Added: ', @col);
    END IF;
  END #
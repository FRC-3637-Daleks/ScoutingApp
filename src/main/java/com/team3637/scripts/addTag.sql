/*Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 Copyright (C) 2016  Team 3637

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
DROP PROCEDURE IF EXISTS addTag|
CREATE PROCEDURE addTag (
  IN tableName VARCHAR(20),
  IN tagName VARCHAR(45)
)
  BEGIN
    IF (SELECT tag FROM tags WHERE tag = tagName) IS NULL THEN
      SET @addTag = CONCAT('INSERT INTO tags (tag, type) VALUES (\'', tagName, '\',\'', tableName, '\');');
      SELECT @addTag;
      PREPARE stmt FROM @addTag;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
    END IF;
  END |
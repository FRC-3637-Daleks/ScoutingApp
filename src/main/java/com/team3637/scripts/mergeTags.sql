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
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
DROP PROCEDURE IF EXISTS init|
CREATE PROCEDURE init()
  BEGIN
    CREATE TABLE IF NOT EXISTS matches (
      id       INT NOT NULL AUTO_INCREMENT,
      matchNum INT NOT NULL,
      team     INT NOT NULL,
      score    INT NOT NULL,
      PRIMARY KEY (`matchNum`,`team`),
      UNIQUE KEY `id_UNIQUE` (`id`)
    );

    IF (SELECT COUNT(INDEX_NAME)
        FROM INFORMATION_SCHEMA.STATISTICS
        WHERE
          `TABLE_CATALOG` = 'def' AND `TABLE_SCHEMA` = DATABASE() AND
          `TABLE_NAME` = 'matches' AND `INDEX_NAME` = 'id_UNIQUE') = 0
    THEN
      CREATE UNIQUE INDEX id_UNIQUE ON matches (id);
    END IF;

    CREATE TABLE IF NOT EXISTS schedule (
      id       INT NOT NULL AUTO_INCREMENT,
      matchNum INT,
      b1       INT,
      b2       INT,
      b3       INT,
      r1       INT,
      r2       INT,
      r3       INT,
      PRIMARY KEY (`matchNum`),
      UNIQUE KEY `id_UNIQUE` (`id`),
      UNIQUE KEY `schedule_matchNum_uindex` (`matchNum`)
    );
    IF (SELECT COUNT(INDEX_NAME)
        FROM INFORMATION_SCHEMA.STATISTICS
        WHERE
          `TABLE_CATALOG` = 'def' AND `TABLE_SCHEMA` = DATABASE() AND
          `TABLE_NAME` = 'schedule' AND `INDEX_NAME` = 'id_UNIQUE') = 0
    THEN
      CREATE UNIQUE INDEX id_UNIQUE ON schedule (id);
    END IF;

    CREATE TABLE IF NOT EXISTS  tags (
      id   INT         NOT NULL AUTO_INCREMENT,
      tag  VARCHAR(45) NOT NULL,
      type VARCHAR(45) NOT NULL,
      expression VARCHAR(45) NULL,
      PRIMARY KEY (`tag`,`type`),
      UNIQUE KEY `id_UNIQUE` (`id`)
    );
    IF (SELECT COUNT(INDEX_NAME)
        FROM INFORMATION_SCHEMA.STATISTICS
        WHERE
          `TABLE_CATALOG` = 'def' AND `TABLE_SCHEMA` = DATABASE() AND
          `TABLE_NAME` = 'tags' AND `INDEX_NAME` = 'id_UNIQUE') = 0
    THEN
      CREATE UNIQUE INDEX id_UNIQUE ON tags (id);
    END IF;

    CREATE TABLE IF NOT EXISTS  teams (
      id       INT         NOT NULL AUTO_INCREMENT,
      team     INT         NOT NULL,
      avgscore FLOAT(6, 2) NOT NULL DEFAULT 0.00,
      matches  INT         NOT NULL DEFAULT 0,
      PRIMARY KEY (`team`),
      UNIQUE KEY `id_UNIQUE` (`id`),
      UNIQUE KEY `team_UNIQUE` (`team`)
    );
    IF (SELECT COUNT(INDEX_NAME)
        FROM INFORMATION_SCHEMA.STATISTICS
        WHERE
          `TABLE_CATALOG` = 'def' AND `TABLE_SCHEMA` = DATABASE() AND
          `TABLE_NAME` = 'teams' AND `INDEX_NAME` = 'id_UNIQUE') = 0
    THEN
      CREATE UNIQUE INDEX id_UNIQUE ON teams (id);
    END IF;
    IF (SELECT COUNT(INDEX_NAME)
        FROM INFORMATION_SCHEMA.STATISTICS
        WHERE
          `TABLE_CATALOG` = 'def' AND `TABLE_SCHEMA` = DATABASE() AND
          `TABLE_NAME` = 'teams' AND `INDEX_NAME` = 'team_UNIQUE') = 0
    THEN
      CREATE UNIQUE INDEX team_UNIQUE ON teams (team);
    END IF;
  END |
CALL init()|
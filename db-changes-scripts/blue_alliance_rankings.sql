CREATE TABLE `blue_alliance_rankings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team` int(11) NOT NULL,
  `matches_played` int(11) DEFAULT NULL,
  `qual_average` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `disqualifications` int(11) DEFAULT NULL,
  `wins` int(11) DEFAULT NULL,
  `losses` int(11) DEFAULT NULL,
  `ties` int(11) DEFAULT NULL,
  `event_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `team_event_uq` (`team`,`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;


INSERT INTO stops VALUES ('STOP1'), ('STOP2'), ('STOP3'), ('STOP4');
INSERT INTO lines VALUES ('LINE1', 'STOP1'), ('LINE2', 'STOP2');
INSERT INTO stopLines VALUES ('STOP1', 'LINE1'), ('STOP2', 'LINE1'), ('STOP2', 'LINE2'), ('STOP3', 'LINE1'), ('STOP4', 'LINE2');
INSERT INTO lineSegments VALUES ('LINE1', 0, 4, 10, 'STOP2'), ('LINE1', 1, 7, 10, 'STOP3'), ('LINE2', 0, 10, 8, 'STOP4');
INSERT INTO passengers VALUES ('LINE1', 0, 1, 0), ('LINE1', 0, 13, 0), ('LINE1', 0, 20, 0),
 ('LINE1', 1, 5, 0), ('LINE1', 1, 17, 0), ('LINE1', 1, 24, 0),
 ('LINE2', 0, 4, 0), ('LINE2', 0, 10, 0), ('LINE2', 0, 20, 0);

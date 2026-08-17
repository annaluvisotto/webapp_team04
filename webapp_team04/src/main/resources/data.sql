SELECT 1; -- non significa nulla, l'ho aggiunto perchè data.sql non può essere vuoto
INSERT INTO Users VALUES (1, admin, password, TRUE);
INSERT INTO Users VALUES (2, sofia, sofia, TRUE);
INSERT INTO Users VALUES (3, anna, anna, TRUE);
INSERT INTO Users VALUES (4, giovanna, giovanna, TRUE);

INSERT INTO Authorities VALUES (1, admin, ROLE_ADMIN);
INSERT INTO Authorities VALUES (2, sofia, ROLE_USER_PRO);
INSERT INTO Authorities VALUES (3, anna, ROLE_USER_BASIC);
INSERT INTO Authorities VALUES (4, giovanna, giovanna, ROLE_USER_PROVA);

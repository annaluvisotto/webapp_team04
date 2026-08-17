SELECT 1; -- non significa nulla, l'ho aggiunto perchè data.sql non può essere vuoto
INSERT INTO Users VALUES (1, admin, password, TRUE);
INSERT INTO Users VALUES (2, sofia, sofia, TRUE);
INSERT INTO Users VALUES (3, anna, anna, TRUE);
INSERT INTO Users VALUES (4, giovanna, giovanna, TRUE);

INSERT INTO Authorities VALUES (1, admin, ROLE_ADMIN);
INSERT INTO Authorities VALUES (2, sofia, ROLE_USER_PRO);
INSERT INTO Authorities VALUES (3, anna, ROLE_USER_BASIC);
INSERT INTO Authorities VALUES (4, giovanna, giovanna, ROLE_USER_PROVA);

INSERT INTO Users (username, password, enabled)
VALUES ('admin#team_04', 'adm_id_04', true);

INSERT INTO Authorities (username, authority)
VALUES ('admin#team_04', 'ROLE_ADMIN');

INSERT INTO Users_info (ID, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'admin#team_04'),
           'Anna',
           'Luvisotto',
           '2005-03-21',
           'anna@email.com',
           CURRENT_DATE
       );

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
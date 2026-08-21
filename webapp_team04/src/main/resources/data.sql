-- utenti test, non toccare le password

-- login con username: admin, pw: admin
INSERT INTO Users (username, password, enabled)
VALUES ('admin', '$2a$10$ClMmt/SOdXDgf9hmfil5NeAEf3OSOQS5Asqb0PpG6iC.Flx7ZAkqC', true);

INSERT INTO Authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO Users_info (ID, username, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'admin'),
            'admin',
           'Admin',
           'Admin',
           '2005-03-21',
           'admin@email.com',
           CURRENT_DATE
       );


-- login con username: prova, pw: prova
INSERT INTO Users (username, password, enabled)
VALUES ('prova', '$2a$10$1hq0AqMSxin/bu5ZvlRdB.uaQeP8n6ZbyeYMut2l1JuprmF1KCFFm', true);

INSERT INTO Authorities (username, authority)
VALUES ('prova', 'ROLE_USER_PROVA');

INSERT INTO Users_info (ID, username, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'prova'),
            'prova',
           'Anna',
           'Luvisotto',
           '2005-03-21',
           'anna@email.com',
           CURRENT_DATE
       );


-- login con username: basic, pw: basic
INSERT INTO Users (username, password, enabled)
VALUES ('basic', '$2a$10$Z4IcIosP3UeeRKG3Wey.7.kxczlOGpX1szfl.0D5MI.cRQwLHGq1O', true);

INSERT INTO Authorities (username, authority)
VALUES ('basic', 'ROLE_USER_BASIC');

INSERT INTO Users_info (ID, username, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'basic'),
            'basic',
           'Sofia',
           'Cestari',
           '2005-11-22',
           'sofia@email.com',
           CURRENT_DATE
       );


-- login con username: pro, pw: pro
INSERT INTO Users (username, password, enabled)
VALUES ('pro', '$2a$10$yFzT17ghKYrbl0LFg.VmLuWNMC6y/m5mMzOmmWw0AtBhVZ5lBgvTC', true);

INSERT INTO Authorities (username, authority)
VALUES ('pro', 'ROLE_USER_PRO');

INSERT INTO Users_info (ID, username, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'pro'),
            'pro',
           'Laura',
           'Prandina',
           '2005-06-23',
           'laura@email.com',
           CURRENT_DATE
       );




-- login con username: admin, pw: admin
-- login con username: prova, pw: prova
-- login con username: basic, pw: basic
-- login con username: pro, pw: pro
INSERT INTO Users (username, password, enabled)
VALUES ('admin', '$2a$10$ClMmt/SOdXDgf9hmfil5NeAEf3OSOQS5Asqb0PpG6iC.Flx7ZAkqC', true),
        ('prova', '$2a$10$1hq0AqMSxin/bu5ZvlRdB.uaQeP8n6ZbyeYMut2l1JuprmF1KCFFm', true),
        ('basic', '$2a$10$Z4IcIosP3UeeRKG3Wey.7.kxczlOGpX1szfl.0D5MI.cRQwLHGq1O', true),
        ('pro', '$2a$10$yFzT17ghKYrbl0LFg.VmLuWNMC6y/m5mMzOmmWw0AtBhVZ5lBgvTC', true);

INSERT INTO Authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN'),
       ('prova', 'ROLE_USER_PROVA'),
       ('basic', 'ROLE_USER_BASIC'),
       ('pro', 'ROLE_USER_PRO');

INSERT INTO Users_info (ID, username, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'admin'),
            'admin',
           'Admin',
           'Admin',
           '2005-03-21',
           'admin@email.com',
           CURRENT_DATE
       ),
       (
           (SELECT ID FROM Users WHERE username = 'prova'),
           'prova',
           'Anna',
           'Luvisotto',
           '2005-03-21',
           'anna@email.com',
           CURRENT_DATE
       ),
       (
           (SELECT ID FROM Users WHERE username = 'basic'),
           'basic',
           'Sofia',
           'Cestari',
           '2005-11-22',
           'sofia@email.com',
           CURRENT_DATE
       ),
       (
           (SELECT ID FROM Users WHERE username = 'pro'),
           'pro',
           'Laura',
           'Prandina',
           '2005-06-23',
           'laura@email.com',
           CURRENT_DATE
       );

INSERT INTO Default_Trainings_Exec (ID_User, ID_Training, esecuzioni)
VALUES ((SELECT ID FROM Users WHERE username = 'prova'), 1, 2),
       ((SELECT ID FROM Users WHERE username = 'basic'), 1, 6),
       ((SELECT ID FROM Users WHERE username = 'basic'), 2, 7),
       ((SELECT ID FROM Users WHERE username = 'basic'), 3, 1),
       ((SELECT ID FROM Users WHERE username = 'basic'), 4, 6),
       ((SELECT ID FROM Users WHERE username = 'pro'), 1, 7),
       ((SELECT ID FROM Users WHERE username = 'pro'), 2, 4),
       ((SELECT ID FROM Users WHERE username = 'pro'), 3, 3),
       ((SELECT ID FROM Users WHERE username = 'pro'), 4, 10);

INSERT INTO Personalized_Trainings_Exec (ID_User, nome_allenamento, esecuzioni)
VALUES ((SELECT ID FROM Users WHERE username = 'pro'), 'Upper', 6),
       ((SELECT ID FROM Users WHERE username = 'pro'), 'Lower', 6);

INSERT INTO Personalized_Trainings_Info (ID_Training, ID_User, nome_esercizio, numero_serie, numero_ripetizioni)
VALUES ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro'), 'Panca piana', 4, 6),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro'), 'Lat machine', 4, 7),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro'), 'Bicipiti ai cavi', 4, 6),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro'), 'Tricipiti ai cavi', 4, 7),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro'), 'Rematore', 4, 6),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro'), 'Alzate laterali', 4, 9),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro'), 'Squat', 3, 12),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro'), 'Affondi', 3, 17),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro'), 'Ponte glutei', 3, 22),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro'), 'Leg curl', 3, 10),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro'), 'Leg extension', 4, 9),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro'), 'Calf machine', 4, 9);

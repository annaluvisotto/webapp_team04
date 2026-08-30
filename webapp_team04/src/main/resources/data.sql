INSERT INTO Users (username, password, enabled)
VALUES ('admin04', '$2a$10$Wi6V6USdBWOtVAr78wBAQeqc7zgws.swTJ9pSE7jjEf0Qomfy6pFC', true),
        ('prova104', '$2a$10$Dmn2Qa7lQ.4i.D8Oe1nCZeQnqUW0lem0qnhvS7OQt7IjCfqviMKla', true),
        ('basic04', '$2a$10$0m.nxmq0k0SHWD1q2dePIuf9wtTvrByz/.bYwwdBOmO5Q63iCVpqu', true),
        ('pro04', '$2a$10$3pkQVoMO4Nlki1jQBEQGIOKxHrxwiS2Ta/NZUnE03eWPmMRaORF7G', true),
        ('prova204', '$2a$10$U2qQ0eagtrXVQz8Tv4CAkuf0a0l/BSQAq41Uutcg.YnT7TLjNpCt6', false);

INSERT INTO Authorities (username, authority)
VALUES ('admin04', 'ROLE_ADMIN'),
       ('prova104', 'ROLE_USER_PROVA'),
       ('basic04', 'ROLE_USER_BASIC'),
       ('pro04', 'ROLE_USER_PRO'),
       ('prova204', 'ROLE_USER_PROVA');

INSERT INTO Users_info (ID, username, nome, cognome, data_nascita, email, data_reg)
VALUES (
           (SELECT ID FROM Users WHERE username = 'admin04'),
            'admin04',
           'Admin',
           'Admin',
           '2005-03-21',
           'admin@email.com',
           '2026-08-01'
       ),
       (
           (SELECT ID FROM Users WHERE username = 'prova104'),
           'prova104',
           'Anna',
           'Luvisotto',
           '2005-03-21',
           'anna@email.com',
           '2026-08-02'
       ),
       (
           (SELECT ID FROM Users WHERE username = 'basic04'),
           'basic04',
           'Sofia',
           'Cestari',
           '2005-11-22',
           'sofia@email.com',
           '2026-08-03'
       ),
       (
           (SELECT ID FROM Users WHERE username = 'pro04'),
           'pro04',
           'Giovanna',
           'Varni',
           '2000-01-01',
           'giovanna@email.com',
           '2026-08-04'
       ),
       (
           (SELECT ID FROM Users WHERE username = 'prova204'),
            'prova204',
            'Mario',
            'Rossi',
            '2009-03-28',
            'mario@email.com',
            '2026-08-05'
       );

INSERT INTO Default_Trainings_Exec (ID_User, ID_Training, esecuzioni)
VALUES ((SELECT ID FROM Users WHERE username = 'prova104'), 1, 2),
       ((SELECT ID FROM Users WHERE username = 'prova204'), 2, 3),
       ((SELECT ID FROM Users WHERE username = 'basic04'), 1, 6),
       ((SELECT ID FROM Users WHERE username = 'basic04'), 2, 7),
       ((SELECT ID FROM Users WHERE username = 'basic04'), 3, 1),
       ((SELECT ID FROM Users WHERE username = 'basic04'), 4, 6),
       ((SELECT ID FROM Users WHERE username = 'pro04'), 1, 7),
       ((SELECT ID FROM Users WHERE username = 'pro04'), 2, 4),
       ((SELECT ID FROM Users WHERE username = 'pro04'), 3, 3),
       ((SELECT ID FROM Users WHERE username = 'pro04'), 4, 10);

INSERT INTO Personalized_Trainings_Exec (ID_User, nome_allenamento, esecuzioni)
VALUES ((SELECT ID FROM Users WHERE username = 'pro04'), 'Upper', 6),
       ((SELECT ID FROM Users WHERE username = 'pro04'), 'Lower', 6);

INSERT INTO Personalized_Trainings_Info (ID_Training, ID_User, nome_esercizio, numero_serie, numero_ripetizioni)
VALUES ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Panca piana', 4, 6),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Lat machine', 4, 7),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Bicipiti ai cavi', 4, 6),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Tricipiti ai cavi', 4, 7),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Rematore', 4, 6),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Upper'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Alzate laterali', 4, 9),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Squat', 3, 12),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Affondi', 3, 17),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Ponte glutei', 3, 22),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Leg curl', 3, 10),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Leg extension', 4, 9),
       ((SELECT ID_Training FROM Personalized_Trainings_Exec WHERE nome_allenamento = 'Lower'), (SELECT ID FROM Users WHERE username = 'pro04'), 'Calf machine', 4, 9);

INSERT INTO Reviews (ID_User, title, text)
VALUES ('2', 'Palestra carina!', 'Ho trovato questa palestra molto carina e fornita, i personal trainer sono stati molto competenti!'),
       ('3', 'Mai più...', 'Gli attrezzi sono rotti e la gente scortese, non ci tornerò mai più!');
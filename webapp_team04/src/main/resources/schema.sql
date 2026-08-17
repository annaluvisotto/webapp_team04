CREATE TABLE IF NOT EXISTS Users(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(32) NOT NULL UNIQUE,
    password varchar(512) NOT NULL,
    enabled boolean NOT NULL
    );

CREATE TABLE IF NOT EXISTS Authorities(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(32) NOT NULL,
    authority enum('ROLE_ADMIN', 'ROLE_USER_PROVA', 'ROLE_USER_BASIC', 'ROLE_USER_PRO') NOT NULL,
    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Users_info(
    ID INT PRIMARY KEY,
    nome varchar(32) NOT NULL,
    cognome varchar(32) NOT NULL,
    data_nascita date,
    email varchar(32) NOT NULL,
    username varchar(32) NOT NULL,
    password varchar(32) NOT NULL,
    data_reg date NOT NULL,
    FOREIGN KEY (ID) REFERENCES Users(ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Default_Trainings(
    ID_User INT NOT NULL,
    nome_allenamento varchar(32) NOT NULL,
    esecuzioni INT,
    PRIMARY KEY (nome_allenamento, ID_User),
    FOREIGN KEY (ID_User) REFERENCES Users(ID)
    );

CREATE TABLE IF NOT EXISTS Personalized_Trainings(
    ID_Training INT AUTO_INCREMENT NOT NULL,
    ID_User INT NOT NULL,
    nome_allenamento varchar(32) NOT NULL,
    esecuzioni INT,
    PRIMARY KEY (ID_Training, ID_User),
    FOREIGN KEY (ID_User) REFERENCES Users(ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Personalized_Trainings_Info(
    ID_Training INT NOT NULL,
    ID_User INT NOT NULL,
    nome_esercizio varchar(32) NOT NULL,
    numero_serie INT NOT NULL,
    numero_ripetizioni INT NOT NULL,
    PRIMARY KEY (ID_Training, ID_User, nome_esercizio), -- l'esercizio è primary key perché gli utenti lo selezionano da una lista (limitata) che fa riferimento al servizio rest
    FOREIGN KEY (ID_User) REFERENCES Users(ID) ON DELETE CASCADE,
    FOREIGN KEY (ID_Training) REFERENCES Personalized_Trainings(ID_Training) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Reviews(
    ID_Review INT AUTO_INCREMENT PRIMARY KEY,
    ID_User INT NOT NULL,
    title varchar(32) NOT NULL,
    text varchar(512) NOT NULL,
    FOREIGN KEY (ID_User) REFERENCES Users(ID)
    );



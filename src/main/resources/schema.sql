CREATE TABLE IF NOT EXISTS Users(
    ID INT PRIMARY KEY,
    username varchar(32) NOT NULL,
    password varchar(32) NOT NULL,
    enabled boolean NOT NULL
    );

CREATE TABLE IF NOT EXISTS Authorities(
    ID INT PRIMARY KEY,
    username varchar(32) NOT NULL,
    ruolo enum('ROLE_ADMIN', 'ROLE_USER_PROVA', 'ROLE_USER_BASIC', 'ROLE_USER_PRO') NOT NULL,
    FOREIGN KEY (ID) REFERENCES Users(ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Users_info(
    ID INT PRIMARY KEY,
    nome varchar(32) NOT NULL,
    nome varchar(32) NOT NULL,
    data_nascita date,
    email varchar(32) NOT NULL,
    username varchar(32) NOT NULL,
    password varchar(32) NOT NULL,
    data_reg date,
    FOREIGN KEY (ID) REFERENCES Users(ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Default_Trainings(
    ID_Training INT,
    ID_User varchar(32) NOT NULL,
    esecuzioni INT,
    PRIMARY KEY (ID_Training, ID_User)
    );

CREATE TABLE IF NOT EXISTS Personalized_Trainings(
    ID_Training INT,
    ID_User varchar(32) NOT NULL,
    nome_allenamento varchar(32) NOT NULL,
    esecuzioni INT,
    PRIMARY KEY (ID_Training, ID_User),
    FOREIGN KEY (ID_User) REFERENCES Users(ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Personalized_Trainings_info(
    ID_Training INT,
    ID_User varchar(32) NOT NULL,
    nome_esercizio varchar(32) NOT NULL,
    numero_serie INT,
    numero_ripetizioni INT,
    PRIMARY KEY (ID_Training, ID_User, nome_esercizio), -- l'esercizio è primary key perché gli utenti lo selezionano da una lista (limitata) che fa riferimento al servizio rest
    FOREIGN KEY (ID_User) REFERENCES Users(ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Recensioni(
    ID_Recensione INT PRIMARY KEY,
    ID_User varchar(32) NOT NULL,
    titolo varchar(32) NOT NULL,
    testo varchar(512) NOT NULL,
    FOREIGN KEY (ID_User) REFERENCES Users(ID)
    );



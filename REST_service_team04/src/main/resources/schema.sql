CREATE TABLE IF NOT EXISTS Default_Trainings(
    ID_Training INT AUTO_INCREMENT PRIMARY KEY,
    nome_allenamento varchar(32) NOT NULL,
    );

CREATE TABLE IF NOT EXISTS Exercises(
    nome_esercizio varchar(32) PRIMARY KEY,
    kcal DECIMAL(10,5) NOT NULL,
    );

CREATE TABLE IF NOT EXISTS Default_Trainings_Info(
    ID_Training INT,
    nome_esercizio varchar(32) NOT NULL,
    numero_serie INT,
    numero_ripetizioni INT,
    kcal DECIMAL(10,5) NOT NULL,
    PRIMARY KEY (ID_Training, nome_esercizio),
    FOREIGN KEY (ID_Training) REFERENCES Default_Trainings(ID_Training) ON DELETE CASCADE
    FOREIGN KEY (nome_esercizio) REFERENCES Exercises(nome_esercizio) ON DELETE CASCADE
    );


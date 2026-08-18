INSERT INTO Default_Trainings (nome_allenamento) VALUES
('Full Body'), ('Push/Pull/Legs'), ('Cardio'), ('Strenght');

INSERT INTO Exercises VALUES 
('Panca piana', 0.75), ('Squat', 1.20), ('Lat machine', 0.65), ('Crunch addominali', 0.20), 
('Bicipiti ai cavi', 0.25), ('Tricipiti ai cavi', 0.25), ('Stacchi da terra', 1.40), 
('Rematore', 0.80), ('Affondi', 0.75), ('Croci ai cavi', 0.35), 
('Salto della corda', 0.20), ('Panca 45°', 0.70), ('Alzate laterali', 0.20),
('Leg curl', 0.35), ('Leg Press', 1.00), ('Leg extension', 0.35),
('Jumping Jacks', 0.12), ('Burpees', 1.30), ('Ponte glutei', 0.60),
('Calf machine', 0.25);

INSERT INTO Default_Trainings_Info (ID_Training, nome_esercizio, numero_serie, numero_ripetizioni, kcal) 
WITH Composizione_Allenamenti (nome_allenamento, nome_es, serie, rip) AS ( VALUES
('Full Body', 'Panca 45°', 3, 11), ('Full Body', 'Lat machine', 3, 11), 
('Full Body', 'Rematore', 3, 11), ('Full Body', 'Alzate laterali', 3, 13),
('Full Body', 'Leg curl', 3, 13), ('Full Body', 'Crunch addominali', 2, 20),

('Push/Pull/Legs', 'Panca piana', 4, 7), ('Push/Pull/Legs', 'Panca 45°', 3, 9),
('Push/Pull/Legs', 'Rematore', 4, 7), ('Push/Pull/Legs', 'Croci ai cavi', 3, 9),
('Push/Pull/Legs', 'Squat', 4, 7), ('Push/Pull/Legs', 'Leg press', 4, 11),

('Cardio', 'Crunch addominali', 3, 15), ('Cardio', 'Rematore', 3, 15), 
('Cardio', 'Salto della corda', 3, 15), ('Cardio', 'Ponte glutei', 3, 15), 
('Cardio', 'Jumping Jacks', 3, 15), ('Cardio', 'Burpees', 3, 15), 

('Strenght', 'Squat', 4, 5), ('Strenght', 'Stacchi da terra', 3, 9), 
('Strenght', 'Affondi', 4, 5), ('Strenght', 'Bicipiti ai cavi', 3, 9), 
('Strenght', 'Lat machine', 4, 5), ('Strenght', 'Tricipiti ai cavi', 3, 9)
)
SELECT dt.ID_Training, ca.nome_es, ca.serie, ca.rip, (ca.serie * ca.rip * e.kcal)
FROM Composizione_Allenamenti ca 
JOIN Default_Trainings dt ON dt.nome_allenamento = ca.nome_allenamento 
JOIN Exercises e ON e.nome_esercizio = ca.nome_es;

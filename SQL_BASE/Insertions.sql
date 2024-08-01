
INSERT INTO App_Users (id_User, Password, email_user, id_authentication) VALUES
('user1', '$2b$12$8l9GENjUnKByb8CbG7OOS.0RTOIcbMyY6xPpnHEQjNJ7i.JU3W81a', 'user1@example.com', 1),
('user2', '$2b$12$0g15ZxNXE/NH4jCmVjLhBuEz4xun54Yv.pf7q09UxhcJEBd2Xv.9m', 'user2@example.com', 1),
('user3', '$2b$12$LG.JOcfPsaLjATyJtXIK0eS7HTlMLg5z5HoJiIFEszYgP1CbiHuQ', 'user3@example.com', 1);

INSERT INTO Facebook_Users (id_User, email_user, id_authentication) VALUES
('fb_user1', 'fb_user1@example.com', 2),
('fb_user2', 'fb_user2@example.com', 2),
('fb_user3', 'fb_user3@example.com', 2);


INSERT INTO Google_Users (id_User, email_user, id_authentication) VALUES
('google_user1', 'google_user1@example.com', 3),
('google_user2', 'google_user2@example.com', 3),
('google_user3', 'google_user3@example.com', 3);

INSERT INTO Information_Users (id_User, nameUser, country, height, age) VALUES
('user1', 'John Doe', 'USA', 180.0, 30),
('user2', 'Jane Smith', 'Canada', 165.0, 25),
('user3', 'Alex Johnson', 'UK', 170.0, 35),
('fb_user1', 'Emily Brown', 'Australia', 160.0, 28),
('fb_user2', 'Michael Davis', 'Ireland', 175.0, 40),
('fb_user3', 'Sophia Wilson', 'New Zealand', 158.0, 22),
('google_user1', 'James Martinez', 'Spain', 185.0, 33),
('google_user2', 'Olivia Garcia', 'Mexico', 162.0, 29),
('google_user3', 'Liam Miller', 'South Africa', 178.0, 27);


INSERT INTO Progress_Users (id_User, DateStarting, id_level_activity, id_gender, id_goal, id_state_health) VALUES
('user1', current_date(), 1, 1, 1, 1)

INSERT INTO Progress_Users (id_User, DateStarting, id_level_activity, id_gender, id_goal, id_state_health) VALUES
('user1', current_date(), 1, 1, 1, 1),
('user2', current_date(), 2, 2, 2, 2),
('user3', current_date(), 3, 3, 3, 3),
('fb_user1', current_date(), 1, 1, 1, 1),
('fb_user2', current_date(), 2, 2, 2, 2),
('fb_user3', current_date(), 3, 3, 3, 3),
('google_user1', current_date(), 1, 1, 1, 1),
('google_user2', current_date(), 2, 2, 2, 2),
('google_user3', current_date(), 3, 3, 3, 3);


INSERT INTO Handle_Weight (id_User, StartingWeight, CurrentWeight, EndWeight, LostWeight, gainedWeight) VALUES
('user1', 80, 75, 70, 10, 0),
('user2', 90, 85, 80, 10, 0),
('user3', 100, 95, 90, 10, 0),
('fb_user1', 80, 75, 70, 10, 0),
('fb_user2', 90, 85, 80, 10, 0),
('fb_user3', 100, 95, 90, 10, 0),
('google_user1', 80, 75, 70, 10, 0),
('google_user2', 90, 85, 80, 10, 0),
('google_user3', 100, 95, 90, 10, 0);

INSERT INTO Daily_Diet (id_User, Calories, Protein, Carbohidrates, Fat) VALUES
('user1', 2500, 150, 300, 70),
('user2', 2200, 120, 250, 60),
('user3', 2700, 180, 350, 80),
('fb_user1', 2500, 150, 300, 70),
('fb_user2', 2200, 120, 250, 60),
('fb_user3', 2700, 180, 350, 80),
('google_user1', 2500, 150, 300, 70),
('google_user2', 2200, 120, 250, 60),
('google_user3', 2700, 180, 350, 80);


INSERT INTO Routine_Users (id_User, doc_routine) VALUES
('user1', 'Routine Document 1'),
('user2', 'Routine Document 2'),
('user3', 'Routine Document 3');


INSERT INTO Receipt_Users (id_User, doc_receipt) VALUES
('user1', 'Receipt Document 1'),
('user2', 'Receipt Document 2'),
('user3', 'Receipt Document 3');


INSERT INTO type_authentication (type_au) VALUES
('App'),
('Facebook'),
('Google');

INSERT INTO levels_activity (typeLevel, valueLevel) VALUES
('Sedentario', 1.2),
('Un Poco Activo',1.4),
('Moderadamente Activo', 1.55),
('Bastante Activo', 1.8),
('Super Activo', 2);

INSERT INTO Goals (typeGoals) VALUES
('Perder Peso'),
('Ganar Peso'),
('Mantener Peso');

INSERT INTO health_states (state_name) VALUES
('Bajo de peso'),
('Peso Normal'),
('Pasado de peso');


INSERT INTO Genders (gender) VALUES
('Femenino'),
('Masculino'),
('Prefiero No decirlo');





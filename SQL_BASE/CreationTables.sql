-- CREATE DATABASE bd_MyProgress_App;
-- CREATION OF THE TABLES


DROP TABLE IF EXISTS App_Users;
DROP TABLE IF EXISTS Facebook_Users;
DROP TABLE IF EXISTS Google_Users;
DROP TABLE IF EXISTS Daily_Diet;
DROP TABLE IF EXISTS Progress_Users;
DROP TABLE IF EXISTS Routine_Users;
DROP TABLE IF EXISTS Receipt_Users;
DROP TABLE IF EXISTS levels_activity;
DROP TABLE IF EXISTS Goals;
DROP TABLE IF EXISTS health_states;
DROP TABLE IF EXISTS handle_weight;
DROP TABLE IF EXISTS Genders;
DROP TABLE IF EXISTS type_authentication;
DROP TABLE IF EXISTS information_users;

CREAtE TABLE App_Users (
	id_User varchar(30) PRIMARY KEY  	,
	Password VARCHAR(100) NOT NULL,
    email_user VARCHAR(100) UNIQUE NOT NULL,
    id_authentication INT NOT NULL,
	CHECK (CHAR_LENGTH(id_user) BETWEEN 5 AND 15),
    CHECK (CHAR_LENGTH(email_user)  BETWEEN 10 AND 80),
    FOREIGN KEY (id_authentication) REFERENCES type_authentication(id)
);

CREAtE TABLE Facebook_Users (
	id_User varchar(30) PRIMARY KEY ,
    email_user VARCHAR(100) UNIQUE NOT NULL,
	 id_authentication INT NOT NULL,
	CHECK (CHAR_LENGTH(id_user) BETWEEN 5 AND 15),
    CHECK (CHAR_LENGTH(email_user)  BETWEEN 10 AND 80),
    FOREIGN KEY (id_authentication) REFERENCES type_authentication(id)
);

CREAtE TABLE Google_Users (
	id_User varchar(30) PRIMARY KEY  	,
    email_user VARCHAR(100) UNIQUE NOT NULL,
	id_authentication INT NOT NULL,
	CHECK (CHAR_LENGTH(id_user) BETWEEN 5 AND 15),
    CHECK (CHAR_LENGTH(email_user)  BETWEEN 10 AND 80),
    FOREIGN KEY (id_authentication) REFERENCES type_authentication(id)
);



CREATE TABLE Progress_Users (
    id_User VARCHAR(30) PRIMARY KEY,
    DateStarting DATE NOT NULL,
    id_level_activity INT NOT NULL,
    id_gender INT NOT NULL,
    id_goal INT,
    id_state_health INT,
	FOREIGN KEY (id_gender) REFERENCES Genders(id),
	FOREIGN KEY (id_goal) REFERENCES Goals(id),
	FOREIGN KEY (id_state_health) REFERENCES health_states(id),
	FOREIGN KEY (id_level_activity) REFERENCES levels_activity(id)
);



CREATE TABLE type_authentication (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type_au VARCHAR(50) NOT NULL
);

CREATE TABLE levels_activity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    typeLevel VARCHAR(50) NOT NULL,
    valueLevel double NOT NULL
);

CREATE TABLE Goals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    typeGoals VARCHAR(50) NOT NULL
);
CREATE TABLE health_states (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_name VARCHAR(50) NOT NULL
);

CREATE TABLE Genders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gender VARCHAR (10) NOT NULL
);



	CREATE TABLE Handle_Weight(
		id_User varchar(30) NOT NULL,
		StartingWeight double NOT NULL,
		CurrentWeight double NOT NULL,
		EndWeight double NOT NULL,
		LostWeight double not null,
		gainedWeight double not null,
		UNIQUE (id_User),
		FOREIGN KEY (id_User) REFERENCES Progress_Users (id_User) ON UPDATE CASCADE  ON DELETE CASCADE
	);

	CREATE TABLE Routine_Users (
		id_User VARCHAR(30) NOT NULL ,
		doc_routine VARCHAR(100) NOT NULL,
		UNIQUE (id_User),
		FOREIGN KEY (id_User) REFERENCES Progress_Users (id_User) ON UPDATE CASCADE  ON DELETE CASCADE
	);

	CREATE TABLE Receipt_Users(
		id_User varchar(30) NOT NULL UNIQUE,
		doc_receipt VARCHAR(100) NOT NULL,
		UNIQUE (id_User),
		FOREIGN KEY (id_User) REFERENCES Progress_Users (id_User) ON UPDATE CASCADE  ON DELETE CASCADE
	);

	CREATE TABLE Information_Users(
		id_User varchar(30) NOT NULL UNIQUE,
		nameUser VARCHAR(30) NOT NULL,
		country VARCHAR(30)NOT NULL,
		height double not null,
		age int not null,
		UNIQUE (id_User),
		FOREIGN KEY (id_User) REFERENCES Progress_Users (id_User) ON UPDATE CASCADE ON DELETE CASCADE
	);


	CREAtE TABLE Daily_Diet (
		id_User varchar(30) NOT NULL UNIQUE,
		Calories INT,
		Protein INT,
		Carbohidrates INT,
		FAT INT,
		UNIQUE (id_User),
		FOREIGN KEY (id_User) REFERENCES Progress_Users (id_User) ON UPDATE CASCADE ON DELETE CASCADE
	);









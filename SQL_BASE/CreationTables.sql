-- CREATE DATABASE bd_MyProgress_App;
-- CREATION OF THE TABLES


DROP TABLE IF EXISTS App_Users;
DROP TABLE IF EXISTS Facebook_Users;
DROP TABLE IF EXISTS Google_Users;
DROP TABLE IF EXISTS Daily_Diet;
DROP TABLE IF EXISTS Progress_Users;
DROP TABLE IF EXISTS Routine_Users;
DROP TABLE IF EXISTS Receipt_Users;

CREAtE TABLE App_Users (
	id_User varchar(30) PRIMARY KEY  	,
	Password VARCHAR(100) NOT NULL,
    email_user VARCHAR(100) UNIQUE NOT NULL,
	CHECK (CHAR_LENGTH(id_user) BETWEEN 5 AND 15),
    CHECK (CHAR_LENGTH(email_user)  BETWEEN 10 AND 80)
);

CREAtE TABLE Facebook_Users (
	id_User varchar(30) PRIMARY KEY ,
    email_user VARCHAR(100) UNIQUE NOT NULL,
	CHECK (CHAR_LENGTH(id_User) BETWEEN 5 AND 15),
    CHECK (CHAR_LENGTH(Email_user)  BETWEEN 10 AND 80)
);

CREAtE TABLE Google_Users (
	id_User varchar(30) PRIMARY KEY  	,
    email_user VARCHAR(100) UNIQUE NOT NULL,
	CHECK (CHAR_LENGTH(id_user) BETWEEN 5 AND 15),
    CHECK (CHAR_LENGTH(email_user)  BETWEEN 10 AND 80)
);

CREAtE TABLE Daily_Diet (
	id_User varchar(30),
    currentDate DATE NOT NULL,
	Calories INT,
    Protein INT,
    FAT INT,
	Carbohydrates INT,
    FIBRA INT,
    SUGAR INT
);

CREAtE TABLE Progress_Users (
	id_User varchar(30) PRIMARY KEY,
    StartingWeight int NOT NULL,
	CurrentWeight int NOT NULL,
	EndWeight int NOT NULL,
	CurrentCalories int not null,
	LostWeight int not null,
	gainedWeight int not null
);

CREATE TABLE Routine_Users(
	id_User varchar(30) NOT NULL,
    doc_routine VARCHAR(100) NOT NULL
);

CREATE TABLE Receipt_Users(
	id_User varchar(30) NOT NULL,
    doc_receipt VARCHAR(100) NOT NULL
);









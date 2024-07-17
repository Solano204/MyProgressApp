 PROCEDURE TO COMPRABATE IF USER EXITS
 
DROP PROCEDURE IF EXISTS getAppUserSelected; 
DELIMITER //
CREATE PROCEDURE getAppUserSelected(
    INOUT user VARCHAR(20),
    OUT email VARCHAR (50),
    OUT typeAuthentication VARCHAR(20),
    OUT currentStarting DATE,
    OUT StartingWeight INT,
    OUT CurrentWeight INT,
    OUT EndWeight INT,
    OUT CurrentCalories INT,
    OUT LostWeight INT,
    OUT gainedWeight INT
)
BEGIN
DECLARE in_success BOOLEAN;
  START TRANSACTION;
    SET in_success = TRUE;
    IF user IS NULL OR CHAR_LENGTH(user) < 5 OR CHAR_LENGTH(user) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
    IF in_success then
    SELECT u.id_User as user, u.email_user as email,u.type_Authentication as typeAuthentication,d.currentStarting, d.StartingWeight, d.CurrentWeight, d.EndWeight, d.CurrentCalories, d.LostWeight,d.gainedWeight
    FROM app_users as u join  progress_users as d ON u.id_User = d.id_User where u.id_User = user;
    commit;
     ELSE
     rollback;
     END IF ;
END //
DELIMITER ;

SET @user = 'Johsn Domjd';
CALL getAppUserSelected(@user, @out_email,@typeAu, @out_currentStarting, @out_StartingWeight, @out_CurrentWeight,
 @out_EndWeight, @out_CurrentCalories, @out_LostWeight, @out_gainedWeight);
 
 - GET EMAIL EXITS
 DROP PROCEDURE IF EXISTS user_app_exist_email; 
DELIMITER //
CREATE PROCEDURE user_app_exist_email(
    IN email VARCHAR (50),
	OUT in_success BOOLEAN
)
BEGIN
DECLARE count INT;
  START TRANSACTION;
    SET in_success = TRUE;
    IF email IS NULL THEN
        SET in_success = FALSE;
    END IF;
    
    IF in_success then
    SELECT  COUNT(*) INTO count 
    FROM app_users as u  where u.email_user = email;
    SET in_success = (count > 0);
    commit;
     ELSE
     set in_success= false;
     rollback;
     END IF ;
END //
DELIMITER ;

CALL user_app_exist_email('john.doe@exsample.com', @in_success);
SELECT @in_success;


 - GET USER EXITS
 DROP PROCEDURE IF EXISTS user_app_exist_user ; 
DELIMITER //
CREATE PROCEDURE user_app_exist_user(
    IN user VARCHAR (50),
	OUT in_success BOOLEAN
)
BEGIN
DECLARE count INT;
  START TRANSACTION;
    SET in_success = TRUE;
    IF user IS NULL THEN
        SET in_success = FALSE;
    END IF;
    
    IF in_success then
    SELECT  COUNT(*) INTO count 
    FROM app_users as u  where u.id_User = user;
    SET in_success = (count > 0);
    commit;
     ELSE
     set in_success= false;
     rollback;
     END IF ;
END //
DELIMITER ;

CALL user_app_exist_user('Johsn Domjd', @in_success);
SELECT @in_success;











SELECT @user AS user, @out_email AS email,@TypeAu, @out_currentStarting AS currentStarting, @out_StartingWeight AS StartingWeight,
 @out_CurrentWeight AS CurrentWeight, @out_EndWeight AS EndWeight, @out_CurrentCalories AS CurrentCalories, @out_LostWeight AS LostWeight, @out_gainedWeight AS gainedWeight;


INSERT INTO daily_diet (id_User,currentDate,Calories,Protein,FAT,Carbohydrates,	FIBRA,SUGAR) VALUES ('Johsn Domjd',CURRENT_DATE(),200,212,123,2323,3254,938);
INSERT progress_users (id_User,currentStarting, StartingWeight,CurrentWeight,EndWeight,CurrentCalories,LostWeight,gainedWeight) VALUES ('Johsn Domjd',CURRENT_DATE(),54,78,100,736,323,233);
 SELECT u.id_User as user, u.email_user as email,d.currentDate, d.Calories, d.Protein, d.Fat, d.Carbohydrates, d.Fibra,d.sugar
    FROM app_users as u join  daily_diet as d ON u.id_User = d.id_User;
SELECT CURRENT_DATE();
SET @success = true;-- the procedure
CALL add_user_google ('Johsn o', 'john.doe@exsasa.com', @success);
select @success;
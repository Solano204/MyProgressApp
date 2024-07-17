DROP PROCEDURE IF EXISTS getGoogleUserSelected; 
DELIMITER //
CREATE PROCEDURE getGoogleUserSelected(
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
    FROM google_users as u join  progress_users as d ON u.id_User = d.id_User where u.id_User = user;
    commit;
     ELSE
     rollback;
     END IF ;
END //
DELIMITER ;

SET @user = 'Johsn Domjd';
CALL getGoogleUserSelected(@user, @out_email,@typeAu, @out_currentStarting, @out_StartingWeight, @out_CurrentWeight,
 @out_EndWeight, @out_CurrentCalories, @out_LostWeight, @out_gainedWeight);
 
 - GET EMAIL EXITS
 DROP PROCEDURE IF EXISTS user_google_exist_email; 
DELIMITER //
CREATE PROCEDURE user_google_exist_email(
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
    FROM google_users as u  where u.email_user = email;
    SET in_success = (count > 0);
    commit;
     ELSE
     set in_success= false;
     rollback;
     END IF ;
END //
DELIMITER ;

CALL user_google_exist_email('john.doe@exsample.com', @in_success);
SELECT @in_success;


 - GET USER EXITS
 DROP PROCEDURE IF EXISTS user_google_exist_user ; 
DELIMITER //
CREATE PROCEDURE user_google_exist_user(
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
    FROM google_users as u  where u.id_User = user;
    SET in_success = (count > 0);
    commit;
     ELSE
     set in_success= false;
     rollback;
     END IF ;
END //
DELIMITER ;

CALL user_google_exist_user('Johsn Domjd', @in_success);
SELECT @in_success;

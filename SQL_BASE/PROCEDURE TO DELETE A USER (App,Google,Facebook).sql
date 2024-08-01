SELECT * FROM bd_myprogress_app.app_users;

select * from app_users where id_User = 'AMADEISWEE'
 AND  Password = 'dsffdfdfsfdsssssssssssssssssssdssdsndfjkhdkdhfkjfdhfdkjhsfkjfdhfdkjhfsdjkfhfdkjhfds';
 
 
 
 
DROP PROCEDURE IF EXISTS deleteUserApp; 
DELIMITER //
CREATE PROCEDURE deleteUserApp(
	IN userOld VARCHAR(20),
	IN PasswordS VARCHAR (100),
    OUT in_success BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET in_success = FALSE;
        ROLLBACK;
    END;
	
    START TRANSACTION;
    SET in_success = TRUE;

    -- Validate input 
    
      IF userOld IS NULL OR CHAR_LENGTH(userOld) < 5 OR CHAR_LENGTH(userOld) > 15 THEN
		select in_success;
        SET in_success = FALSE;
    END IF;
    
    IF PasswordS IS NULL OR CHAR_LENGTH(PasswordS) = 0 THEN
        SET in_success = FALSE;
    END IF;
    
        IF in_success THEN
        
        DELETE FROM app_users  WHERE id_user = userOld AND Password = PasswordS ;
		DELETE FROM progress_users WHERE id_user = userOld;
        -- Perform update
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
ENDdeleteUseFacebook


DROP PROCEDURE IF EXISTS deleteUserFacebook; 
DELIMITER //
CREATE PROCEDURE deleteUserFacebook(
	IN userOld VARCHAR(20),
    OUT in_success BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLERlevels_activity FOR SQLEXCEPTION 
    BEGIN
        SET in_success = FALSE;
        ROLLBACK;
    END;
	
    START TRANSACTION;
    SET in_success = TRUE;

    -- Validate input 
    
      IF userOld IS NULL OR CHAR_LENGTH(userOld) < 5 OR CHAR_LENGTH(userOld) > 15 THEN
		select in_success;
        SET in_success = FALSE;
    END IF;

    
        IF in_success THEN
        
        DELETE FROM facebook_users  WHERE id_user = userOld ;
		DELETE FROM progress_users WHERE id_user = userOld;
        -- Perform update
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END


DROP PROCEDURE IF EXISTS deleteUserGoogle; 
DELIMITER //
CREATE PROCEDURE deleteUserGoogle(
	IN userOld VARCHAR(20),
    OUT in_success BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET in_success = FALSE;
        ROLLBACK;
    END;
	
    START TRANSACTION;
    SET in_success = TRUE;

    -- Validate input 
    
      IF userOld IS NULL OR CHAR_LENGTH(userOld) < 5 OR CHAR_LENGTH(userOld) > 15 THEN
		select in_success;
        SET in_success = FALSE;
    END IF;

    
        IF in_success THEN
        
        DELETE FROM google_users  WHERE id_user = userOld ;
		DELETE FROM progress_users WHERE id_user = userOld;
        -- Perform update
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END




 
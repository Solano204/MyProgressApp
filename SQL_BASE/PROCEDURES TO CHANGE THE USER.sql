
DROP PROCEDURE IF EXISTS updateUserApp; 
DELIMITER //
CREATE PROCEDURE updateUserApp(
	IN userOld VARCHAR(20),
    IN newUser VARCHAR(20),
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

    -- Validate input values
    IF newUser IS NULL OR CHAR_LENGTH(newUser) < 5 OR CHAR_LENGTH(newUser) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
      IF userOld IS NULL OR CHAR_LENGTH(userOld) < 5 OR CHAR_LENGTH(userOld) > 15 THEN
		select in_success;
        SET in_success = FALSE;
    END IF;
    
    IF PasswordS IS NULL OR CHAR_LENGTH(PasswordS) = 0 THEN
        SET in_success = FALSE;
    END IF;
    
        IF in_success THEN
        -- Perform updates
        UPDATE app_users
        SET
        id_User = newUser
        WHERE
		id_User = userOld and Password = PasswordS; 
        
        select userOld;
		UPDATE progress_users
        SET
        id_User = newUser
        WHERE
		id_User = userOld;
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END




DROP PROCEDURE IF EXISTS updateUserGoogle; 
DELIMITER //
CREATE PROCEDURE updateUserGoogle(
	IN userOld VARCHAR(20),
    IN newUser VARCHAR(20),
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

    -- Validate input values
    IF newUser IS NULL OR CHAR_LENGTH(newUser) < 5 OR CHAR_LENGTH(newUser) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
      IF userOld IS NULL OR CHAR_LENGTH(userOld) < 5 OR CHAR_LENGTH(userOld) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
    
    IF in_success THEN
        -- Perform updates
        UPDATE google_users
        SET
        id_User = newUser
        WHERE
		id_User = userOld;
        
		UPDATE progress_users
        SET
        id_User = newUser
        WHERE
		id_User = userOld;
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END


DROP PROCEDURE IF EXISTS updateUserFacebook; 
DELIMITER //
CREATE PROCEDURE updateUserFacebook(
	IN userOld VARCHAR(20),
    IN newUser VARCHAR(20),
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

    -- Validate input values
    IF newUser IS NULL OR CHAR_LENGTH(newUser) < 5 OR CHAR_LENGTH(newUser) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
      IF userOld IS NULL OR CHAR_LENGTH(userOld) < 5 OR CHAR_LENGTH(userOld) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
    
    IF in_success THEN
        -- Perform updates
        UPDATE facebook_users
        SET
        id_User = newUser
        WHERE
		id_User = userOld;
        
		UPDATE progress_users
        SET
        id_User = newUser
        WHERE
		id_User = userOld;
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END
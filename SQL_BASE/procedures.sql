
-- CREATION OF PROCEDURE


DROP PROCEDURE IF EXISTS add_user_app; 
DELIMITER //
CREATE PROCEDURE add_user_app(
    IN id_user VARCHAR(20),
    IN password VARCHAR(100),
    IN email_user VARCHAR(100),
    IN typeAuthentication VARCHAR(20),
    OUT in_success BOOLEAN
)
BEGIN

    -- Initialize success flag
    declare  au INT;
    SET in_success = TRUE;
-- Validate id_user
    IF id_user IS NULL OR CHAR_LENGTH(id_user) < 5 OR CHAR_LENGTH(id_user) > 80 THEN
		select * from app_users;
        SET in_success = FALSE;
	
    END IF;

    -- Validate password
    IF password IS NULL OR CHAR_LENGTH(password) < 50 OR CHAR_LENGTH(password) > 100 THEN
        -- Set failure sflag 
        select * from daily_diet;
        SET in_success = FALSE;
    END IF;

    -- Validate email format
    IF email_user NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[cC][oO][mM]$' THEN
 
 select * from google_users ;
        SET in_success = FALSE; 
    END IF;


SELECT   id into au from type_authentication where type_au = typeAuthentication;
    -- InDDDSDSDFGGDSFs	IFF(ert into App_Users table if all validations pass
    IF in_success then
    INSERT INTO App_Users (id_user, PassWord, email_user,id_authentication) 
    VALUES (id_user, password, email_user,au);
end if;
    -- Set success flag to TRUE after successful insertion
    SET in_success = TRUE;
END

// DELIMITER ;


SELECT * FROM app_users;
SET @success = true;-- the procedure
CALL add_user_app ('Johsn Do','DSKLJJJJJJJJJJJIdshjkhddhhjhjdjhldkj' , 'john.doe@exsample.com', @success);
select @success;



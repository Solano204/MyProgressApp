DROP PROCEDURE IF EXISTS updateStateUser; 
DELIMITER //
CREATE PROCEDURE updateStateUser(
    IN User VARCHAR(20),
    IN CurrentWeightS INT,
    IN LostWeightS INT,
    IN GainedWeightS INT,
    IN CurrentCaloriesS INT,
    IN ProteinS INT,
    IN CarbohydratesS INT,
    IN FatS INT,
    IN State_healthS VARCHAR(20),
    OUT in_success BOOLEAN
)
BEGIN
    DECLARE id_statetH INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET in_success = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    SET in_success = TRUE;

    -- Validate input values
    IF User IS NULL OR CHAR_LENGTH(User) < 5 OR CHAR_LENGTH(User) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
    IF State_healthS IS NULL OR CHAR_LENGTH(State_healthS) < 3 OR CHAR_LENGTH(State_healthS) > 20 THEN
        SET in_success = FALSE;
    END IF;
    
    IF CurrentWeightS IS NULL OR LostWeightS IS NULL OR GainedWeightS IS NULL OR 
       CurrentCaloriesS IS NULL OR ProteinS IS NULL OR CarbohydratesS IS NULL OR FatS IS NULL THEN
        SET in_success = FALSE;
    END IF;

select State_healthS;
    IF in_success THEN
        -- Perform updates
        UPDATE Handle_Weight
        SET
            CurrentWeight = CurrentWeightS,
            LostWeight = LostWeightS,
            gainedWeight = GainedWeightS
        WHERE
            id_User = User;
        
        UPDATE Daily_Diet
        SET
            Calories = CurrentCaloriesS,
            Protein = ProteinS,
            Carbohidrates = CarbohydratesS,
            FAT = FatS
        WHERE
            id_User = User;
        
        SELECT id INTO id_statetH
        FROM health_states 
        WHERE state_name = State_healthS;
        
        select id_statetH;
        UPDATE progress_users
        SET id_state_health = id_statetH
        WHERE id_User = User;

        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END //
DELIMITER ;

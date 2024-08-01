

DROP PROCEDURE IF EXISTS updateData; 
DELIMITER //
CREATE PROCEDURE updateData(
    IN User VARCHAR(20),
    IN NameUserS VARCHAR(20),
    IN CountryS VARCHAR(20),
    IN HeightS DOUBLE,
    IN AgeS INT,
    IN CurrentCalories INT,
    IN ProteinS INT,
    IN CarbohydratesS INT,
    IN FatS INT,
    IN Goal VARCHAR(20),
    IN GenderS VARCHAR(10),
    IN State_healthS VARCHAR(20),
    IN level_activity VARCHAR(20),
    OUT in_success BOOLEAN
)
BEGIN 

    DECLARE id_statetH INT;
    DECLARE id_genderS INT;
    DECLARE id_activity INT;
    DECLARE id_goalS INT;
      DECLARE EXIT HANDLER FOR 1062
    BEGIN
        SET in_success = FALSE;
        ROLLBACK;
    END;

    
    SET in_success = TRUE;
    -- Validate 	input values
    IF User IS NULL OR CHAR_LENGTH(User) < 5 OR CHAR_LENGTH(User) > 15 THEN
        SET in_success = FALSE;
    END IF;

    IF NameUserS IS NULL OR CHAR_LENGTH(NameUserS) < 3 OR CHAR_LENGTH(NameUserS) > 20 THEN
        SET in_success = FALSE;
    END IF;

    IF CountryS IS NULL OR CHAR_LENGTH(CountryS) < 3 OR CHAR_LENGTH(CountryS) > 20 THEN
        SET in_success = FALSE;
    END IF;

    IF HeightS IS NULL OR HeightS <= 0 THEN
        SET in_success = FALSE;
    END IF;

    IF AgeS IS NULL OR AgeS <= 0 THEN
        SET in_success = FALSE;
    END IF;


    If
       CurrentCalories IS NULL OR ProteinS IS NULL OR CarbohydratesS IS NULL OR FatS IS NULL THEN
        SET in_success = FALSE;
    END IF;

    IF Goal IS NULL OR CHAR_LENGTH(Goal) < 3 OR CHAR_LENGTH(Goal) > 20 THEN
        SET in_success = FALSE;
    END IF;

    IF GenderS IS NULL OR CHAR_LENGTH(GenderS) < 4 OR CHAR_LENGTH(GenderS) > 10 THEN
        SET in_success = FALSE;
    END IF;


    IF State_healthS IS NULL OR CHAR_LENGTH(State_healthS) < 3 OR CHAR_LENGTH(State_healthS) > 20 THEN
        SET in_success = FALSE;
    END IF;

    IF level_activity IS NULL OR CHAR_LENGTH(level_activity) < 3 OR CHAR_LENGTH(level_activity) > 20 THEN
        SET in_success = FALSE;
    END IF;
    
START TRANSACTION;
    IF in_success THEN
        -- Retrieve IDs for foreign keys
        SELECT id INTO id_statetH
        FROM health_states
        WHERE state_name = State_healthS;

        SELECT id INTO id_goalS
        FROM goals
        WHERE typeGoals = Goal;

         SELECT id  INTO id_genderS
        FROM genders
        WHERE gender = GenderS ;
        
        

        SELECT id INTO id_activity
        FROM levels_activity
        WHERE typeLevel = level_activity;
         
		UPDATE Progress_Users
        SET
        id_level_activity= id_activity,
        id_gender = id_genderS,
		id_goal = id_goalS,
		id_state_health =  id_statetH
        WHERE
		id_User =  User;


		UPDATE daily_diet
        SET
        Calories = CurrentCalories,
        Protein = ProteinS,
		Carbohidrates = CarbohydratesS,
		Fat =  FatS
        WHERE
		id_User =  User;
        
        
		UPDATE Information_Users
        SET
        nameUser = NameUserS,
        country = CountryS,
		Height = HeightS,
		age =  AgeS
        WHERE
		id_User =  User;

        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END
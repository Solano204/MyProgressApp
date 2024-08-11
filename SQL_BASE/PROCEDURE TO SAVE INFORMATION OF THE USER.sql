DROP PROCEDURE IF EXISTS fill_in_information; 
DELIMITER //

CREATE PROCEDURE fill_in_information(
    IN User VARCHAR(20),
    IN NameUserS VARCHAR(20),
    IN CountryS VARCHAR(20),
    IN HeightS DOUBLE,
    IN AgeS INT,
    IN StartingWeightS INT,
    IN CurrentWeightS INT,
    IN EndWeightS INT,
    IN LostWeightS INT,
    IN GainedWeightS INT,
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

SELECT State_healthS;
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

    IF StartingWeightS IS NULL OR CurrentWeightS IS NULL OR EndWeightS IS NULL OR 
       LostWeightS IS NULL OR GainedWeightS IS NULL OR 
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

 INSERT INTO Progress_Users (id_User, DateStarting, id_level_activity, id_gender, id_goal, id_state_health)
        VALUES (User, CURRENT_DATE(), id_activity, id_genderS, id_goalS, id_statetH);

        INSERT INTO  daily_diet (id_User, Calories, Protein, Carbohidrates, Fat)
        VALUES (User, CurrentCalories, ProteinS, CarbohydratesS, FatS);
      
        -- Perform inserts
       

        INSERT INTO Handle_Weight (id_User, StartingWeight, CurrentWeight, EndWeight, LostWeight, gainedWeight)
        VALUES (User, StartingWeightS, CurrentWeightS, EndWeightS, LostWeightS, GainedWeightS);

        INSERT INTO Information_Users (id_User, nameUser, country, height, age)
        VALUES (User, NameUserS, CountryS, HeightS, AgeS);

        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END //

DELIMITER ;
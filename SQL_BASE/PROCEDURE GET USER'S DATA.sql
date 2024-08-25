 PROCEDURE TO COMPRABATE IF USER ;
 
DROP PROCEDURE IF EXISTS getAppUserSelected; 
DELIMITER //
CREATE PROCEDURE getAppUserSelected(
    INOUT UserS VARCHAR(20),
    OUT passwordS VARCHAR(100),
    OUT EmailS VARCHAR (50),
    OUT TypeAuthenticationS VARCHAR(20),
    OUT NameUserS VARCHAR(20),
    OUT CountryS VARCHAR(20),
    OUT heightS DOUBLE ,
    OUT AgeS INT,
    OUT DateStartingS DATE,
    OUT StartingWeightS INT,
    OUT CurrentWeightS INT,
    OUT EndWeightS INT,
    OUT LostWeightS INT,
    OUT GainedWeightS INT,
    OUT CurrentCaloriesS INT,
    OUT ProteinS INT,
    OUT CarbohydratesS INT,
    OUT FatS INT,
    OUT GoalS VARCHAR(20),
    OUT GenderS VARCHAR(10),
    OUT State_healthS VARCHAR(20),
    OUT TypeLevelS VARCHAR(20)
)
BEGIN
DECLARE in_success BOOLEAN;
  START TRANSACTION;
    SET in_success = TRUE;
    IF UserS IS NULL OR CHAR_LENGTH(UserS) < 5 OR CHAR_LENGTH(UserS) > 15 THEN
        SET in_success = FALSE;
    END IF;
    
   
    IF in_success then
    SELECT u.id_User, u.Password, u.email_user, au.type_au,inf.nameUser,inf.country,inf.height,inf.age,p.DateStarting,hw.StartingWeight,hw.CurrentWeight,hw.EndWeight,hw.LostWeight,hw.gainedWeight,
		di.Calories,di.Protein,di.Carbohidrates,di.FAT,go.typeGoals,ge.gender, hs.state_name,lv.typeLevel
        INTO UserS,passwordS,EmailS,TypeAuthenticationS,NameUserS,CountryS,HeightS,AgeS,DateStartingS,StartingWeightS,CurrentWeightS,EndWeightS,
        LostWeightS,GainedWeightS,CurrentCaloriesS,ProteinS,CarbohydratesS,FatS,GoalS,GenderS,State_healthS,TypeLevelS
        FROM app_users AS u 
        JOIN progress_users AS p ON u.id_User = p.id_User 
        JOIN daily_diet as di ON p.id_User = di.id_User
        JOIN handle_weight as hw ON p.id_User = hw.id_User
        JOIN type_authentication as au ON u.id_authentication  = au.id
		JOIN information_users as inf ON inf.id_User  = p.id_User
        JOIN genders as ge ON p.id_gender  = ge.id
        JOIN goals as go ON p.id_goal = go.id
        JOIN health_states hs ON p.id_state_health = hs.id
        JOIN levels_activity lv ON p.id_level_activity = lv.id
        WHERE u.id_User = UserS;
    commit;
     ELSE
     rollback;
     END IF ;
END //
DELIMITER ;

SET @User =  'user1';
SET  @pAsswordS =  '';
SET @EmailS = '';
SET @TypeAuthenticationS = '';
SET @NameUserS = '';
SET @CountryS = '';
SET @HeightS = 0;
SET @AgeS = 0;
SET @DateStartingS = NULL;
SET @StartingWeightS = 0;
SET @CurrentWeightS = 0;
SET @EndWeightS = 0;
SET @LostWeightS = 0;
SET @GainedWeightS = 0;
SET @CurrentCaloriesS = 0;
SET @ProteinS = 0;
SET @CarbohydratesS = 0;
SET @FatS = 0;
SET @GoalS = '';
SET @GenderS = '';
SET @State_healthS = '';
SET @TypeLevelS = '';

-- Call the stored procedure
CALL getAppUserSelected(@User,@pAsswordS,@EmailS, @TypeAuthenticationS, @NameUserS, @CountryS, @HeightS, @AgeS, @DateStartingS, @StartingWeightS, @CurrentWeightS, @EndWeightS, @LostWeightS, @GainedWeightS, @CurrentCaloriesS, @ProteinS, @CarbohydratesS, @FatS, @GoalS, @GenderS, @State_healthS, @TypeLevelS);

-- Select the output parameters to see the results
SELECT 
    @User AS User,
    @pAsswordS as  pas,
    @EmailS AS Email,
    @TypeAuthenticationS AS TypeAuthentication,
    @NameUserS AS NameUser,
    @CountryS AS Country,
    @HeightS AS Height,
    @AgeS AS Age,
    @DateStartingS AS DateStarting,
    @StartingWeightS AS StartingWeight,
    @CurrentWeightS AS CurrentWeight,
     @EndWeightS AS EndWeight,
    @LostWeightS AS LostWeight,
    @GainedWeightS AS GainedWeight,
    @CurrentCaloriesS AS CurrentCalories,
    @ProteinS AS Protein,
    @CarbohydratesS AS Carbohydrates,
    @FatS AS Fat,
    @GoalS AS Goal,
    @GenderS AS Gender,
    @State_healthS AS State_health,
    @TypeLevelS AS TypeLevel;
 
 -- GET EMAIL EXITS
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
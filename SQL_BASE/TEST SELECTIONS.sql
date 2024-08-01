

-- https://chatgpt.com/share/cc032ea5-3d16-4800-af22-14b8610dd987
SET @User = 'fb_user1';  -- or any other valid user id
CALL getFaceUserSelected(@User, @Email, @TypeAuthentication, @NameUser, @Country, @Height, @Age, @DateStarting, 
                         @StartingWeight, @CurrentWeight, @EndWeight, @LostWeight, @GainedWeight, @CurrentCalories, 
                         @Protein, @Carbohydrates, @Fat, @Goal, @Gender, @State_health, @TypeLevel);
                        
                        SET @User = 'user1';  -- or any other valid user id
CALL getAppUserSelected(@User, @Email, @TypeAuthentication, @NameUser, @Country, @Height, @Age, @DateStarting, 
                         @StartingWeight, @CurrentWeight, @EndWeight, @LostWeight, @GainedWeight, @CurrentCalories, 
                         @Protein, @Carbohydrates, @Fat, @Goal, @Gender, @State_health, @TypeLevel);
                         
                                    SET @User = 'google_user1';  -- or any other valid user id
CALL getAppUserSelected(@User, @Email, @TypeAuthentication, @NameUser, @Country, @Height, @Age, @DateStarting, 
                         @StartingWeight, @CurrentWeight, @EndWeight, @LostWeight, @GainedWeight, @CurrentCalories, 
                         @Protein, @Carbohydrates, @Fat, @Goal, @Gender, @State_health, @TypeLevel);

SELECT @User AS User, @Email AS Email, @TypeAuthentication AS TypeAuthentication, @NameUser AS NameUser, 
       @Country AS Country, @Height AS Height, @Age AS Age, @DateStarting AS DateStarting, 
       @StartingWeight AS StartingWeight, @CurrentWeight AS CurrentWeight, @EndWeight AS EndWeight, 
       @LostWeight AS LostWeight, @GainedWeight AS GainedWeight, @CurrentCalories AS CurrentCalories, 
       @Protein AS Protein, @Carbohydrates AS Carbohydrates, @Fat AS Fat, @Goal AS Goal, 
       @Gender AS Gender, @State_health AS State_health, @TypeLevel AS TypeLevel;
       
       CALL getAppUserSelected(@User, @Email, @TypeAuthentication, @NameUser, @Country, @Height, @Age, @DateStarting, 
                         @StartingWeight, @CurrentWeight, @EndWeight, @LostWeight, @GainedWeight, @CurrentCalories, 
                         @Protein, @Carbohydrates, @Fat, @Goal, @Gender, @State_health, @TypeLevel);
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
SELECT u.id_User, u.email_user, au.type_au, inf.nameUser, inf.country, inf.height, inf.age, p.DateStarting, hw.CurrentWeight, hw.EndWeight, hw.LostWeight, hw.gainedWeight,
di.Calories, di.Protein, di.Carbohidrates, di.FAT, go.typeGoals, ge.gender, hs.state_name, lv.typeLevel
FROM facebook_users AS u 
JOIN progress_users AS p ON u.id_User = p.id_User 
JOIN daily_diet AS di ON p.id_User = di.id_User
JOIN handle_weight AS hw ON p.id_User = hw.id_User
JOIN type_authentication AS au ON u.id_authentication = au.id
JOIN information_users AS inf ON inf.id_User = p.id_User
JOIN genders AS ge ON p.id_gender = ge.id
JOIN goals AS go ON p.id_goal = go.id
JOIN health_states AS hs ON p.id_state_health = hs.id
JOIN levels_activity AS lv ON p.id_level_activity = lv.id
WHERE u.id_User = 'fb_user1';

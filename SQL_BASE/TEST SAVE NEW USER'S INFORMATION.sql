CALL fill_in_information(
    'IceColl',          -- User
    'Raper',         -- NameUserS
    'USA unidtyed',              -- CountryS
    175.5,              -- HeightS
    30,                 -- AgeS
    70,                 -- StartingWeightS
    75,                 -- CurrentWeightS
    80,                 -- EndWeightS
    5,                  -- LostWeightS
    10,                 -- GainedWeightS
    2000,               -- CurrentCalories
    150,                -- Protein
    250,                -- Carbohydrates
    70,                 -- Fat
    'Perder Peso',      -- Goal
    'Masculino',             -- Gender
    'Pasado de peso',          -- State_healthS
    'Moderadamente Activo',           -- level_activity
    @in_success         -- OUT parameter
);

SELECT @in_success;


SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM app_users where  u.Password = '';



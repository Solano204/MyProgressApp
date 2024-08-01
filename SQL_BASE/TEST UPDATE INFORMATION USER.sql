-- Prepare to call the procedure
SET @User = 'Savage';
SET @CurrentWeightS = 70;
SET @LostWeightS = 20;
SET @GainedWeightS = 12;
SET @CurrentCaloriesS = 2000;
SET @ProteinS = 1150;
SET @CarbohydratesS = 1250;
SET @FatS = 170;
SET @State_healthS = 'Peso Normal';
SET @in_success = FALSE;

-- Call the procedure
CALL updateStateUser(
    @User,
    @CurrentWeightS,
    @LostWeightS,
    @GainedWeightS,
    @CurrentCaloriesS,
    @ProteinS,
    @CarbohydratesS,
    @FatS,
    @State_healthS,
    @in_success
);


-- Check the result
SELECT @in_success;
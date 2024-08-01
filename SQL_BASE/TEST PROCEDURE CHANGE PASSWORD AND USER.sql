




-- Declare a variable to hold the OUT parameter
SET @in_success = FALSE;

-- Call the stored procedure
CALL updateUserApp('JosueUser1','newUser1', '$2b$12$LG.JOcfPsaLjATyJtXIK0eS7HTlMLg5z5HoJiIFEszYgP1CbiHuQ', @in_success);

-- Retrieve the value of the OUT parameter
SELECT @in_success;


UPDATE app_users
        SET
        Password = '$2b$12$LG.JOcfPsaLjATyJtXIK0eS7HTlMLg5z5HoJiIFEszYgP1CbiHuQ'
        WHERE
		id_User = 'newUser1' and Password = '$2b$12$8l9GENjUnKByb8CbG7OOS.0RTOIcbMyY6xPpnHEQjNJ7i.JU3W81a' ; 
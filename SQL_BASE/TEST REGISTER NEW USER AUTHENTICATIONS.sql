
SET @in_success = FALSE;

-- Call the stored procedure with input parameters and the output parameter
CALL add_user_google('john_doe', 'johndoe@example.com', 'Google', @in_success);

-- Check the value of the output parameter
SELECT @in_success;




-- Fix NULL values in Profile_Completed column
-- Run this in MySQL Workbench or your MySQL client

USE emart;

-- Update all NULL values to 0 (false)
UPDATE Customer 
SET Profile_Completed = 0 
WHERE Profile_Completed IS NULL;

-- Verify the update
SELECT User_id, Email, Profile_Completed, Auth_Provider 
FROM Customer;

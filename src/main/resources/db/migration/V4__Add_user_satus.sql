ALTER TABLE user
ADD is_active bit default true NOT NULL;

UPDATE user
SET user.is_active = true
WHERE user.is_active IS NULL;

INSERT INTO user_details(
    account_non_expired, account_non_locked, credentials_non_expired, enabled, id, email, last_name, name, user_password)
VALUES (true, true, true, true, 1, 'admin@mail.com', 'admin', 'admin', '$2a$10$ISxAkFV4mXKGlQLgtD8BzO5CkLaBWna4ly3T78qVftqmYJXBIg56m');
-- INSERT INTO user_roles(user_id, roles) values
INSERT user (id, email, password, user_firstname, user_lastname, username)
VALUES ('1', 'negumadness@gmail.com', '123456','Ivan', 'Shkubel', 'admin');

INSERT user_roles (user_id, roles_id) VALUES (1,1);

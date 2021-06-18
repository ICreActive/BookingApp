INSERT user (id, email, password, user_firstname, user_lastname, username)
VALUES ('1', 'negumadness@gmail.com', '$2a$10$4kXYW4Si1MD8KDamfiEV4eUyUEMH0esWBKilcxUmNg5b4wBhgBiNK','Ivan', 'Shkubel', 'admin');

INSERT user_roles (user_id, roles_id) VALUES (1,1);

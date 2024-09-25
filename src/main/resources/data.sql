INSERT INTO role (id, name, description) VALUES (1, 'ADMIN', 'Admin role');

INSERT INTO permission (id, name, resource, role_id) VALUES (1, 'READ-USER', 'USER', 1);
INSERT INTO permission (id, name, resource, role_id) VALUES (2, 'WRITE-USER', 'USER', 1);
INSERT INTO permission (id, name, resource, role_id) VALUES (3, 'DELETE-USER', 'USER', 1);
INSERT INTO permission (id, name, resource, role_id) VALUES (4, 'READ-ROLE', 'ROLE', 1);
INSERT INTO permission (id, name, resource, role_id) VALUES (5, 'WRITE-ROLE', 'ROLE', 1);
INSERT INTO permission (id, name, resource, role_id) VALUES (6, 'DELETE-ROLE', 'ROLE', 1);
INSERT INTO permission (id, name, resource, role_id) VALUES (7, 'READ-PERMISSION', 'PERMISSION', 1);

INSERT INTO role ( id, name, description) VALUES (2, 'USER', 'User role');

INSERT INTO permission (id, name, resource, role_id) VALUES (8, 'USER-INFO', 'USER', 2);
INSERT INTO permission (id, name, resource, role_id) VALUES (9, 'UPDATE-USER', 'USER', 2);

INSERT INTO "user" (id, username, password, email, name, last_name) VALUES (1, 'admin', 'password', 'admin@mail.com', 'Admin', 'Admin');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO "user" (id, username, password, email, name, last_name) VALUES (2, 'user', 'password', 'user@mail.com', 'user', 'user');

INSERT INTO user_role (user_id, role_id) VALUES (2, 2);


INSERT INTO permissions (id, name, description, date_Created, last_Updated) VALUES
(1, 'MANAGE_CATEGORIES', '', now(), now()),
(2, 'MANAGE_DISCS', '', now(), now()),
(3, 'MANAGE_ORDERS', '', now(), now()),
(4, 'MANAGE_GENRES', '', now(), now()),
(5, 'MANAGE_USERS', '', now(), now()),
(6, 'MANAGE_ROLES', '', now(), now()),
(7, 'MANAGE_PERMISSIONS', '', now(), now());

INSERT INTO roles (id, name, date_Created, last_Updated) VALUES
(1, 'ROLE_SUPER_ADMIN', now(), now()),
(2, 'ROLE_ADMIN', now(), now()),
(3, 'ROLE_USER', now(), now());

INSERT INTO users (id, email, password, user_Name, first_Name, last_Name, phone, address, date_Created, last_Updated) VALUES
(1, 'superadmin@gmail.com', '$2a$06$ucnRvG91hj54lMYDhFS.Y.fay.Raee.aJMVkEsV4cLsRnwITYgI/S', 'Super Admin', 'Derek', 'Jones', '+421 955 489 777', 'Jehodova 6, 563 34 Brno', now(), now());

insert into role_permission(role_id, permission_id) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),
(2,1),(2,2),(2,3),(2,4);

insert into user_role(user_id, role_id) values
(1,1);

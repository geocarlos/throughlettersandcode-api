CREATE TABLE user_entity (
    id INTEGER PRIMARY KEY,
    username VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(80),
	user_password VARCHAR(80)
);

CREATE TABLE permission (
	id INTEGER PRIMARY KEY,
	description VARCHAR(50) NOT NULL
);

CREATE TABLE user_permission (
	user_id INTEGER NOT NULL,
	permission_id INTEGER NOT NULL,
	PRIMARY KEY (user_id, permission_id),
	FOREIGN KEY (user_id) REFERENCES user_entity(id),
	FOREIGN KEY (permission_id) REFERENCES permission(id)
);

INSERT INTO user_entity (id, username, first_name, last_name, email, user_password) values (1, 'admin', 'Geocarlos', 'Alves',  'geowildcat@gmail.com', '$2a$10$JrOPQJ.BObEvbrlTHqTLLO/nayX7wi9gxK0CIyNygfjsxpZrmo8ly');

INSERT INTO permission (id, description) values (1, 'ROLE_CREATE_CATEGORY');
INSERT INTO permission (id, description) values (2, 'ROLE_READ_CATEGORY');

INSERT INTO permission (id, description) values (3, 'ROLE_CREATE_USER');
INSERT INTO permission (id, description) values (4, 'ROLE_READ_USER');

INSERT INTO permission (id, description) values (5, 'ROLE_CREATE_ARTICLE');
INSERT INTO permission (id, description) values (6, 'ROLE_READ_ARTICLE');
INSERT INTO permission (id, description) values (7, 'ROLE_DELETE_ARTICLE');

INSERT INTO permission (id, description) values (8, 'ROLE_CREATE_VIDEO');
INSERT INTO permission (id, description) values (9, 'ROLE_READ_VIDEO');
INSERT INTO permission (id, description) values (10, 'ROLE_DELETE_VIDEO');

-- admin
INSERT INTO user_permission (user_id, permission_id) values (1, 1);
INSERT INTO user_permission (user_id, permission_id) values (1, 2);
INSERT INTO user_permission (user_id, permission_id) values (1, 3);
INSERT INTO user_permission (user_id, permission_id) values (1, 4);
INSERT INTO user_permission (user_id, permission_id) values (1, 5);
INSERT INTO user_permission (user_id, permission_id) values (1, 6);
INSERT INTO user_permission (user_id, permission_id) values (1, 7);
INSERT INTO user_permission (user_id, permission_id) values (1, 8);
INSERT INTO user_permission (user_id, permission_id) values (1, 9);
INSERT INTO user_permission (user_id, permission_id) values (1, 10);

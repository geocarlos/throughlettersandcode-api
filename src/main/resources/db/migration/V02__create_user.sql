CREATE TABLE user_entity (
    id serial PRIMARY KEY,
    username VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(80)
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

INSERT INTO permission (id, description) values (1, 'ROLE_CREATE_CATEGORTY');
INSERT INTO permission (id, description) values (2, 'ROLE_CREATE_USER');
INSERT INTO permission (id, description) values (3, 'ROLE_CREATE_ARTICLE');
INSERT INTO permission (id, description) values (4, 'ROLE_DELETE_ARTICLE');
INSERT INTO permission (id, description) values (5, 'ROLE_CREATE_VIDEO');
INSERT INTO permission (id, description) values (6, 'ROLE_DELETE_VIDEO');


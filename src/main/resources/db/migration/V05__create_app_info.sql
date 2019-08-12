CREATE TABLE app_info (
    id serial PRIMARY KEY,
    title VARCHAR(80),
    author_id INTEGER REFERENCES user_entity(id),
    created_date TIMESTAMP NOT NULL,
    youtube_id VARCHAR(50),
    app_description TEXT
);
CREATE TABLE video (
    id serial PRIMARY KEY,
    title VARCHAR(80),
    author_id INTEGER REFERENCES user_entity(id),
    created_date TIMESTAMP NOT NULL,
    youtube_id VARCHAR(50),
    category_id INTEGER REFERENCES category(id),
    language VARCHAR(10) 
);

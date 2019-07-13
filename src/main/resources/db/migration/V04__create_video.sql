CREATE TABLE video (
    id serial PRIMARY KEY,
    title VARCHAR(80),
    author_id INTEGER REFERENCES userEntity(id),
    created_date TIMESTAMP NOT NULL,
    youtubeId VARCHAR(50),
    category_id INTEGER REFERENCES category(id) 
);

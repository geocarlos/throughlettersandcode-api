CREATE TABLE IF NOT EXISTS video (
    id serial PRIMARY KEY,
    title VARCHAR(80),
    author_id INTEGER REFERENCES user_entity(id),
    created_date TIMESTAMP NOT NULL,
    youtube_id VARCHAR(50),
    video_description TEXT,
    category_id INTEGER REFERENCES category(id),
    language VARCHAR(10) 
);

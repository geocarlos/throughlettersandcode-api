CREATE TABLE article (
    id serial PRIMARY KEY,
    title VARCHAR(80),
    author_id INTEGER REFERENCES user_entity(id),
    created_date TIMESTAMP NOT NULL,
    content TEXT,
    category_id INTEGER REFERENCES category(id)    
);

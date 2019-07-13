CREATE TABLE article (
    id serial PRIMARY KEY,
    author_id INTEGER REFERENCES userEntity(id),
    created_date TIMESTAMP NOT NULL,
    content TEXT,
    category_id INTEGER REFERENCES category(id)    
);

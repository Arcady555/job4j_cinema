CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    movie_id INT NOT NULL REFERENCES movies(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id)
);
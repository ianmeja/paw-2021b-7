CREATE TABLE IF NOT EXISTS images(
     image_id SERIAL NOT NULL PRIMARY KEY,
     rest_id BIGINT NOT NULL,
     data BYTEA,
     FOREIGN KEY (rest_id) REFERENCES restaurant
);

CREATE TABLE IF NOT EXISTS restaurant (
    restId SERIAL NOT NULL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    full_name TEXT NOT NULL,
    address VARCHAR(100) NOT NULL,
    price INTEGER,
    rating INTEGER,
    cuisine VARCHAR(50),
    neighborhood VARCHAR(50),
    message VARCHAR(100),
    reservation BOOLEAN,
    capacity INTEGER,
    about TEXT,
    cant_reviews INTEGER,
);

CREATE TABLE IF NOT EXISTS users(
    userId SERIAL NOT NULL PRIMARY KEY ,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS tokens(
    tokenId SERIAL NOT NULL PRIMARY KEY,
    userId BIGINT NOT NULL,
    createdAt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed BOOLEAN NOT NULL,
    token TEXT NOT NULL,
    FOREIGN KEY (userId) REFERENCES users
);

CREATE TABLE IF NOT EXISTS reviews(
    id SERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    rest_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    rating INTEGER,
    date_review TEXT,
    answer TEXT,
    FOREIGN KEY (rest_id) REFERENCES restaurant,
    FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE IF NOT EXISTS favorites(
    id SERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    rest_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users,
    FOREIGN KEY (rest_id) REFERENCES restaurant
    );

CREATE TABLE IF NOT EXISTS menu(
    dish_id SERIAL NOT NULL,
    rest_id BIGINT NOT NULL,
    dish VARCHAR(50) NOT NULL,
    description TEXT,
    price INTEGER NOT NULL,
    category varchar(20) NOT NULL,
    CONSTRAINT chk_category CHECK (category IN ('STARTER', 'MAIN', 'DESSERT')),
    PRIMARY KEY (dish_id),
    FOREIGN KEY (rest_id) REFERENCES restaurant
);

CREATE TABLE IF NOT EXISTS bookings(
    booking_id SERIAL NOT NULL,
    rest_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    time VARCHAR(10) NOT NULL,
    diners INTEGER NOT NULL,
    confirmed BOOLEAN NOT NULL,
    PRIMARY KEY (booking_id),
    FOREIGN KEY (rest_id) REFERENCES restaurant,
    FOREIGN KEY (user_id) REFERENCES users
    );

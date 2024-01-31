CREATE TABLE IF NOT EXISTS restaurant (
    restId IDENTITY NOT NULL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    full_name VARCHAR(10000000) NOT NULL,
    address VARCHAR(100) NOT NULL,
    price INTEGER,
    rating INTEGER,
    cuisine VARCHAR(50),
    neighborhood VARCHAR(50),
    message VARCHAR(100),
    reservation BOOLEAN,
    capacity INTEGER,
    about VARCHAR(10000000),
    cant_reviews INTEGER,
    image BYTEA
);

CREATE TABLE IF NOT EXISTS users(
    userId IDENTITY NOT NULL PRIMARY KEY ,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled boolean NOT NULL
);

create sequence if not exists users_userid_seq start with 1;

INSERT INTO users(userId, username, email, password, enabled) values (2, 'fulano','fulano@gmail.com', 'password', true);
INSERT INTO users(userId, username, email, password, enabled) values (3, 'jose','jose@gmail.com', 'jose123', true);
INSERT INTO users(userId, username, email, password, enabled) values (4, 'julian','julian@gmail.com', '4uli1233', true);

CREATE TABLE IF NOT EXISTS tokens(
    tokenId IDENTITY NOT NULL PRIMARY KEY,
    userId BIGINT NOT NULL,
    createdAt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed BOOLEAN NOT NULL,
    token VARCHAR(10000000) NOT NULL,
    FOREIGN KEY (userId) REFERENCES users
);

CREATE TABLE IF NOT EXISTS reviews(
    id IDENTITY NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    rest_id BIGINT NOT NULL,
    text VARCHAR(10000000) NOT NULL,
    rating INTEGER,
    date_review VARCHAR(10000000),
    answer VARCHAR(10000000),
    FOREIGN KEY (rest_id) REFERENCES restaurant,
    FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE IF NOT EXISTS favorites(
    id IDENTITY NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    rest_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users,
    FOREIGN KEY (rest_id) REFERENCES restaurant
);

CREATE TABLE IF NOT EXISTS menu(
    dish_id IDENTITY NOT NULL,
    rest_id BIGINT NOT NULL,
    dish VARCHAR(50) NOT NULL,
    description VARCHAR(10000000),
    price INTEGER NOT NULL,
    category varchar(20) NOT NULL,
    CONSTRAINT chk_category CHECK (category IN ('STARTER', 'MAIN', 'DESSERT')),
    PRIMARY KEY (dish_id),
    FOREIGN KEY (rest_id) REFERENCES restaurant
);

CREATE TABLE IF NOT EXISTS bookings(
    booking_id IDENTITY NOT NULL,
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

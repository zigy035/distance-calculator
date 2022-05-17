CREATE TABLE auth_user (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    create_date TIMESTAMP(3) NOT NULL
);

CREATE TABLE postcode_coordinate (
    id BIGINT NOT NULL PRIMARY KEY,
    postcode VARCHAR(8) NOT NULL UNIQUE,
    latitude NUMERIC(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);

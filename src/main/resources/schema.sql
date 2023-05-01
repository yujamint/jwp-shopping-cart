CREATE TABLE PRODUCTS
(
    id    INT     NOT NULL AUTO_INCREMENT,
    name  VARCHAR NOT NULL,
    price INT     NOT NULL,
    image VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE USERS
(
    id          INT     NOT NULL AUTO_INCREMENT,
    email       VARCHAR NOT NULL UNIQUE,
    password    VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

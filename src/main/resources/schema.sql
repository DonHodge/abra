DROP TABLE IF EXISTS author_customer;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;

CREATE TABLE author (
	author_id int NOT NULL AUTO_INCREMENT,
	author_first_name varchar(128) NOT NULL,
	author_middle_name varchar(128) NOT NULL,
	author_last_name varchar(128) NOT NULL,
	PRIMARY KEY (author_id)
);

CREATE TABLE book (
	book_id int NOT NULL AUTO_INCREMENT,
	author_id int NOT NULL,
	title varchar(256),
	PRIMARY KEY (book_id),
	FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);

CREATE TABLE customer (
	customer_id int NOT NULL AUTO_INCREMENT,
	customer_first_name varchar(128) NOT NULL,
	customer_last_name varchar(128) NOT NULL,
	customer_email varchar(128) NOT NULL,
	PRIMARY KEY (customer_id)
);

CREATE TABLE author_customer (
	author_id int NOT NULL,
	customer_id int NOT NULL,
	FOREIGN KEY (author_id) REFERENCES author (author_id),
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);
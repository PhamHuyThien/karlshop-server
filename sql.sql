CREATE TABLE accounts(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	password VARCHAR(200) NOT NULL,
	firstname VARCHAR(30),
	lastname VARCHAR(30),
	email VARCHAR(50),
	role INT DEFAULT 0,
	status BIT DEFAULT 1, 
	time DATETIME
);


CREATE TABLE categories(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30),
	status BIT DEFAULT 1,
	time DATETIME
);

CREATE TABLE products(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_category INT NOT NULL,
	name VARCHAR(30) NOT NULL UNIQUE,
	sex VARCHAR(10),
	price INT DEFAULT 0,
	img VARCHAR(100),
	title VARCHAR(100),
	des VARCHAR(1000),
	status BIT DEFAULT 1, 
	time DATETIME,
	CONSTRAINT FOREIGN KEY (id_category) REFERENCES categories(id)
);

CREATE TABLE discounts(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	code VARCHAR(50) NOT NULL UNIQUE,
	usages INT DEFAULT 1,
	pattern VARCHAR(200) DEFAULT '*',
	cond VARCHAR(200) DEFAULT '',
	discount VARCHAR(50) DEFAULT '0',
	time_expired DATETIME,
	status BIT DEFAULT 1,
	time DATETIME
);



CREATE TABLE orders(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_account INT NOT NULL,
	id_discount INT NOT NULL,
	des VARCHAR(100),
	status BIT DEFAULT 1,
	time DATETIME,
	CONSTRAINT FOREIGN KEY (id_account) REFERENCES accounts(id),
	CONSTRAINT FOREIGN KEY (id_discount) REFERENCES discounts(id)
);

CREATE TABLE order_details(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_order INT NOT NULL,
	id_product INT NOT NULL,
	quantity INT DEFAULT 1,
	status BIT DEFAULT 1,
	time DATETIME,
	CONSTRAINT FOREIGN KEY (id_order) REFERENCES orders(id),
	CONSTRAINT FOREIGN KEY (id_product) REFERENCES products(id)	
);

INSERT INTO discounts VALUES(2, '', -1, '*', '', '0', 0, 1, 0);




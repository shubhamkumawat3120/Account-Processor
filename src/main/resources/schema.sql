DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS country;

CREATE TABLE country (
  country_id bigint AUTO_INCREMENT,
  country_code VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (country_Id),
  UNIQUE KEY country_code (country_code),
  UNIQUE KEY name (name)
);



CREATE TABLE account (
  account_id bigint AUTO_INCREMENT,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email_address VARCHAR(250) NOT NULL,
  PRIMARY KEY (account_id),
  UNIQUE KEY email_address (email_address)
);



CREATE TABLE address (
  address_id bigint AUTO_INCREMENT,
  street VARCHAR(500) NOT NULL,
  city VARCHAR(250) NOT NULL,
  province_state VARCHAR(10) DEFAULT NULL,
  country_id bigint NOT NULL,
  account_id bigint NOT NULL,
  PRIMARY KEY (address_id),
 FOREIGN KEY (country_id) REFERENCES country (country_id),
 FOREIGN KEY (account_id) REFERENCES account (account_id)
);





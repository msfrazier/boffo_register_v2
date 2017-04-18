-- Authors: Walt Brady, Hadi

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=TRADITIONAL;

-- If we decide to enable/disable instead of delete:
-- enabled TINYINT(1) NULL,

-- -----------------------------------------------------
-- Schema boffo_register_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS boffo_register_schema DEFAULT CHARACTER SET utf8 ;
USE boffo_register_schema ;

-- -----------------------------------------------------
-- Table boffo_register_schema.inventory_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.inventory_tbl (
  product_id INT UNSIGNED NOT NULL,
  sku INT NULL,
  upc INT NULL,
  `name` VARCHAR(45) NULL,
  price DOUBLE NULL,
  quantity INT NULL,
  description VARCHAR(200) NULL,
  rating VARCHAR(45) NULL,
  PRIMARY KEY (product_id),
  UNIQUE INDEX product_id_UNIQUE (product_id ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table boffo_register_schema.user_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.user_tbl (
  user_id INT UNSIGNED NOT NULL,
  user_name VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  first_name VARCHAR(45) NULL,
  last_name VARCHAR(45) NULL,
  PRIMARY KEY (user_id),
  UNIQUE INDEX user_id_UNIQUE (user_id ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table boffo_register_schema.bundle_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.bundle_tbl (
  bundle_id INT UNSIGNED NOT NULL,
  start_date DATETIME NULL,
  end_date DATETIME NULL,
  discount_type VARCHAR(45) NULL,
  discount_amount DOUBLE NULL,
  PRIMARY KEY (bundle_id),
  UNIQUE INDEX bundle_id_UNIQUE (bundle_id ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table boffo_register_schema.transaction_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.transaction_tbl (
  transaction_id INT UNSIGNED NOT NULL,
  product_id INT NULL,
	FOREIGN KEY fk_product_id(product_id)
    REFERENCES inventory_tbl(product_id),
  quantity INT NULL,
  ticket_id INT NULL,
	FOREIGN KEY fk_ticket_id(ticket_id)
    REFERENCES ticket_tbl(ticket_id),
  bundle_id INT NULL,
	FOREIGN KEY fk_bundle_id(bundle_id)
    REFERENCES bundle_tbl(bundle_id),
  price DOUBLE NULL,
  PRIMARY KEY (transaction_id),
  UNIQUE INDEX transaction_id_UNIQUE (transaction_id ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table boffo_register_schema.store_info_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.store_info_tbl (
  store_id INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NULL,
  phone_num VARCHAR(45) NULL,
  hours VARCHAR(45) NULL,
  tax_rate DOUBLE NULL,
  receipt_msg VARCHAR(200) NULL,
  PRIMARY KEY (store_id),
  UNIQUE INDEX store_id_UNIQUE (store_id ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table boffo_register_schema.bundle_items_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.bundle_items_tbl (
  bundle_items_id INT UNSIGNED NOT NULL,
  bundle_id INT NULL,
	FOREIGN KEY fk_bundle_id(bundle_id)
    REFERENCES bundle_tbl(bundle_id),
  product_id INT NULL,
	FOREIGN KEY fk_product_id(product_id)
    REFERENCES inventory_tbl(product_id),
  PRIMARY KEY (bundle_items_id),
  UNIQUE INDEX bundle_items_id_UNIQUE (bundle_items_id ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table boffo_register_schema.ticket_tbl
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS boffo_register_schema.ticket_tbl (
  ticket_id INT UNSIGNED NOT NULL,
  `date` DATETIME NULL,
  sale_type VARCHAR(45) NULL,
  user_id INT NULL,
	FOREIGN KEY fk_user_id(user_id)
    REFERENCES user_tbl(user_id),
  PRIMARY KEY (ticket_id),
  UNIQUE INDEX ticket_id_UNIQUE (ticket_id ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

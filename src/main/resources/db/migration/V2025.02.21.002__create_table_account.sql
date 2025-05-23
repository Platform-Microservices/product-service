CREATE TABLE product (
    id_product VARCHAR(36) NOT NULL,
    tx_name VARCHAR(256) NOT NULL,
    tx_unit VARCHAR(256) NOT NULL,
    double_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id_product)
);

ALTER TABLE invoice
    ADD creating_date varchar(255),
    ADD updating_date varchar(255);

ALTER TABLE order_user
    ADD creating_date varchar(255),
    ADD updating_date varchar(255);

ALTER TABLE user
    ADD creating_date varchar(255),
    ADD updating_date varchar(255);

ALTER TABLE seller
    ADD creating_date varchar(255),
    ADD updating_date varchar(255);


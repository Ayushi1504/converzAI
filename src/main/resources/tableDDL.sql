create schema cnv;
GO

create table cnv.customer(
    customer_id  uniqueidentifier NOT NULL PRIMARY KEY,
    customer_name varchar(255) NOT NULL
);

create table cnv.service_operator(
    operator_id  uniqueidentifier NOT NULL PRIMARY KEY,
    operator_name varchar(255) NOT NULL,
    operator_identifier varchar(255) NOT NULL
);


create table cnv.booking(
    booking_id  uniqueidentifier NOT NULL PRIMARY KEY,
    operator_id  uniqueidentifier NOT NULL,
    customer_id  uniqueidentifier NOT NULL,
    booking_status varchar(255) NOT NULL,
    booked_start_time bigint,
    booked_end_time bigint,
    CONSTRAINT FK_operator_id FOREIGN KEY (operator_id) REFERENCES cnv.service_operator,
    CONSTRAINT FK_customer_id FOREIGN KEY (customer) REFERENCES cnv.customer
);

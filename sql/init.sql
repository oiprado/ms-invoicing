drop table sale_session_status;
drop table sale_session;


create table sale_session_status
(
	id int(2) primary key auto_increment,
	name varchar(25),
	active bit	
);
                        
create table sale_session
(
	id int(11) primary key auto_increment,
	uuid varchar(255),
	created_date timestamp,
	user_id int(11),
	sale_session_status_id int(2)
);

alter table sale_session
	ADD CONSTRAINT fk_sale_session_session_sale_status
		FOREIGN KEY (sale_session_status_id) REFERENCES sale_session_status(id);

create table sale_session_causal
(
    id int(2) primary key auto_increment,
	name varchar(25),
	active bit
);

create table sale_session_log
(
	id int(11) primary key auto_increment,
	sale_session_id int(11),
	user_id int(11),
	authorized_by int(11),
	sale_session_status_id int(2),
	sale_session_causal_id int(2),
	authorized_at timestamp,
	description varchar(255)
);

alter table sale_session_log
	ADD CONSTRAINT fk_sale_session_log_sale_session_status_id
		FOREIGN KEY (sale_session_status_id) REFERENCES sale_session_status(id);

create table sale
(
	id int(11) primary key auto_increment,
	document_uuid varchar(255),
	user_id int(11),
	creation_date timestamp,
	active bit
);

create table sale_product 
(
	id int(11) primary key auto_increment,
	sale_id int(11),
    product_id int(11),
    quantity int(5),
    price float,
    tax float
);

alter table sale_product
	ADD CONSTRAINT fk_sale_product_sale_id
		FOREIGN KEY (sale_id) REFERENCES sale(id);

alter table sale_product
	ADD CONSTRAINT fk_sale_product_product_id
		FOREIGN KEY (product_id) REFERENCES product(id);

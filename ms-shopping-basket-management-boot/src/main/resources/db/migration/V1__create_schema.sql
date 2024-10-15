CREATE SCHEMA IF NOT EXISTS shopping_management AUTHORIZATION administrator;

CREATE TABLE shopping_management.users (
	user_id varchar(36) NOT NULL,
	email varchar(60) NOT NULL,
	first_name varchar(45) NOT NULL,
	last_name varchar(45) NOT NULL,
	created_by varchar(80) NULL DEFAULT 'Initial Data'::character varying,
	created_at timestamp NULL DEFAULT now(),
	updated_by varchar(80) NULL DEFAULT 'Initial Data'::character varying,
	updated_at timestamp NULL DEFAULT now(),
	CONSTRAINT shop_management_users_pk PRIMARY KEY (user_id)
);

INSERT INTO shopping_management.users (user_id, email, first_name, last_name) VALUES('6b751421-6f25-4850-8c48-536f80b1210c', 'kevin_pina@outlook.com', 'kevin', 'pina');

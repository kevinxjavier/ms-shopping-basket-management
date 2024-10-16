CREATE SCHEMA IF NOT EXISTS shopping_management AUTHORIZATION administrator;

CREATE TABLE shopping_management.item_product (
	id uuid NOT NULL,
	username varchar(45) NOT NULL,
	items varchar(5000) NOT NULL,
	created_by varchar(80) NULL DEFAULT 'Initial Data'::character varying,
	created_at timestamp NULL DEFAULT now(),
	updated_by varchar(80) NULL DEFAULT 'Initial Data'::character varying,
	updated_at timestamp NULL DEFAULT now(),
	CONSTRAINT shop_management_items_pk PRIMARY KEY (id)
);

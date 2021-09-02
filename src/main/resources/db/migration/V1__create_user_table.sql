CREATE TABLE IF NOT EXISTS public.app_user (
	id  VARCHAR (50) UNIQUE NOT NULL,
	user_name VARCHAR (255) UNIQUE NOT NULL,
	first_name VARCHAR (255) NOT NULL,
	last_name VARCHAR (255) NOT NULL,
	phone VARCHAR (255),
	password VARCHAR (255) NOT NULL,
	active boolean NOT NULL
);

ALTER TABLE ONLY public.app_user add constraint pk_app_user primary key (id);

CREATE TABLE IF NOT EXISTS public.app_user (
	id  VARCHAR (50) UNIQUE NOT NULL,
	user_name VARCHAR (255) UNIQUE NOT NULL,
	password VARCHAR (255) NOT NULL,
	active boolean NOT NULL
);

ALTER TABLE ONLY public.app_user add constraint pk_app_user primary key (id);

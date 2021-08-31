CREATE TABLE IF NOT EXISTS public.role (
	id VARCHAR ( 50 ) UNIQUE NOT NULL,
	name VARCHAR (255) UNIQUE NOT NULL
);

ALTER TABLE ONLY public.role add constraint pk_role primary key (id);

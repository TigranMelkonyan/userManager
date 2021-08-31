CREATE TABLE IF NOT EXISTS public.app_user_roles
(
    user_id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    roles_id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, roles_id),
    CONSTRAINT fk_role FOREIGN KEY (roles_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES public.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


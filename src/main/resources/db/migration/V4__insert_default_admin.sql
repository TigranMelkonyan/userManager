INSERT INTO public.app_user(id, user_name, password, active)
values('7ab5ad82-596b-4b81-9e03-daab61111b3e', 'Tigran', 'Tigran1234!', true);

INSERT INTO public.role(id, name)
values('9b9c334b-c0a5-4f62-a8d4-59e8be2648f0','ADMIN');

INSERT INTO public.app_user_roles(user_id, roles_id)
values('7ab5ad82-596b-4b81-9e03-daab61111b3e', '9b9c334b-c0a5-4f62-a8d4-59e8be2648f0');


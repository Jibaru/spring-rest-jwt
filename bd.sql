-- Before run project
CREATE DATABASE todolistspring CHARACTER SET utf8 COLLATE utf8_general_ci;

-- After run project
insert into todolistspring.rol (rol_nombre) values ('ROLE_ADMIN'),('ROLE_USER');
insert into todolistspring.usuario (`email`, `password`) values ('ignacior@gmail.com', '$2a$10$KkOTvay8rTYOtBUGGLjFquaQugvbw2Jv6RiL5pPOLT0i/yvmbIrJC');
insert into todolistspring.usuario_role (usuario_id, rol_id) values (1, 1);
insert into todolistspring.usuario_role (usuario_id, rol_id) values (1, 2);
insert into todolistspring.persona (dni, nombre, usuario_id) values ('74536964', 'ignacio', 1);
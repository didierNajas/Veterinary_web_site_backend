-- Asegura roles necesarios para registro / login
USE veterinaria;

INSERT INTO rol (id_rol, nombre_rol, descripcion, activo)
VALUES
    (1, 'admin', 'Acceso total al sistema', 1),
    (2, 'veterinario', 'Atencion clinica de mascotas', 1),
    (3, 'cliente', 'Propietario de mascotas registrado', 1)
ON DUPLICATE KEY UPDATE
    descripcion = VALUES(descripcion),
    activo = VALUES(activo);

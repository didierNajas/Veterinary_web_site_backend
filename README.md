# Veterinary Web Site — Backend

API REST del sistema de gestión de una veterinaria. Permite que el **frontend** (o Postman) registre usuarios, inicie sesión y consulte el perfil autenticado, guardando la información en **MySQL**.

---

## 1. ¿Qué problema resuelve? (lógica de negocio)

En una veterinaria digital hay distintos actores:

| Rol en el negocio | Nombre en BD | Nombre en API | Qué puede hacer (visión) |
|-------------------|--------------|---------------|---------------------------|
| Administrador | `admin` | `ADMIN` | Gestionar el sistema |
| Veterinario | `veterinario` | `VETERINARIAN` | Atender citas y mascotas |
| Cliente / dueño | `cliente` | `CUSTOMER` | Registrar mascotas y pedir citas |

**Hoy el backend ya cubre el proceso de acceso:**

1. Una persona **se registra** con nombre, correo, contraseña y rol.
2. El sistema **guarda** el usuario en MySQL (tabla `usuario`) con la contraseña cifrada.
3. Luego **inicia sesión** con correo y contraseña.
4. Si es correcto, recibe un **token JWT** (pase temporal).
5. Con ese token puede llamar rutas protegidas (por ejemplo ver su perfil).

La base de datos `veterinaria` ya tiene tablas para el resto del negocio (mascotas, citas, facturas, etc.). Esas APIs se irán agregando después; el acceso (auth) es el primer bloque listo.

```text
[Cliente / Frontend]
        |
        |  1. Registro o Login
        v
[Backend Spring Boot :8080]
        |
        |  2. Valida / guarda
        v
[MySQL — BD veterinaria]
   tablas: usuario, rol, (+ mascota, cita, ...)
```

---

## 2. ¿Cómo funciona el inicio de sesión? (proceso)

### Registro
1. El frontend envía nombre, email, password y (opcional) role.
2. El backend verifica que el correo no exista.
3. Busca el rol en la tabla `rol`.
4. Separa el nombre completo en `nombre` + `apellido`.
5. Cifra la contraseña con **BCrypt** y la guarda en `password_hash`.
6. Devuelve un **token** + datos del usuario.

### Login
1. El frontend envía email y password.
2. Spring Security busca el usuario por correo y compara el hash.
3. Si coincide y el usuario está `activo`, genera un JWT.
4. El frontend guarda el token (por ejemplo en `localStorage`).

### Peticiones protegidas
1. El frontend envía: `Authorization: Bearer <token>`.
2. Un filtro del backend lee el token, identifica al usuario y deja pasar la petición.
3. Sin token válido → respuesta **401** en JSON.

No hay “sesión” en el servidor: cada request se autentica con el JWT.

---

## 3. Stack técnico

| Tecnología | Uso |
|------------|-----|
| Java 17 | Lenguaje |
| Spring Boot 3.3 | Framework |
| Spring Security + JWT | Autenticación / autorización |
| Spring Data JPA | Acceso a MySQL |
| MySQL | Base de datos `veterinaria` |
| Maven | Dependencias y build |
| BCrypt | Hash de contraseñas |

**Puerto por defecto:** `http://localhost:8080`

---

## 4. Estructura del proyecto (qué hace cada parte)

```text
src/main/java/.../backend/
├── controller/     → Endpoints HTTP (Auth, Users, Health)
├── service/        → Lógica de negocio (registro, login)
├── model/          → Entidades JPA (User ↔ usuario, Rol ↔ rol)
├── repository/     → Consultas a la BD
├── security/       → JWT, filtro, errores 401/403
├── DTO/            → Objetos de entrada/salida JSON
├── confing/        → Security, CORS
└── exception/      → Manejo de errores en JSON
```

**Mapeo con MySQL**

| Java | Tabla MySQL |
|------|-------------|
| `User` | `usuario` |
| `Rol` | `rol` |

---

## 5. Requisitos previos

- JDK 17+
- Maven (o IntelliJ IDEA con Maven integrado)
- MySQL en marcha (BD `veterinaria` con tablas `usuario` y `rol`)
- Roles cargados (`admin`, `veterinario`, `cliente`) — script en `src/main/resources/db/auth_setup.sql`

---

## 6. Configuración y arranque

### 6.1 Credenciales MySQL (local, no se sube a Git)

```bash
cp src/main/resources/application-mysql.properties.example src/main/resources/application-mysql.properties
```

Edita `application-mysql.properties` y pon tu password real:

```properties
spring.datasource.password=TU_PASSWORD
```

El perfil activo por defecto es `mysql` (`application.properties`).

### 6.2 Roles en la BD (si aún no existen)

Ejecuta en MySQL Workbench o consola:

```sql
SOURCE src/main/resources/db/auth_setup.sql;
-- o copia el contenido de ese archivo
```

### 6.3 Arrancar

En IntelliJ: ejecutar `BackendApplication`.

O por terminal:

```bash
mvn spring-boot:run
```

Comprobar que vive:

```http
GET http://localhost:8080/api/health
```

Respuesta esperada:

```json
{ "status": "UP", "service": "veterinary-web-site-backend" }
```

---

## 7. API disponible

Base URL: `http://localhost:8080`

| Método | Ruta | Auth | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/health` | No | Estado del servicio |
| `POST` | `/api/auth/register` | No | Crear usuario |
| `POST` | `/api/auth/login` | No | Iniciar sesión |
| `GET` | `/api/users/me` | Sí (Bearer) | Perfil del usuario logueado |

### Registro — `POST /api/auth/register`

```json
{
  "fullName": "Ana Lopez",
  "email": "ana@test.com",
  "password": "123456",
  "role": "CUSTOMER"
}
```

Roles aceptados: `CUSTOMER`, `VETERINARIAN`, `ADMIN` (también `cliente`, `veterinario`, `admin`).

### Login — `POST /api/auth/login`

```json
{
  "email": "ana@test.com",
  "password": "123456"
}
```

### Respuesta típica (register / login)

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "user": {
    "id": 2,
    "fullName": "Ana Lopez",
    "email": "ana@test.com",
    "role": "CUSTOMER"
  }
}
```

### Perfil — `GET /api/users/me`

Header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Errores (formato común para el frontend)

```json
{
  "message": "Credenciales invalidas",
  "status": 401,
  "path": "/api/auth/login",
  "timestamp": "2026-08-15T17:00:00"
}
```

Hay colección Postman en: `postman/Veterinary Auth.postman_collection.json`

---

## 8. Integración con el frontend

- El backend tiene **CORS** habilitado para `http://localhost:*` y `http://127.0.0.1:*` (Vite, React, Angular, etc.).
- Tras login/register, el frontend debe guardar el `token` y enviarlo en cada ruta protegida:

```js
headers: {
  Authorization: `Bearer ${token}`,
  "Content-Type": "application/json"
}
```

Flujo recomendado en UI:

1. Pantalla de registro / login → llamar a `/api/auth/...`
2. Guardar token
3. Redirigir al panel
4. Cargar perfil con `/api/users/me`
5. Si responde 401 → limpiar token y volver al login

---

## 9. Seguridad (resumen técnico)

- Contraseñas: **nunca** en texto plano; solo hash BCrypt en `password_hash`.
- JWT firmado con secreto en `app.jwt.secret` (válido ~24 h: `app.jwt.expiration-ms`).
- Rutas públicas: `/api/auth/**`, `/api/health`.
- El resto exige autenticación.
- `application-mysql.properties` está en `.gitignore` (no subir passwords al repo).

---

## 10. Estado actual vs. siguiente pasos

**Listo**
- Registro, login, perfil (`/me`)
- Conexión a MySQL `veterinaria`
- CORS para frontend local
- Errores JSON 401/403
- Health check

**Pendiente (según tablas ya existentes en MySQL)**
- Mascotas
- Citas y horarios
- Historia clínica
- Productos / inventario
- Facturas y pagos

---

## 11. Autores / proyecto

Proyecto académico — Sitio web veterinario (backend).  
Actividad de proyecto SENA — Fase de ejecución.

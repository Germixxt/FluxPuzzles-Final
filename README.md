# FluxPuzzles - API REST con Spring Boot + JPA + MySQL

Una aplicación backend escalable desarrollada con **Spring Boot 4.0.4** y **Java 17** que gestiona puzzles interactivos, usuarios y retos diarios. Implementa autenticación JWT, seguridad con Spring Security y almacenamiento en MySQL.

---

## Tabla de Contenidos

1. [Descripción General](#descripción-general)
2. [Estructura del Proyecto](#estructura-del-proyecto)
3. [Explicación Detallada de Carpetas y Archivos](#explicación-detallada-de-carpetas-y-archivos)
4. [Explicación de Líneas de Código Clave](#explicación-de-líneas-de-código-clave)
5. [Requisitos](#requisitos)
6. [Configuración](#configuración)
7. [Ejecución del Proyecto](#ejecución-del-proyecto)
8. [Endpoints de la API](#endpoints-de-la-api)

---

## Descripción General

**FluxPuzzles** es una API REST que permite a los usuarios:
- Autenticarse con JWT (JSON Web Tokens)
- Crear y resolver puzzles matemáticos/lógicos
- Participar en retos diarios
- Gestionar su perfil de usuario
- Acceder a datos de APIs externas (JSONPlaceholder)

---

## Estructura del Proyecto

```
FluxPuzzles-Final/
├── src/
│   ├── main/
│   │   ├── java/com/duoc/fluxpuzzles/
│   │   │   ├── FluxpuzzlesApplication.java         ← Punto de entrada de la aplicación
│   │   │   ├── config/
│   │   │   │   └── WebClientConfig.java            ← Configuración de cliente HTTP
│   │   │   ├── Controller/
│   │   │   │   ├── AuthController.java             ← Endpoints de autenticación
│   │   │   │   ├── PuzzleController.java           ← Endpoints de puzzles
│   │   │   │   ├── UsuarioController.java          ← Endpoints de usuarios
│   │   │   │   └── RetoDiarioController.java       ← Endpoints de retos diarios
│   │   │   ├── dto/
│   │   │   │   ├── ApiResponse.java                ← Respuesta genérica
│   │   │   │   ├── AuthRequest.java                ← Solicitud de login
│   │   │   │   ├── AuthResponse.java               ← Respuesta de autenticación
│   │   │   │   ├── PuzzleDTO.java                  ← DTO de puzzle
│   │   │   │   ├── PuzzleCreadorDTO.java           ← DTO con datos del creador
│   │   │   │   ├── UsuarioDTO.java                 ← DTO de usuario
│   │   │   │   └── ExternalApiDTO.java             ← DTO de API externa
│   │   │   ├── exception/
│   │   │   │   ├── ApiError.java                   ← Estructura de error
│   │   │   │   └── GlobalExceptionHandler.java     ← Manejador global de excepciones
│   │   │   ├── Model/
│   │   │   │   ├── Usuario.java                    ← Entidad JPA de Usuario
│   │   │   │   └── Puzzle.java                     ← Entidad JPA de Puzzle
│   │   │   ├── Repository/
│   │   │   │   ├── UsuarioRepository.java          ← CRUD de Usuario
│   │   │   │   └── PuzzleRepository.java           ← CRUD de Puzzle
│   │   │   └── security/
│   │   │       ├── JwtUtil.java                    ← Generación y validación de JWT
│   │   │       ├── JwtFilter.java                  ← Filtro para interceptar requests
│   │   │       ├── SecurityConfig.java             ← Configuración de seguridad
│   │   │       └── UserDetailsServiceImpl.java      ← Servicio de detalles de usuario
│   │   ├── Service/
│   │   │   ├── PuzzleService.java                  ← Lógica de negocio de puzzles
│   │   │   ├── UsuarioService.java                 ← Lógica de negocio de usuarios
│   │   │   └── RetoDiarioService.java              ← Lógica de negocio de retos
│   │   └── resources/
│   │       └── application.properties              ← Configuración de la aplicación
│   └── test/
│       └── java/com/duoc/fluxpuzzles/
│           └── FluxpuzzlesApplicationTests.java    ← Tests unitarios
├── pom.xml                                          ← Dependencias de Maven
├── mvnw / mvnw.cmd                                  ← Maven Wrapper (Windows/Unix)
├── Dockerfile                                       ← Imagen Docker de la aplicación
├── docker-compose.yml                              ← Orquestación de contenedores
├── .dockerignore                                   ← Exclusiones para Docker
└── README.md                                       ← Este archivo

```

---

## Explicación Detallada de Carpetas y Archivos

### 📁 **src/main/java/com/duoc/fluxpuzzles/**

#### **FluxpuzzlesApplication.java**
**Propósito:** Punto de entrada de la aplicación Spring Boot.

```java
@SpringBootApplication
public class FluxpuzzlesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FluxpuzzlesApplication.class, args);
    }
}
```

**Explicación línea por línea:**
- `@SpringBootApplication` - Anotación que combina tres anotaciones: `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`. Indica que es la clase principal de la aplicación.
- `public static void main(String[] args)` - Método principal que se ejecuta primero. Spring necesita un método main.
- `SpringApplication.run(FluxpuzzlesApplication.class, args)` - Inicia el servidor embebido de Spring Boot (Tomcat) en el puerto 8080 e inicializa todos los beans de la aplicación.

---

### 📁 **config/**

#### **WebClientConfig.java**
**Propósito:** Configuración centralizada del cliente HTTP para consumir APIs externas.

**Líneas clave:**
- Define un `Bean` de `WebClient` que se reutiliza en toda la aplicación
- Especifica la URL base de servicios externos (ej: JSONPlaceholder)
- Permite realizar peticiones HTTP reactivas (no bloqueantes) a APIs REST externas

---

### 📁 **Controller/**

#### **PuzzleController.java**
**Propósito:** Capa de presentación. Expone endpoints REST para gestionar puzzles.

**Anotaciones principales:**
- `@RestController` - Indica que esta clase maneja peticiones HTTP y devuelve respuestas JSON
- `@RequestMapping("/api/v1/puzzles")` - Define la ruta base para todos los endpoints de este controlador
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` - Mapean métodos a operaciones HTTP específicas

**Métodos del controlador:**

| Método | HTTP | Ruta | Descripción |
|--------|------|------|-------------|
| `listarPuzzles()` | GET | `/api/v1/puzzles` | Retorna todos los puzzles |
| `crearPuzzle(puzzle)` | POST | `/api/v1/puzzles` | Crea un nuevo puzzle (requiere ADMIN) |
| `obtenerPuzzle(id)` | GET | `/api/v1/puzzles/{id}` | Obtiene un puzzle por ID |
| `actualizarPuzzle(id, puzzle)` | PUT | `/api/v1/puzzles/{id}` | Actualiza un puzzle existente |
| `eliminarPuzzle(id)` | DELETE | `/api/v1/puzzles/{id}` | Elimina un puzzle |
| `puzzlesConCreador()` | GET | `/api/v1/puzzles/con-creador` | Retorna puzzles con datos del creador |

**Explicación de líneas clave:**
```java
@Autowired
private PuzzleService puzzleService;
```
- `@Autowired` - Inyección de dependencias. Spring busca un Bean de tipo `PuzzleService` y lo inyecta automáticamente.

```java
@PostMapping
public ResponseEntity<Puzzle> crearPuzzle(@Valid @RequestBody Puzzle puzzle) {
    Puzzle nuevoPuzzle = puzzleService.savePuzzle(puzzle);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPuzzle);
}
```
- `@Valid` - Valida que los datos del Puzzle cumplan con las restricciones definidas en la clase Modelo
- `@RequestBody` - Serializa automáticamente el JSON del request a objeto Puzzle
- `ResponseEntity.status(HttpStatus.CREATED)` - Retorna código HTTP 201 (recurso creado)
- `.body(nuevoPuzzle)` - El cuerpo de la respuesta contiene el puzzle creado

#### **AuthController.java**
**Propósito:** Maneja la autenticación de usuarios.

**Endpoints:**
- `POST /api/v1/auth/login` - Autentica usuario y devuelve JWT
- `POST /api/v1/auth/register` - Registra un nuevo usuario

**Flujo de autenticación:**
1. Usuario envía credenciales (usuario/contraseña)
2. Se validan contra la base de datos
3. Si son correctas, se genera un JWT con tiempo de expiración
4. El cliente guarda el JWT y lo envía en futuras peticiones

#### **UsuarioController.java**
**Propósito:** Gestiona operaciones CRUD de usuarios.

**Endpoints:**
- GET `/api/v1/usuarios` - Listar todos
- POST `/api/v1/usuarios` - Crear (solo ADMIN)
- GET `/api/v1/usuarios/{id}` - Obtener uno
- PUT `/api/v1/usuarios/{id}` - Actualizar (solo ADMIN)
- DELETE `/api/v1/usuarios/{id}` - Eliminar (solo ADMIN)

#### **RetoDiarioController.java**
**Propósito:** Gestiona retos diarios que se obtienen de APIs externas.

**Endpoints:**
- GET `/api/v1/reto-diario/post/{id}` - Obtiene un post del reto del día
- POST `/api/v1/reto-diario/completar` - Marca un reto como completado

---

### 📁 **dto/**

DTOs (Data Transfer Objects) son objetos simples que transportan datos entre el cliente y la API, sin lógica de negocio.

#### **AuthRequest.java**
```java
public class AuthRequest {
    private String username;    // Nombre de usuario
    private String password;    // Contraseña
}
```
- Se usa al recibir el JSON de login desde el cliente

#### **AuthResponse.java**
```java
public class AuthResponse {
    private String token;       // JWT generado
    private String username;    // Usuario autenticado
    private String role;        // Rol del usuario (USER/ADMIN)
}
```
- Se envía como respuesta después de autenticación exitosa

#### **PuzzleDTO.java**
Versión simplificada del modelo Puzzle que oculta información sensible.

#### **PuzzleCreadorDTO.java**
Combina datos del Puzzle con información del Usuario que lo creó (JOIN).

---

### 📁 **exception/**

#### **GlobalExceptionHandler.java**
**Propósito:** Captura todas las excepciones de la aplicación y devuelve respuestas JSON consistentes.

**Cómo funciona:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
```

- `@ControllerAdvice` - Intercepta excepciones globalmente en toda la aplicación
- `@ExceptionHandler` - Define qué hacer cuando ocurra una excepción específica
- En lugar de devolver un error HTML, retorna JSON estructurado

#### **ApiError.java**
```java
public class ApiError {
    private int status;          // Código HTTP (404, 500, etc)
    private String mensaje;      // Mensaje descriptivo
    private LocalDateTime fecha; // Cuándo ocurrió
}
```

---

### 📁 **Model/**

Entidades JPA que mapean directamente a tablas de la base de datos.

#### **Usuario.java**
**Propósito:** Representa un usuario en la base de datos.

```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;      // PK, autoincrementada

    private String nombre;   // Nombre del usuario
    private Integer edad;    // Edad
    private String correo;   // Email único
    private String username; // Username único para login
    private String password; // Contraseña (hashada con BCrypt)
    private String role;     // Rol: "USER" o "ADMIN"
}
```

**Explicación de anotaciones:**
- `@Entity` - Indica que es una entidad mapeada a tabla
- `@Table(name = "usuarios")` - Especifica el nombre de la tabla en MySQL
- `@Id` - Define como clave primaria
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` - El ID se genera automáticamente (AUTO_INCREMENT en MySQL)
- `@Data` (Lombok) - Genera automáticamente getters, setters, toString(), equals(), hashCode()
- `@NoArgsConstructor` - Crea constructor sin argumentos
- `@AllArgsConstructor` - Crea constructor con todos los argumentos

#### **Puzzle.java**
**Propósito:** Representa un puzzle en la base de datos.

**Campos típicos:**
```java
private Integer id;          // Identificador único
private String titulo;       // Título del puzzle
private String descripcion;  // Descripción
private String dificultad;   // "FÁCIL", "MEDIO", "DIFÍCIL"
private Integer idCreador;   // FK a Usuario (quién lo creó)
private LocalDateTime createdAt; // Fecha de creación
```

---

### 📁 **Repository/**

Interfaces que extienden JpaRepository para operaciones de base de datos.

#### **PuzzleRepository.java**
```java
@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {
    List<Puzzle> findByIdCreador(Integer idCreador);
    Puzzle findByTitulo(String titulo);
}
```

**Spring Data JPA automáticamente implementa:**
- `findAll()` - SELECT * FROM puzzles
- `findById(id)` - SELECT * FROM puzzles WHERE id = ?
- `save(entity)` - INSERT/UPDATE
- `delete(entity)` - DELETE
- Métodos personalizados con convención de nombres (`findBy` + nombreCampo)

---

### 📁 **security/**

#### **SecurityConfig.java**
**Propósito:** Configura las políticas de seguridad de la aplicación.

**Configuraciones principales:**
```java
.csrf(csrf -> csrf.disable())
```
- Deshabilita protección CSRF (no necesaria en APIs REST stateless)

```java
.sessionManagement(session -> 
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
```
- No almacena sesiones en el servidor (cada request se autentica con JWT)

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/v1/auth/**").permitAll()      // Login: público
    .requestMatchers(HttpMethod.GET, "/api/v1/**")       // GET: autenticado
        .hasAnyRole("USER", "ADMIN")
    .requestMatchers(HttpMethod.POST, "/api/v1/**")      // POST: solo ADMIN
        .hasRole("ADMIN")
)
```
- Define quién puede acceder a cada endpoint

#### **JwtUtil.java**
**Propósito:** Genera y valida JWT (JSON Web Tokens).

**Métodos principales:**
```java
public String generarToken(String username, String role)
```
- Crea un JWT firmado con una clave secreta
- Incluye claims (datos) como username y rol
- Configura tiempo de expiración (ej: 24 horas)

```java
public String extraerUsername(String token)
public boolean validarToken(String token)
```
- Extrae el username del JWT
- Valida que el token no esté expirado y la firma sea válida

#### **JwtFilter.java**
**Propósito:** Intercepta cada petición HTTP para validar el JWT.

**Flujo:**
1. Extrae el JWT del header `Authorization: Bearer <token>`
2. Valida el token usando `JwtUtil`
3. Si es válido, carga el usuario en el contexto de seguridad
4. Si es inválido, rechaza la petición con error 401

#### **UserDetailsServiceImpl.java**
**Propósito:** Carga datos del usuario desde la base de datos.

```java
public UserDetails loadUserByUsername(String username) {
    Usuario usuario = usuarioRepository.findByUsername(username);
    return new User(
        usuario.getUsername(),
        usuario.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole()))
    );
}
```
- Spring Security lo llama cuando necesita autenticar un usuario
- Busca el usuario en la BD y devuelve sus detalles

---

### 📁 **Service/**

Capas de lógica de negocio. Aquí van las reglas de validación y procesamiento de datos.

#### **PuzzleService.java**
```java
@Service
@Transactional
public class PuzzleService {
    @Autowired
    private PuzzleRepository puzzleRepository;

    public List<Puzzle> getPuzzles() {
        return puzzleRepository.findAll();
    }

    public Puzzle savePuzzle(Puzzle puzzle) {
        // Validar que el puzzle tenga al menos un título
        if (puzzle.getTitulo() == null || puzzle.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        return puzzleRepository.save(puzzle);
    }

    public Puzzle getPuzzleId(Integer id) {
        return puzzleRepository.findById(id).orElse(null);
    }

    public Puzzle updatePuzzle(Puzzle puzzle) {
        if (puzzleRepository.existsById(puzzle.getId())) {
            return puzzleRepository.save(puzzle);
        }
        return null;
    }

    public void deletePuzzle(Integer id) {
        puzzleRepository.deleteById(id);
    }
}
```

**Explicación:**
- `@Service` - Anotación que marca esta clase como servicio (componente de lógica)
- `@Transactional` - Cada operación se ejecuta en una transacción (atomicidad)
- Los métodos encapsulan operaciones CRUD y agregan validaciones
- La inyección de dependencia `@Autowired` trae el repositorio

#### **UsuarioService.java**
Similar al anterior pero con lógica adicional:
```java
public void registrarUsuario(UsuarioDTO dto) {
    // Validar que no exista usuario con ese username
    if (usuarioRepository.findByUsername(dto.getUsername()) != null) {
        throw new IllegalArgumentException("Username ya existe");
    }
    
    // Hashear la contraseña antes de guardar
    String passwordHasheada = passwordEncoder.encode(dto.getPassword());
    
    Usuario usuario = new Usuario();
    usuario.setUsername(dto.getUsername());
    usuario.setPassword(passwordHasheada);
    usuario.setRole("USER");
    
    usuarioRepository.save(usuario);
}
```

#### **RetoDiarioService.java**
```java
@Service
public class RetoDiarioService {
    @Autowired
    private WebClient webClient;

    public ExternalApiDTO obtenerRetoDiario(Integer id) {
        // Realiza petición a JSONPlaceholder para obtener posts
        return webClient.get()
            .uri("/posts/" + id)
            .retrieve()
            .bodyToMono(ExternalApiDTO.class)
            .block(); // Espera la respuesta (síncrono)
    }
}
```
- Usa `WebClient` (cliente HTTP reactivo) para llamar a APIs externas
- Convierte la respuesta JSON a objeto Java automáticamente

---

### 📁 **resources/**

#### **application.properties**
**Propósito:** Archivo de configuración de la aplicación.

```properties
# Nombre de la aplicación
spring.application.name=fluxpuzzles

# Configuración de base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3307/fluxpuzzles?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate / JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# API Externa (Reto Diario)
jsonplaceholder.base-url=https://jsonplaceholder.typicode.com
```

**Explicación línea por línea:**
- `spring.datasource.url` - URL de conexión a MySQL (crea BD si no existe)
- `createDatabaseIfNotExist=true` - Crea automáticamente la BD `fluxpuzzles`
- `spring.datasource.username=root` - Usuario de MySQL
- `spring.datasource.password=root` - Contraseña
- `ddl-auto=update` - Hibernate crea/actualiza tablas según las entidades (no borra datos)
- `show-sql=true` - Imprime las queries SQL en consola (útil para debug)
- `jsonplaceholder.base-url` - URL base para consumir API de retos diarios

---

### **Dockerfile**
**Propósito:** Define cómo construir una imagen Docker de la aplicación.

```dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests
```

**Build stage (etapa 1):**
- `FROM maven:3.9.9-eclipse-temurin-17` - Imagen base con Maven y Java 17
- `WORKDIR /app` - Directorio de trabajo dentro del contenedor
- `COPY pom.xml .` - Copia el archivo de dependencias
- `COPY src ./src` - Copia el código fuente
- `RUN mvn clean package` - Compila el proyecto y genera el JAR
  - `-B` flag: batch mode (sin input interactivo)
  - `-DskipTests` - No ejecuta tests (para hacer build más rápido)

```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

**Runtime stage (etapa 2):**
- `FROM eclipse-temurin:17-jre` - Imagen base solo con JRE (sin Maven)
- `COPY --from=build` - Copia el JAR generado en la etapa anterior
- `EXPOSE 8080` - Expone el puerto 8080 (documentación)
- `ENTRYPOINT` - Comando que se ejecuta al iniciar el contenedor

**Ventaja:** Build multi-stage reduce el tamaño de la imagen final (Maven no se incluye)

---

### **docker-compose.yml**
**Propósito:** Orquesta múltiples contenedores (app + base de datos).

```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/fluxpuzzles?createDatabaseIfNotExist=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: fluxpuzzles
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

**Explicación:**
- Define dos servicios: `app` (aplicación) y `db` (base de datos)
- `depends_on: db` - App espera a que BD esté lista antes de iniciarse
- `environment` - Variables de entorno (Spring lee estas propiedades)
- `volumes` - Persistencia de datos de MySQL en disco

---

## Explicación de Líneas de Código Clave

### 1. **Inyección de Dependencias**
```java
@Autowired
private PuzzleService puzzleService;
```
- Spring automáticamente busca un Bean de tipo `PuzzleService` y lo asigna
- Permite que el controlador use el servicio sin crear `new PuzzleService()`
- Favorece testing (fácil de mockear) y desacoplamiento

### 2. **Validación de Datos**
```java
@PostMapping
public ResponseEntity<Puzzle> crearPuzzle(@Valid @RequestBody Puzzle puzzle) {
    // ...
}
```
- `@Valid` - Spring valida que el Puzzle cumpla restricciones (ej: @NotNull, @Size, @Email)
- Si no cumple, devuelve error 400 automáticamente

### 3. **Transacciones**
```java
@Transactional
public void operacionCompleja() {
    usuario.save();
    puzzle.save();
    // Si falla uno, se revierte todo (ROLLBACK)
}
```
- `@Transactional` - Garantiza que o ambas operaciones se guardan o ninguna

### 4. **Manejo de Excepciones**
```java
@ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<ApiError> handleNotFoundException(EntityNotFoundException ex) {
    ApiError error = new ApiError(404, ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}
```
- Captura excepciones globalmente y devuelve respuestas JSON consistentes

### 5. **Seguridad con JWT**
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/v1/auth/**").permitAll()
        .requestMatchers("/api/v1/**").authenticated()
    );
}
```
- `/auth/**` - Público (login sin token)
- Otras rutas - Requieren token JWT válido

---

## Requisitos

- **Java 17 o superior** instalado
- **Maven** (o usar Maven Wrapper incluido: `mvnw`/`mvnw.cmd`)
- **MySQL 8.0+** en el puerto 3307 (u otro configurado en `application.properties`)
  - Usuario: `root`
  - Contraseña: `root`
- **Docker y Docker Compose** (opcional, para ejecución en contenedores)
- **IDE**: VS Code, IntelliJ IDEA o Eclipse
- **Cliente API**: Postman o Insomnia para probar endpoints

**Nota:** Hibernate crea automáticamente las tablas al iniciar (`ddl-auto=update`). No es necesario crearlas manualmente.

---

## Configuración

### Base de Datos
El archivo [application.properties](src/main/resources/application.properties) contiene:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/fluxpuzzles?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jsonplaceholder.base-url=https://jsonplaceholder.typicode.com
```

**Parámetros clave:**
- `createDatabaseIfNotExist=true` - Crea la BD si no existe
- `ddl-auto=update` - Actualiza tablas sin borrar datos
- `show-sql=true` - Imprime SQL en consola para debug

---

## Ejecución del Proyecto

### Opción A: Maven Wrapper (Recomendado)

**Windows (PowerShell/CMD):**
```powershell
.\mvnw spring-boot:run
```

**Linux/macOS:**
```bash
./mvnw spring-boot:run
```

### Opción B: Compilar y ejecutar JAR

```bash
.\mvnw clean package
java -jar target/fluxpuzzles-0.0.1-SNAPSHOT.jar
```

### Opción C: Docker Compose (BD + App)

```bash
docker-compose up -d --build
```

- **App:** http://localhost:8080
- **MySQL:** localhost:3306

### Opción D: IDE (VS Code/IntelliJ)

1. Abrir la carpeta del proyecto
2. Instalar extensiones Java (Extension Pack for Java en VS Code)
3. Click derecho en `FluxpuzzlesApplication.java` → Run

---

## Endpoints de la API

### URL Base
```
http://localhost:8080/api/v1
```

### 📋 **Autenticación (/auth/)**

| Método | Ruta | Autenticación | Descripción |
|--------|------|--------|-------------|
| POST | `/auth/login` | ❌ Pública | Login con username/password, devuelve JWT |
| POST | `/auth/register` | ❌ Pública | Registrar nuevo usuario |

**Ejemplo de login:**
```json
POST /api/v1/auth/login
{
  "username": "user123",
  "password": "pass123"
}

Respuesta (200):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "user123",
  "role": "USER"
}
```

---

### 🧩 **Puzzles (/puzzles/)**

| Método | Ruta | Autenticación | Rol | Descripción |
|--------|------|--------|------|-------------|
| GET | `/puzzles` | ✅ JWT | USER/ADMIN | Listar todos los puzzles |
| POST | `/puzzles` | ✅ JWT | **ADMIN** | Crear nuevo puzzle |
| GET | `/puzzles/{id}` | ✅ JWT | USER/ADMIN | Obtener puzzle por ID |
| PUT | `/puzzles/{id}` | ✅ JWT | **ADMIN** | Actualizar puzzle |
| DELETE | `/puzzles/{id}` | ✅ JWT | **ADMIN** | Eliminar puzzle |
| GET | `/puzzles/con-creador` | ✅ JWT | USER/ADMIN | Listar puzzles con datos del creador |

**Ejemplo de solicitud (con JWT):**
```
GET /api/v1/puzzles
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

Respuesta (200):
[
  {
    "id": 1,
    "titulo": "Fibonacci",
    "descripcion": "Serie de números",
    "dificultad": "FÁCIL",
    "idCreador": 5,
    "createdAt": "2024-06-28T10:30:00"
  }
]
```

---

### 👤 **Usuarios (/usuarios/)**

| Método | Ruta | Autenticación | Rol | Descripción |
|--------|------|--------|------|-------------|
| GET | `/usuarios` | ✅ JWT | USER/ADMIN | Listar usuarios |
| POST | `/usuarios` | ✅ JWT | **ADMIN** | Crear usuario |
| GET | `/usuarios/{id}` | ✅ JWT | USER/ADMIN | Obtener usuario |
| PUT | `/usuarios/{id}` | ✅ JWT | **ADMIN** | Actualizar usuario |
| DELETE | `/usuarios/{id}` | ✅ JWT | **ADMIN** | Eliminar usuario |

---

### 🎯 **Retos Diarios (/reto-diario/)**

| Método | Ruta | Autenticación | Descripción |
|--------|------|--------|-------------|
| GET | `/reto-diario/post/{id}` | ✅ JWT | Obtener reto del día de JSONPlaceholder |
| POST | `/reto-diario/completar` | ✅ JWT | Marcar reto como completado |

---

## Resumen de Tecnologías

| Componente | Tecnología | Versión |
|------------|-----------|---------|
| **Framework** | Spring Boot | 4.0.4 |
| **Java** | OpenJDK | 17 |
| **ORM** | Spring Data JPA / Hibernate | Último |
| **Base de Datos** | MySQL | 8.0+ |
| **Seguridad** | Spring Security + JWT | Último |
| **Cliente HTTP** | WebClient (Spring WebFlux) | Reactivo |
| **Contenedores** | Docker + Docker Compose | Último |
| **Build** | Maven | 3.9.9 |
| **Validación** | Hibernate Validator | Último |
| **Utilidades** | Lombok | Último |

---

## Notas Importantes

1. **JWT Token:** Incluir en cada petición autenticada:
   ```
   Authorization: Bearer <token>
   ```

2. **Roles:** 
   - `USER` - Puede leer datos
   - `ADMIN` - Puede leer, crear, actualizar y eliminar

3. **Contraseñas:** Guardadas en la BD con hash BCrypt (nunca en texto plano)

4. **Persistencia de Datos:** Los datos en MySQL persisten entre reinicios de la app

5. **Logs:** Ejecutar con `show-sql=true` para ver las queries SQL en consola (ideal para debug)

---

## Troubleshooting

**Error: "Connection refused" a MySQL**
- Verificar que MySQL esté corriendo
- Revisar puerto en `application.properties` (por defecto 3307)

**Error: "Unknown user 'root'"**
- Verificar credenciales en `application.properties`
- Usuario y contraseña deben coincidir con tu MySQL local

**Error: "Port 8080 already in use"**
- Cambiar puerto en `application.properties`: `server.port=8081`

**Error: "JWT expired"**
- Generar nuevo JWT (hacer login nuevamente)

---

**Hecho con ❤️ usando Spring Boot y Java 17**

Buscar puzzle por ID (GET)

Crear un nuevo puzzle (POST)

Actualizar un puzzle existente (PUT)

Eliminar un puzzle (DELETE)

Listar puzzles mapeados con su creador utilizando un DTO (GET)

### 5.2 Listar puzzles
Método: GET

URL: /api/v1/puzzles
Descripción: Retorna una lista con todos los puzzles almacenados en la base de datos, incluyendo la relación completa del usuario creador.

### 5.3 Buscar puzzle por ID
Método: GET

URL: /api/v1/puzzles/{id}
Descripción: Retorna un único puzzle que coincida con el identificador numérico proporcionado en la URL. Si no existe, se dispara el manejador global de errores.

### 5.4 Crear puzzle
Método: POST

URL: /api/v1/puzzles

Descripción: Registra un nuevo puzzle asignándolo a un usuario existente mediante su ID. El cuerpo de la petición pasa por validaciones automáticas.

Body JSON ejemplo:

JSON
{
  "nombrePuzzle": "Laberinto de Fuego",
  "descripcion": "Un puzzle de lógica infernal",
  "dificultad": "Experto",
  "usuario": {
    "id": 1
  }
}
### 5.5 Actualizar puzzle
Método: PUT

URL: /api/v1/puzzles/{id}

Descripción: Reemplaza los datos de un puzzle existente identificado por su ID con los nuevos datos enviados en el cuerpo de la petición.

Body JSON ejemplo:

JSON
{
  "id": 1,
  "nombrePuzzle": "Laberinto de Fuego V2",
  "descripcion": "Un puzzle de lógica infernal parcheado",
  "dificultad": "Pesadilla",
  "usuario": {
    "id": 1
  }
}
## 5.6 Eliminar puzzle
Método: DELETE

URL: /api/v1/puzzles/{id}

Descripción: Remueve de forma permanente un registro de puzzle de la base de datos utilizando su ID.

## 5.7 Listar puzzles con creador (Cruce de Tablas / DTO)
Método: GET

URL: /api/v1/puzzles/con-creador

Descripción: Retorna todos los puzzles simplificados junto con el nombre de su creador. Este endpoint utiliza una proyección DTO para omitir información confidencial del usuario (como contraseñas, hashes o correos electrónicos).

Respuesta JSON ejemplo:

JSON
{
  {
    "nombrePuzzle": "Laberinto de Fuego",
    "nombreCreador": "German Felipe Letelier"
  }
}
### ¿Cómo funciona el DTO internamente?
La clase PuzzleCreadorDTO es un Objeto Java Plano (POJO) que contiene únicamente los atributos necesarios que deseamos mostrar al cliente final. En la capa de servicio se hace uso del API de Streams de Java para transformar la lista de entidades en una estructura limpia de DTOs:
puzzleRepository.findAll().stream()
    .filter(p -> p.getUsuario() != null)
    .map(p -> new PuzzleCreadorDTO(
        p.getNombrePuzzle(),
        p.getUsuario().getNombre()
    ))
    .toList();
## 6. Dependencias principales (pom.xml)
spring-boot-starter-web: Proporciona todas las herramientas necesarias para construir aplicaciones web, incluyendo soporte para servicios RESTful y el servidor embebido Tomcat.

spring-boot-starter-data-jpa: Facilita la persistencia de datos en bases de datos relacionales utilizando Java Persistence API (JPA) junto con Hibernate como el proveedor core.

mysql-connector-j: Controlador JDBC oficial de MySQL que permite la comunicación directa entre la aplicación Spring Boot y el motor de base de datos relacional.

spring-boot-starter-validation: Añade soporte para la validación de Beans basada en anotaciones estándar de Jakarta (@NotNull, @NotBlank, @Size, @Email), garantizando la integridad de los datos de entrada.

spring-boot-starter-webflux: Introduce capacidades de programación reactiva. En este proyecto se utiliza específicamente para instanciar WebClient y realizar peticiones HTTP eficientes hacia servicios externos.

lombok: Biblioteca que se conecta al compilador para generar automáticamente código repetitivo como métodos accesores (getters/setters), métodos equals, hashCode, toString y constructores mediante simples anotaciones.

## 7. Manejador global de errores (@ControllerAdvice)
La aplicación implementa una clase especializada GlobalExceptionHandler utilizando la anotación @RestControllerAdvice para capturar cualquier excepción lanzada en la capa de controladores, asegurando respuestas estandarizadas y limpias para el cliente.

Flujo del control de excepciones:

Ocurre un error imprevisto en la aplicación (Ej: El cliente envía un correo vacío que viola la regla @NotBlank o la API de retos diarios externa está caída).

@RestControllerAdvice intercepta la excepción antes de que la aplicación responda con una traza de código del servidor.

El método correspondiente (anotado con @ExceptionHandler) toma el control del flujo y da formato al mensaje.

Se instancia y retorna un objeto estándar ApiError acompañado del código de estado HTTP correcto (como 400 Bad Request, 404 Not Found o 500 Internal Server Error).

Estructura de la respuesta de error estándar (JSON):

JSON
{
  "codigo": 400,
  "mensaje": "Error en la validación de campos",
  "detalle": "correo: no debe estar vacío"
}
## 8. WebClient y consumo de APIs externas
Se seleccionó e implementó WebClient (un componente moderno de Spring WebFlux) para interactuar de manera óptima con la API pública de pruebas JSONPlaceholder. El objetivo de esta integración es simular el aprovisionamiento dinámico de un "Reto Diario" externo para los usuarios de la plataforma.

Configuración: La clase WebClientConfig.java inyecta la propiedad jsonplaceholder.base-url configurada en el archivo application.properties para inicializar el cliente HTTP centralizado.

Servicio: La clase RetoDiarioService.java se encarga de estructurar la ruta del recurso, ejecutar la llamada HTTP y mapear automáticamente el JSON de respuesta en un objeto de transferencia de datos local ExternalApiDTO.java.

Endpoint expuesto: GET /api/v1/reto-diario?id=1

Flujo detallado de la llamada externa de red:

Plaintext
Cliente → Petición HTTP GET /api/v1/reto-diario?id=1
↳ Intercepta: RetoDiarioController
  ↳ Llama a: RetoDiarioService.obtenerPuzzleExterno(1)
    ↳ Invoca: WebClient.get().uri("/posts/1")
      ↳ Conexión de Red: [https://jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com)
        ↳ Respuesta: Retorna el JSON nativo desde el servidor remoto
      ↳ Mapeo automático de datos: Transforma el JSON a la clase ExternalApiDTO
    ↳ Retorna: Objeto mapeado al controlador
↳ Respuesta final: 200 OK con el Reto Diario estructurado enviado al Cliente
## 9. Autores
* **German Letelier**

* **Michael Ciudad**

* **Erick Villagran**

Proyecto final desarrollado para la asignatura de Programación Full Stack.
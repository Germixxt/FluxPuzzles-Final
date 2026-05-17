FluxPuzzles - API REST con Spring Boot + JPA + MySQL
Proyecto a nivel de producción para aprender arquitectura por capas con Spring Boot, desarrollado para la gestión de puzzles y usuarios.

controller (capa web / endpoints REST)

service (lógica de negocio)

repository (acceso a datos con JPA / Hibernate)

model (entidades JPA / estructura de datos)

dto (objetos de transferencia de datos)

1) Requisitos
Java 17+

Maven (opcional si usas mvnw)

MySQL corriendo en localhost:3306 (usuario root, sin contraseña)

IDE recomendado: VS Code / IntelliJ / Eclipse

Postman (opcional para probar la API)

Nota: Hibernate crea automáticamente las tablas puzzles y usuarios al iniciar la aplicación (ddl-auto=update). No es necesario crearlas manualmente en Laragon/MySQL.

2) Configuración de base de datos
El archivo src/main/resources/application.properties contiene la conexión:

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/fluxpuzzles?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# API Externa (Reto Diario)
jsonplaceholder.base-url=https://jsonplaceholder.typicode.com
createDatabaseIfNotExist=true → crea la base de datos fluxpuzzles automáticamente si no existe.

ddl-auto=update → Hibernate actualiza el esquema automáticamente según las entidades Puzzle y Usuario.

show-sql=true → muestra las consultas SQL generadas en la consola.

3) ¿Cómo ejecutar el proyecto?
Opción A: usando Maven Wrapper (recomendado)
Desde la carpeta raíz del proyecto (Fluxpuzzles):

En Windows (PowerShell / CMD):

Bash
.\mvnw spring-boot:run
En Linux / macOS:

Bash
./mvnw spring-boot:run
Opción B: compilar y ejecutar el JAR

Bash
.\mvnw clean package
java -jar target/fluxpuzzles-0.0.1-SNAPSHOT.jar
4) URL base de la API
Por defecto Spring Boot levanta en el puerto 8080:
http://localhost:8080

Base paths de los controladores:

/api/v1/puzzles

/api/v1/usuarios

/api/v1/reto-diario

5) Endpoints disponibles (Ejemplo: Puzzles)
5.1 Listar puzzles
Método: GET

URL: /api/v1/puzzles

Descripción: Retorna todos los puzzles almacenados en la base de datos.

5.2 Buscar puzzle por ID
Método: GET

URL: /api/v1/puzzles/{id}

Descripción: Retorna un puzzle específico por su id.

5.3 Crear puzzle
Método: POST

URL: /api/v1/puzzles

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
5.4 Actualizar puzzle
Método: PUT

URL: /api/v1/puzzles/{id}

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
5.5 Eliminar puzzle
Método: DELETE

URL: /api/v1/puzzles/{id}

Descripción: Elimina un puzzle por id.

5.6 Listar puzzles con creador (Cruce de Tablas / DTO)
Método: GET

URL: /api/v1/puzzles/con-creador

Descripción: Retorna todos los puzzles junto con el nombre de su creador, usando un DTO proyectado para proteger datos sensibles como contraseñas o correos.

Respuesta JSON ejemplo:

JSON
[
  {
    "nombrePuzzle": "Laberinto de Fuego",
    "nombreCreador": "German Felipe Letelier"
  }
]
¿Cómo funciona el DTO internamente?
La clase PuzzleCreadorDTO es un objeto simple (POJO) con solo los campos que queremos exponer. En el servicio se usa un stream para transformar la lista de entidades en una lista de DTOs:

Java
puzzleRepository.findAll().stream()
    .filter(p -> p.getUsuario() != null)
    .map(p -> new PuzzleCreadorDTO(
            p.getNombrePuzzle(),
            p.getUsuario().getNombre()
    ))
    .toList();
6) Estructura del proyecto y explicación por capas
Plaintext
src/main/java/com/duoc/fluxpuzzles/
├── config/           ← Configuración de WebClient
├── Controller/       ← Presentación / API REST
├── dto/              ← Objetos de transferencia de datos
├── exception/        ← Manejo global de errores (ControllerAdvice)
├── Model/            ← Entidades JPA
├── Repository/       ← Interfaces Spring Data JPA
└── Service/          ← Lógica de negocio
6.1 Controller
Recibe las peticiones HTTP. Utiliza anotaciones como @RestController, @RequestMapping, @GetMapping, @PostMapping, @RequestBody, y delega la lógica a los Services inyectados con @Autowired.

6.2 Service
Centraliza reglas de negocio y coordina el acceso al repositorio. Utiliza @Service para ser detectado por Spring.

6.3 Repository
Interfaces que extienden JpaRepository<T, ID>. Generan automáticamente la implementación de métodos CRUD (findAll, findById, save, deleteById) traduciendo llamadas a comandos SQL a través de Hibernate.

6.4 Model
Entidades que representan las tablas de la base de datos (e.g., Puzzle y Usuario). Utilizan @Entity, @Id, y anotaciones de Lombok (@Data, @AllArgsConstructor, @NoArgsConstructor) para evitar código boilerplate.

7) Dependencias principales (pom.xml)
spring-boot-starter-web: Para construir la API REST.

spring-boot-starter-data-jpa + mysql-connector-j: Para la persistencia y conexión a base de datos.

spring-boot-starter-validation: Soporte para @Valid, @NotNull, @NotBlank.

spring-boot-starter-webflux: Para utilizar WebClient y consumir APIs externas.

lombok: Para auto-generar getters, setters y constructores.

8) Manejador global de errores (@ControllerAdvice)
La aplicación utiliza GlobalExceptionHandler para centralizar la gestión de excepciones, evitando bloques try-catch repetitivos.

Flujo:

Ocurre un error (Ej: Falla una validación o la API externa no responde).

@RestControllerAdvice intercepta la excepción.

El manejador correspondiente (ej: @ExceptionHandler(MethodArgumentNotValidException.class)) procesa el error.

Retorna un objeto estándar ApiError con un código HTTP adecuado (400, 404, 500).

JSON
{
  "codigo": 400,
  "mensaje": "Error en la validación de campos",
  "detalle": "correo: no debe estar vacío"
}
9) WebClient y consumo de APIs externas
Se implementó WebClient (parte de Spring WebFlux) para consumir la API externa JSONPlaceholder de forma síncrona.

Configuración: WebClientConfig.java lee la URL base desde el application.properties y expone un @Bean.

Servicio: RetoDiarioService.java construye la ruta, hace la petición y mapea la respuesta directamente a la clase ExternalApiDTO.java.

Endpoint: GET /api/v1/reto-diario?id=1

Flujo de la llamada externa:

Plaintext
Cliente → GET /api/v1/reto-diario?id=1
  ↳ RetoDiarioController
    ↳ RetoDiarioService.obtenerPuzzleExterno(1)
      ↳ WebClient.get().uri("/posts/1")
        ↳ jsonplaceholder.typicode.com
          ↳ Retorna JSON → Mapea a ExternalApiDTO
            ↳ Retorna 200 OK al Cliente
10) Autor
German Felipe Letelier Muñoz
Michael Ciudad
Erick Villagran
Desarrollador y Fundador de Tecnologías Move SpA
Asignatura de Programación Full Stack.

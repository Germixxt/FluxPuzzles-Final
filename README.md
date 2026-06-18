# FluxPuzzles - API REST con Spring Boot + JPA + MySQL

## 1. Capas de modelo

* **controller**: Capa web encargada de exponer los endpoints REST, recibir las peticiones HTTP del cliente y validar los datos de entrada.
* **service**: Capa de lógica de negocio donde se aplican las reglas del sistema, se procesan los datos y se coordinan las llamadas a los repositorios.
* **repository**: Capa de acceso a datos que interactúa directamente con la base de datos MySQL a través de Spring Data JPA e Hibernate.
* **model**: Entidades JPA que representan la estructura de las tablas de la base de datos y sus relaciones (ej. Relación Uno a Muchos entre Usuario y Puzzle).
* **dto**: Objetos de transferencia de datos utilizados para moldear las respuestas de la API, ocultando datos sensibles o combinando información de múltiples tablas.

### 1.1 Estructura del directorio

plaintext
src/main/java/com/duoc/fluxpuzzles/
├── config/      ← Configuración de WebClient para llamadas a APIs externas
├── Controller/  ← Controladores REST (Capa de presentación)
├── dto/         ← Clases DTO para el intercambio seguro de datos
├── exception/   ← Manejo global de errores y excepciones del sistema
├── Model/       ← Entidades JPA que mapean las tablas de MySQL
├── Repository/  ← Interfaces que heredan de JpaRepository
└── Service/     ← Clases de servicios con la lógica de negocio

* **Config**
Contiene las clases de configuración de la aplicación. Aquí se define el Bean de WebClient, especificando la URL base de los servicios externos que consumirá la API.

* **Controller**
Recibe las peticiones HTTP. Utiliza anotaciones como @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping y @RequestBody, y delega la ejecución de la lógica a los Services inyectados mediante @Autowired.

* **Service**
Centraliza las reglas de negocio de la aplicación y coordina el acceso al repositorio. Lleva la anotación @Service para que Spring lo reconozca como un componente administrado en su contenedor de inversión de control.

* **Repository**
Interfaces que extienden JpaRepository<T, ID>. Spring Data JPA genera automáticamente la implementación de los métodos CRUD estándar (findAll, findById, save, deleteById) abstrayendo las consultas SQL nativas a través de Hibernate.

* **Model**
Clases de entidades que mapean directamente a las tablas de la base de datos. Utilizan anotaciones de JPA (@Entity, @Id, @GeneratedValue, @ManyToOne) y anotaciones de Lombok (@Data, @AllArgsConstructor, @NoArgsConstructor) para automatizar la creación de getters, setters y constructores.

## 2. Requisitos
Java 17 o superior instalado.

Maven instalado (opcional, ya que el proyecto incluye el Maven Wrapper mvnw).

MySQL corriendo en el puerto 3306 (por defecto configurado para el usuario root sin contraseña).

Un entorno de desarrollo (IDE) recomendado como VS Code, IntelliJ IDEA o Eclipse.

Postman o Insomnia para realizar las pruebas de los endpoints de la API.

Nota: Hibernate crea automáticamente las tablas puzzles y usuarios al iniciar la aplicación (ddl-auto=update). No es necesario crearlas manualmente en Laragon, phpMyAdmin o MySQL Workbench.

## 3. Configuración de base de datos
El archivo src/main/resources/application.properties contiene los parámetros de conexión y el comportamiento del ORM:

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/fluxpuzzles?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Explicación de las propiedades:

createDatabaseIfNotExist=true: Le indica al conector de MySQL que cree la base de datos llamada fluxpuzzles automáticamente si aún no existe.

ddl-auto=update: Hibernate analiza las entidades del paquete Model y actualiza o crea la estructura de las tablas de forma automática sin borrar los datos existentes.

show-sql=true: Imprime en la consola de la aplicación cada consulta SQL que Hibernate genera en tiempo de ejecución, ideal para depuración.

## 4. ¿Cómo ejecutar el proyecto?
Opción A: usando Maven Wrapper (recomendado)
Desde la carpeta raíz del proyecto (donde se encuentra el archivo mvnw), ejecuta en tu terminal:

En Windows (PowerShell / CMD):

.\mvnw spring-boot:run
En Linux / macOS:

./mvnw spring-boot:run
Opción B: compilar y ejecutar el archivo JAR
Si deseas empaquetar la aplicación en un archivo ejecutable independiente:

.\mvnw clean package
java -jar target/fluxpuzzles-0.0.1-SNAPSHOT.jar
Opción C: usando Docker y Docker Compose
El proyecto incluye un Dockerfile y un docker-compose.yml. Para levantar la aplicación junto con una base de datos MySQL en contenedores aislados, ejecuta:
docker-compose up -d --build

## 5. URL base de la API
Por defecto, el servidor embebido de Spring Boot se levanta en el puerto 8080:
http://localhost:8080

#### Base paths de los controladores del sistema:

/api/v1/puzzles

/api/v1/usuarios

/api/v1/reto-diario

### 5.1 Endpoints de Puzzles
Listar todos los puzzles (GET)

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
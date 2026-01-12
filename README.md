# Tasca S04.T02.N01 Spring Boot - Nivell 1: API REST con H2 y Arquitectura por Capas

## Descripción
Este proyecto consiste en el desarrollo de una **API REST** para gestionar el stock de una frutería.  
Se aplica una arquitectura multicapa (**Controller, Service, Repository**) y se utiliza una base de datos en memoria **H2** para la persistencia de datos.

El objetivo principal es realizar un mantenimiento de registros de fruta (CRUD) cumpliendo con las siguientes historias de usuario:

- **Registrar una fruta nueva:** Añadir nombre y peso en kilos.
- **Consultar todas las frutas:** Obtener una visión global del stock.
- **Consultar una fruta específica:** Acceder a los detalles mediante su identificador/nombre.
- **Modificar una fruta existente:** Actualizar datos de un producto ya registrado.
- **Eliminar una fruta:** Garantizar que el stock solo contenga información relevante.

### Características principales
- **Persistencia con JPA y H2:** Base de datos relacional en memoria de rápido acceso.
- **Validación de Datos:** Uso de Bean Validation (`@NotBlank`, `@Min`) tanto en la Entidad como en los DTOs.
- **DTO Pattern:** Separación de las entidades de base de datos de los objetos de transferencia de datos.
- **Manejo Global de Excepciones:** Centralización de errores mediante `@RestControllerAdvice`.
- **TDD (Test-Driven Development):** Desarrollo guiado por pruebas unitarias e integración.
- **Dockerizado:** Preparado para entornos de producción mediante multi-stage build.

## Tecnologías Utilizadas
- Java 21 (LTS)
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Lombok
- Maven
- JUnit 5 & Mockito
- Docker

## Estructura del Proyecto
cat.itacademy.s04.t02.n01/
├── controller/
│ └── FruitController.java → Controlador REST
├── entity/
│ └── Fruit.java → Entidad JPA
├── DTO/
│ ├── FruitRequest.java → DTO de entrada
│ ├── FruitResponse.java → DTO de salida
│ └── ErrorResponse.java → Estructura de errores
├── service/
│ ├── FruitService.java → Interfaz de negocio
│ └── FruitServiceImp.java → Implementación de lógica
├── repository/
│ └── FruitRepository.java → Acceso a datos (JPA)
├── exception/
│ ├── FruitExistsException.java → Error 409
│ ├── FruitNotExistsException.java→ Error 404
│ └── GlobalExceptionHandler.java → Gestor de excepciones
└── src/test/
├── FruitServiceTest.java → Tests unitarios (Mockito)
└── FruitControllerTest.java → Tests de integración (MockMvc)

bash
Copiar código

## Instalación y Ejecución

### Requisitos
- Java JDK 21
- Maven 3.x

### Clonar y ejecutar
```
bash
git clone https://github.com/tu-usuario/fruit-api-h2.git
cd fruit-api-h2
mvn spring-boot:run
```
La API estará disponible en: http://localhost:8080/fruits

Acceso a consola H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Endpoints Disponibles
```
1. Registrar Fruta
POST /fruits
Request Body:

json
Copiar código
{
  "name": "Apple",
  "weightInKilos": 10
}
Respuestas:

201 Created: Fruta guardada con éxito.

400 Bad Request: Validación fallida.

409 Conflict: El nombre de la fruta ya existe.

2. Obtener Todas las Frutas
GET /fruits

Respuestas:

200 OK: Retorna lista de frutas (o lista vacía []).

3. Obtener Fruta por Nombre
GET /fruits/{name}

Respuestas:

200 OK: Retorna el objeto fruta.

404 Not Found: Si la fruta no existe.

4. Actualizar Fruta
PUT /fruits/{name}
Request Body:

json
Copiar código
{
  "name": "Banana",
  "weightInKilos": 5
}
Respuestas:

200 OK: Actualización exitosa.

404 Not Found: El nombre original no existe.

5. Eliminar Fruta
DELETE /fruits/{name}

Respuestas:


204 No Content: Eliminación exitosa.

404 Not Found: Fruta no encontrada.

```

## Pruebas Automatizadas (TDD)
El proyecto incluye una suite de pruebas completa:

Tests de Servicio: Validan la lógica de negocio (p. ej., que no se pueda actualizar una fruta con el nombre de otra ya existente).

Tests de Controlador: Validan los códigos de estado HTTP y la correcta serialización de JSON utilizando MockMvc.

Para ejecutar los tests:

```
Copiar código
mvn test
Dockerización (Multi-stage Build)
```
El proyecto incluye un Dockerfile optimizado:

Build Stage: Utiliza una imagen de Maven para compilar y empaquetar el .jar.

Final Stage: Copia solo el ejecutable a una imagen ligera de JRE para reducir el tamaño y mejorar la seguridad en producción.

## Notas de Implementación
Se ha implementado una validación personalizada en la capa de servicio para asegurar que los nombres de las frutas sean únicos, lanzando una FruitExistsException en caso de duplicidad.

El uso de Records para los DTOs garantiza la inmutabilidad y un código más limpio.

Desarrollado como parte del itinerario de IT Academy. 🚀
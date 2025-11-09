# Almacén de Películas - Microservicio de Catálogo

## Descripción General

Este proyecto implementa el microservicio de **Catálogo de Películas** como parte de una arquitectura de microservicios para una aplicación de venta de películas online. El sistema permite gestionar y consultar el catálogo de películas disponibles para la venta.

## Tecnologías Utilizadas

- **Java 25** con paradigma orientado a objetos
- **Spring Boot 3.5.6** - Framework web y configuración
- **Spring Data JPA** - Persistencia de datos
- **Hibernate 7.0.7** - ORM
- **MySQL/MariaDB** - Base de datos en producción
- **H2 Database** - Base de datos en memoria para tests
- **Maven** - Gestión de dependencias y build
- **Lombok** - Reducción de código boilerplate
- **JUnit 5** - Testing automatizado

## Arquitectura del Proyecto

### Estructura de Carpetas
```
src/
├── main/java/ar/edu/unrn/catalogo/
│   ├── model/          # Modelo de dominio (reglas de negocio)
│   ├── repository/     # Acceso a datos
│   ├── service/        # Lógica de aplicación
│   ├── web/            # Controllers y DTOs
│   └── utils/          # Utilidades (configuración JPA)
└── test/java/ar/edu/unrn/catalogo/
    ├── model/          # Tests unitarios del modelo
    ├── util/           # Tests de utilidades
    └── web/            # Tests de integración
```

### Principios de Diseño

- **Modelo de Dominio Rico**: Todas las reglas de negocio están en el modelo
- **Tell Don't Ask**: Los objetos encapsulan su comportamiento
- **Objetos Inmutables**: Sin getters/setters, construcción por constructor
- **Validaciones en Constructor**: Objetos siempre válidos y completos
- **Encapsulación**: Listas devueltas como solo lectura

## Modelo de Dominio

### Entidades Principales

#### Película
```java
public class Pelicula {
    private Long id;
    private String titulo;
    private int anio;
    private double precio;
    private Director director;
    private List<Actor> actores;
    private String condicion;    // "nuevo" o "usado"
    private String formato;      // "DVD", "Blu-ray", etc.
    private String genero;
    private String sinopsis;
    private String imagen;
}
```

#### Director
```java
public class Director {
    private Long id;
    private String nombre;
}
```

#### Actor
```java
public class Actor {
    private Long id;
    private String nombre;
}
```

## API REST - Endpoints

### Base URL
```
http://localhost:8080/catalogo
```

### 1. Listar Catálogo de Películas

**GET** `/catalogo`

Devuelve el listado completo de películas ordenadas de la más nueva a la más vieja (por año de lanzamiento).

**Respuesta:**
```json
[
  {
    "id": 1,
    "titulo": "Inception",
    "anio": 2010,
    "precio": 25.99,
    "director": "Christopher Nolan",
    "actores": ["Leonardo DiCaprio", "Marion Cotillard", "Tom Hardy"],
    "imagen": "https://example.com/inception.jpg"
  },
  {
    "id": 2,
    "titulo": "The Dark Knight",
    "anio": 2008,
    "precio": 22.50,
    "director": "Christopher Nolan",
    "actores": ["Christian Bale", "Heath Ledger", "Aaron Eckhart"],
    "imagen": "https://example.com/dark-knight.jpg"
  }
]
```

**Códigos de Estado:**
- `200 OK` - Éxito

---

### 2. Obtener Detalle de Película

**GET** `/catalogo/{id}`

Devuelve todos los detalles de una película específica.

**Parámetros:**
- `id` (Long) - ID de la película

**Ejemplo:** `GET /catalogo/1`

**Respuesta:**
```json
{
  "id": 1,
  "titulo": "Inception",
  "anio": 2010,
  "precio": 25.99,
  "director": "Christopher Nolan",
  "actores": ["Leonardo DiCaprio", "Marion Cotillard", "Tom Hardy"],
  "condicion": "nuevo",
  "formato": "Blu-ray",
  "genero": "Ciencia Ficción",
  "sinopsis": "Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños...",
  "imagen": "https://example.com/inception-large.jpg"
}
```

**Códigos de Estado:**
- `200 OK` - Película encontrada
- `500 Internal Server Error` - Película no encontrada

**Error Response:**
```json
{
  "message": "Película no encontrada"
}
```

## DTOs (Data Transfer Objects)

### PeliculaInfoCatalogo
Utilizado para el listado del catálogo. Contiene solo la información necesaria para mostrar en el listado principal.

### PeliculaDetalle
Utilizado para mostrar el detalle completo de una película. Incluye todos los campos disponibles.

## Configuración de Base de Datos

### Producción (MySQL/MariaDB)
El proyecto está configurado para usar MySQL/MariaDB en producción a través de la clase `EmfBuilder`.

### Testing (H2 en Memoria)
Para los tests de integración se utiliza H2 Database en memoria, lo que permite ejecutar tests rápidos y aislados.

## Testing

### Estrategia de Testing
- **Tests Unitarios**: Modelo de dominio y validaciones
- **Tests de Integración**: Servicios con base de datos
- **Cobertura**: >= 90%

### Convenciones de Naming
- Métodos: `cuestionATestear_resultadoEsperado`
- DisplayName: Descripción en lenguaje natural

### Estructura de Tests
```java
@Test
@DisplayName("Descripción del test")
void nombreDelMetodo() {
    // Setup: Preparar el escenario
    // Ejercitación: Ejecutar la acción a probar
    // Verificación: Verificar el resultado esperado
}
```

## Instalación y Ejecución

### Prerrequisitos
- Java 25
- Maven 3.6+
- MySQL/MariaDB (para producción)

### Pasos para Ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd almacen-peliculas
   ```

2. **Configurar base de datos**
   - Crear base de datos MySQL/MariaDB
   - Ajustar configuración en `EmfBuilder.java`

3. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

4. **Ejecutar tests**
   ```bash
   mvn test
   ```

5. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

La aplicación estará disponible en `http://localhost:8080`

## CORS

El servicio está configurado para aceptar peticiones desde `http://localhost:5173` (frontend en Vite/React).

## Roadmap

Este microservicio forma parte de una arquitectura más amplia que incluirá:

- **Carrito de Compras**: Gestión del carrito y proceso de compra
- **Usuarios y Autenticación**: Gestión de usuarios y autenticación
- **Votaciones y Comentarios**: Sistema de rating y comentarios
- **Descuentos**: Gestión de ofertas y descuentos
- **Notificaciones**: Envío de emails y notificaciones
- **Administración**: Panel de administración

## Contribución

El proyecto sigue las convenciones establecidas en las instrucciones de diseño:
- Sin getters/setters en el modelo de dominio
- Validaciones en constructores
- Uso de RuntimeException para errores
- Principio "Tell Don't Ask"
- Tests exhaustivos con alta cobertura

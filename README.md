# Order Management System API

Backend desarrollado como parte de un reto técnico para implementar un sistema de gestión de clientes y pedidos.

La solución permite administrar clientes, consultar sus pedidos desde servicios externos, enriquecer los pedidos con información de productos y realizar búsquedas flexibles por diferentes criterios.

El proyecto fue desarrollado utilizando **Java 21, Spring Boot y MongoDB**, siguiendo una arquitectura **Hexagonal (Ports & Adapters)** para mantener separadas las reglas de negocio de los detalles de infraestructura.

---

## Funcionalidades

### Gestión de clientes

La API permite:

- Crear clientes.
- Consultar clientes mediante `userId`.
- Actualizar información del cliente.
- Eliminar clientes.
- Consultar los pedidos asociados a un cliente.
- Enriquecer dinámicamente la información del cliente con sus pedidos.

### Integración de pedidos

Los pedidos son obtenidos desde un servicio externo.

La aplicación:

1. Consulta los pedidos.
2. Filtra los pedidos correspondientes al `userId`.
3. Obtiene las referencias de pedido mediante `orderRef`.
4. Relaciona los pedidos con el cliente.

### Integración de productos

Cada pedido contiene identificadores de productos.

La aplicación consulta el servicio externo de productos y combina ambas fuentes de información para devolver el detalle completo del pedido.

La estrategia de resolución es:

1. Buscar el producto mediante `itemId`.
2. Si no existe coincidencia exacta, obtener el `skuId` desde el identificador.
3. Buscar el producto mediante `skuId`.
4. Si el servicio externo no contiene el producto, continuar procesando el pedido con una lista de productos vacía.

Esta estrategia permite manejar inconsistencias entre las fuentes externas sin provocar un error general en la consulta.

### Búsqueda flexible de pedidos

La API permite buscar pedidos utilizando texto libre sobre:

- `orderRef`
- `orderStatus`
- `storeName`

La búsqueda soporta:

- Mayúsculas y minúsculas.
- Texto parcial.
- Palabras con o sin acentos.
- Diferencias de puntuación y espacios.
- Errores menores de escritura.

Ejemplos:

```text
santa fe
SANTA FE
galerias serdan
galerías serdán
monterey
3010091676
```

---

# Arquitectura

La aplicación utiliza **Hexagonal Architecture / Ports & Adapters**.

El dominio y los casos de uso no dependen directamente de MongoDB, HTTP ni de los servicios externos.

```text
                     ┌──────────────────────┐
                     │      REST Client     │
                     │ Swagger / Postman    │
                     └──────────┬───────────┘
                                │
                                ▼
                  ┌─────────────────────────┐
                  │     REST Adapters       │
                  │    infrastructure       │
                  │       adapter/in        │
                  └───────────┬─────────────┘
                              │
                       Input Ports
                              │
                              ▼
                  ┌─────────────────────────┐
                  │       Use Cases         │
                  │      application        │
                  └───────────┬─────────────┘
                              │
                       Output Ports
                         ┌────┴────┐
                         │         │
                         ▼         ▼
                  ┌───────────┐ ┌──────────────┐
                  │ MongoDB   │ │ External APIs│
                  │ Adapter   │ │   Adapters   │
                  └─────┬─────┘ └──────┬───────┘
                        │              │
                        ▼              ▼
                  ┌───────────┐   ┌───────────┐
                  │ MongoDB   │   │ /pedidos  │
                  │  Atlas    │   │ /items    │
                  └───────────┘   └───────────┘
```

## Principios utilizados

- Dependency Inversion Principle.
- Separation of Concerns.
- Ports & Adapters.
- Domain independent from infrastructure.
- DTO separation.
- Centralized exception handling.
- Bean Validation.
- External configuration through environment variables.

---

# Estructura del proyecto

```text
src/main/java/com/ecomeerce/validation
│
├── domain
│   ├── model
│   └── exception
│
├── application
│   ├── port
│   │   ├── in
│   │   └── out
│   ├── usecase
│   └── util
│
├── infrastructure
│   ├── adapter
│   │   ├── in
│   │   │   └── rest
│   │   │       ├── dto
│   │   │       └── mapper
│   │   └── out
│   │       ├── mongodb
│   │       └── external
│   └── config
│
└── shared
    └── exception
```

---

# Tecnologías

- Java 21
- Spring Boot
- Spring Data MongoDB
- MongoDB Atlas
- Spring RestClient
- Bean Validation
- Springdoc OpenAPI / Swagger UI
- JUnit 5
- Mockito
- Maven
- Checkstyle
- Git / GitHub

---

# Requisitos

Para ejecutar el proyecto se requiere:

- JDK 21
- Maven 3.9+
- Acceso a MongoDB Atlas
- Git

Verificar Java:

```bash
java -version
```

Verificar Maven:

```bash
mvn -version
```

---

# Configuración

La conexión con MongoDB se administra mediante una variable de entorno para evitar almacenar credenciales dentro del repositorio.

Variable requerida:

```text
MONGODB_URI
```

Ejemplo:

```text
MONGODB_URI=mongodb+srv://<user>:<password>@<cluster>/<database>
```

> No se deben almacenar credenciales reales dentro de `application.properties` ni subirlas al repositorio.

La configuración principal utiliza:

```properties
spring.data.mongodb.uri=${MONGODB_URI}
server.port=${SERVER_PORT:8088}
```

Los endpoints de servicios externos también pueden configurarse mediante variables de entorno:

```text
ORDERS_API_URL
ITEMS_API_URL
```

---

# Ejecución

Clonar el repositorio y acceder al proyecto:

```bash
git clone <repository-url>
cd ecommerce-validation
```

Configurar `MONGODB_URI`.

Posteriormente ejecutar:

```bash
mvn clean verify
```

Si las validaciones son correctas se obtiene:

```text
BUILD SUCCESS
```

Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

Por defecto la aplicación inicia en:

```text
http://localhost:8088
```

---

# Swagger / OpenAPI

La documentación interactiva de la API está disponible mediante Swagger UI.

Con la aplicación ejecutándose:

```text
http://localhost:8088/swagger-ui/index.html
```

Swagger permite consultar los contratos y ejecutar directamente las operaciones REST.

---

# Endpoints

## Customers

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/customers` | Crear cliente |
| GET | `/api/v1/customers/{userId}` | Consultar cliente |
| PUT | `/api/v1/customers/{userId}` | Actualizar cliente |
| DELETE | `/api/v1/customers/{userId}` | Eliminar cliente |
| GET | `/api/v1/customers/{userId}/orders` | Consultar pedidos asociados |

## Orders

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/v1/orders/customer/{userId}` | Consultar pedidos enriquecidos |
| GET | `/api/v1/orders/search?q={query}` | Búsqueda flexible de pedidos |

---

# Ejemplo de cliente

Request:

```json
{
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "firstName": "Customer",
  "paternalLastName": "Orders",
  "maternalLastName": "Demo",
  "email": "customer.orders@example.com",
  "shippingAddress": "Ciudad de Mexico"
}
```

Al consultar posteriormente el cliente, la aplicación obtiene dinámicamente las referencias de pedidos asociadas al `userId`.

Ejemplo:

```json
{
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "firstName": "Customer",
  "paternalLastName": "Orders",
  "maternalLastName": "Demo",
  "email": "customer.orders@example.com",
  "orders": [
    "3010091676",
    "30100916760987",
    "632005897"
  ]
}
```

---

# Manejo de errores

La aplicación utiliza manejo centralizado de excepciones mediante `@RestControllerAdvice`.

Entre los escenarios controlados se encuentran:

- Cliente inexistente.
- Cliente duplicado.
- Errores de validación.
- Datos de entrada inválidos.

Los errores utilizan una estructura consistente:

```json
{
  "timestamp": "...",
  "status": 400,
  "code": "...",
  "message": "...",
  "errors": {}
}
```

---

# Validaciones

Los DTOs de entrada utilizan Bean Validation.

Entre las validaciones implementadas:

- Campos obligatorios.
- Formato válido de email.
- Tamaño máximo de campos.
- Validación de dirección de envío.

---

# Pruebas automatizadas

El proyecto contiene pruebas unitarias y de contexto utilizando:

- JUnit 5
- Mockito
- Spring Boot Test

Actualmente la suite contiene:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
```

Las pruebas cubren, entre otros escenarios:

- Creación de clientes.
- Clientes duplicados.
- Cliente inexistente.
- Asociación cliente/pedidos.
- Enriquecimiento exacto mediante `itemId`.
- Fallback mediante `skuId`.
- Productos inexistentes.
- Búsqueda sin distinguir mayúsculas/minúsculas.
- Búsqueda ignorando acentos.
- Búsqueda parcial.
- Tolerancia a errores menores de escritura.

Ejecutar pruebas:

```bash
mvn clean test
```

Validar el proyecto completo:

```bash
mvn clean verify
```

---

# Checkstyle

El proyecto incluye Maven Checkstyle Plugin para análisis estático y control de estándares de código.

La configuración se encuentra en:

```text
config/checkstyle/checkstyle.xml
```

Puede ejecutarse mediante:

```bash
mvn checkstyle:check
```

> Checkstyle se mantiene desacoplado del lifecycle principal de Maven para permitir ejecutar el análisis estático de forma independiente del proceso de compilación y pruebas.

---

# Decisiones de diseño

## Arquitectura Hexagonal

Se eligió arquitectura hexagonal para evitar acoplar las reglas de negocio con tecnologías específicas como MongoDB o servicios HTTP.

Los casos de uso dependen de interfaces (ports) y las implementaciones técnicas se encuentran en adapters.

## Pedidos dinámicos

Las referencias de pedidos no se duplican como fuente definitiva dentro del documento del cliente.

Cuando se consulta un cliente, los pedidos asociados se obtienen desde el servicio de pedidos utilizando su `userId`.

Esto evita información desactualizada entre MongoDB y la fuente externa.

## Enriquecimiento de productos

Los datos externos presentan casos donde el identificador de producto incluido en un pedido no coincide exactamente con los identificadores disponibles en el servicio de productos.

Para manejar esta situación se implementó:

```text
itemId exacto
      │
      ├── encontrado → utilizar producto
      │
      └── no encontrado
              │
              ▼
         extraer skuId
              │
              ▼
         buscar por SKU
              │
              ├── encontrado → utilizar producto
              │
              └── no encontrado → continuar sin producto
```

Esto evita que una inconsistencia en un producto provoque el fallo de todo el pedido.

---

# Consideraciones sobre datos externos

La aplicación no modifica la información recibida desde los servicios externos.

Cuando un producto no puede resolverse mediante `itemId` ni mediante `skuId`, el pedido continúa siendo válido y se devuelve:

```json
"items": []
```

Esto permite mantener disponible la información del pedido aun cuando la fuente de productos no contenga una coincidencia.

---

# Flujo de desarrollo

El desarrollo se realizó utilizando Git mediante ramas feature y Pull Requests.

Ejemplos de funcionalidades desarrolladas de manera independiente:

```text
feature/hexagonal-architecture
feature/orders-integration
feature/items-integration
feature/quality-documentation
```

Este flujo permite mantener cambios pequeños, revisables y con historial claro de evolución del proyecto.

---

# Posibles mejoras

En una evolución productiva podrían incorporarse:

- Retry y Circuit Breaker para servicios externos.
- Caché para consultas frecuentes.
- Observabilidad y métricas.
- Logging estructurado.
- CI/CD.
- Containerización.
- Gestión centralizada de secretos.
- Despliegue en cloud.

Estas funcionalidades se consideran mejoras futuras y no forman parte de la implementación actual.

---

# Estado del proyecto

La implementación incluye:

- Customer CRUD.
- MongoDB.
- Integración de pedidos.
- Integración de productos.
- Enriquecimiento de pedidos.
- Manejo de inconsistencias entre fuentes externas.
- Búsqueda flexible.
- Validaciones.
- Manejo global de errores.
- Swagger / OpenAPI.
- Pruebas automatizadas.
- Checkstyle.
- Arquitectura Hexagonal.
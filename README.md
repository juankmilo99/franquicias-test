# Franquicias API

API backend reactiva para gestión de franquicias, sucursales y productos.

## Stack
- Java 21
- Spring Boot WebFlux
- R2DBC + PostgreSQL
- Maven
- JUnit + Mockito

## Estructura (Clean Architecture)
- `domain`: entidades y reglas de negocio
- `application`: casos de uso y puertos
- `infrastructure`: adaptadores y repositorios
- `entrypoints`: controladores, DTOs y handler de errores

## Configuración
La conexión a PostgreSQL se configura con variables de entorno:
- `SPRING_R2DBC_URL`
- `SPRING_R2DBC_USERNAME`
- `SPRING_R2DBC_PASSWORD`

En Render, agrega esas variables en el servicio.

## Ejecutar
```bash
./mvnw spring-boot:run
```
En Windows:
```bash
mvnw.cmd spring-boot:run
```

## Probar tests
```bash
./mvnw test
```

## Endpoints principales
- `POST /api/franchises` → crear franquicia
- `POST /api/franchises/{franchiseId}/branches` → agregar sucursal
- `POST /api/branches/{branchId}/products` → agregar producto
- `DELETE /api/branches/{branchId}/products/{productId}` → eliminar producto
- `PATCH /api/products/{productId}/stock` → actualizar stock
- `GET /api/franchises/{franchiseId}/branches/top-stock-product` → producto con mayor stock por sucursal

### Extras
- `PATCH /api/franchises/{franchiseId}/name`
- `PATCH /api/branches/{branchId}/name`
- `PATCH /api/products/{productId}/name`

## Swagger (probar en navegador)
Con la app levantada, abre:
- `http://localhost:8080/swagger`
- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/swagger-ui.html`

OpenAPI JSON:
- `http://localhost:8080/v3/api-docs`

## Docker
Construir imagen:
```bash
docker build -t franquicias-api .
```
Ejecutar contenedor:
```bash
docker run -p 8080:8080 franquicias-api
```

## Terraform (ejemplo básico Neon)
Revisar carpeta:
- `infra/terraform`

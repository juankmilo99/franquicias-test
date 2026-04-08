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
Produccion (Render):
- `https://franquicias-test.onrender.com/swagger-ui/index.html#/`

Local:
- `http://localhost:8080/swagger`
- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/swagger-ui.html`

OpenAPI JSON:
- `http://localhost:8080/v3/api-docs`
- `https://franquicias-test.onrender.com/v3/api-docs`

## Docker
Construir imagen:
```bash
docker build -t franquicias-api .
```
Ejecutar contenedor:
```bash
docker run -p 8080:8080 franquicias-api
```



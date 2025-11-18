# 🛰️ Starlink Manager

Sistema profesional de administración de equipos Starlink con gestión integral de pagos.

## 🚀 Inicio Rápido

```bash
cd starlink-manager
mvn clean install
mvn spring-boot:run
```

## 📡 Acceso

- **API**: http://localhost:8080/api
- **H2 Console**: http://localhost:8080/h2-console

## 📋 Características

- CRUD de equipos
- Gestión de pagos
- Estadísticas
- Validaciones empresariales
- Base de datos H2

## 📚 Endpoints

### Equipos
- POST /api/equipos - Crear
- GET /api/equipos - Listar
- GET /api/equipos/{id} - Obtener

### Pagos
- POST /api/pagos/equipo/{id}
- GET /api/pagos/equipo/{id}

### Estadísticas
- GET /api/estadisticas
# starlink-manager

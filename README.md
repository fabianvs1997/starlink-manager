<div align="center">

# 🛰️ Starlink Manager

### Sistema completo de gestión para clientes de internet satelital Starlink

[![Version](https://img.shields.io/badge/version-2.0.0-blue.svg)](https://github.com/fabianvs1997/starlink-manager)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![AI Powered](https://img.shields.io/badge/AI-Powered-purple.svg)](https://perplexity.ai)

<p align="center">
  <img src="https://img.shields.io/badge/Estado-En%20Desarrollo-yellow.svg" alt="Estado">
  <img src="https://img.shields.io/badge/Contribuciones-Bienvenidas-brightgreen.svg" alt="Contribuciones">
</p>

---

### 🤖 **Este proyecto fue creado 100% con Inteligencia Artificial**

Desarrollado completamente usando **Perplexity AI** - Demostrando el poder de la IA en el desarrollo de software moderno.

---

[🚀 Demo](#-demo) •
[📋 Características](#-características) •
[🛠️ Tecnologías](#️-tecnologías) •
[📦 Instalación](#-instalación) •
[📖 Documentación](#-documentación) •
[🤝 Contribuir](#-contribuir)

</div>

---

## 📋 Características

<table>
  <tr>
    <td width="50%">

### 📊 **Dashboard Inteligente**
- ✅ Estadísticas en tiempo real
- 📈 Gráficos interactivos con Chart.js
- ⚡ Actualizaciones automáticas
- 🎯 Indicadores de rendimiento (KPIs)
- 🔔 Alertas de vencimientos

    </td>
    <td width="50%">

### 📱 **Gestión de Equipos**
- ➕ CRUD completo de equipos
- 🏷️ Categorización avanzada
- 🔍 Búsqueda y filtros inteligentes
- 📊 Estados en tiempo real
- 🔢 Tracking de números de serie

    </td>
  </tr>
  <tr>
    <td width="50%">

### 💰 **Sistema de Pagos**
- 💳 Múltiples métodos de pago
- 📅 **Actualización automática de vencimientos**
- 💵 Soporte para pagos adelantados
- 📊 Historial completo de transacciones
- 📈 Desglose por método

    </td>
    <td width="50%">

### 🛰️ **Monitor Starlink**
- 📡 Estadísticas en tiempo real
- ⚡ Latencia y velocidad
- 📶 Calidad de señal
- ⏱️ Uptime del terminal
- ⚠️ Alertas de obstrucciones

    </td>
  </tr>
</table>

---

## 🎯 ¿Por qué Starlink Manager?

<div align="center">

| 🚀 **Rápido** | 💪 **Robusto** | 🎨 **Moderno** | 🤖 **Inteligente** |
|:-------------:|:--------------:|:--------------:|:------------------:|
| Interfaz ágil y responsiva | Arquitectura escalable | Diseño clean y profesional | Creado con IA de última generación |
| Carga instantánea | Spring Boot + MySQL | CSS3 y ES6 Modules | Optimizado con machine learning |

</div>

---

## 🛠️ Tecnologías

<div align="center">

### Backend
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

### Frontend
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-ES6+-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Chart.js](https://img.shields.io/badge/Chart.js-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)

### Herramientas
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![VS Code](https://img.shields.io/badge/VS%20Code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

</div>

---

## 📦 Instalación Rápida

### 📋 Requisitos Previos

✅ Java JDK 17+
✅ Maven 3.8+
✅ MySQL 8.0+
✅ Git
✅ Un navegador moderno (Chrome, Firefox, Edge)



### ⚡ Instalación en 5 pasos

<details>
<summary><b>1️⃣ Clonar el Repositorio</b></summary>

git clone https://github.com/fabianvs1997/starlink-manager.git
cd starlink-manager

</details>

<details>
<summary><b>2️⃣ Configurar Base de Datos</b></summary>

-- Crear base de datos
CREATE DATABASE starlink_manager;

-- Usar base de datos
USE starlink_manager;

-- Importar schema (opcional)
SOURCE database/schema.sql;


</details>

<details>
<summary><b>3️⃣ Configurar Backend</b></summary>

Edita `src/main/resources/application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/starlink_manager
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
server.port=8081


</details>

<details>
<summary><b>4️⃣ Ejecutar Backend</b></summary>

Compilar y ejecutar
mvn spring-boot:run

Backend corriendo en: http://localhost:8081
text
</details>

<details>
<summary><b>5️⃣ Ejecutar Frontend</b></summary>

**Opción A: Live Server (VS Code)**
1. Instala extensión "Live Server"
2. Click derecho en `index.html`
3. Selecciona "Open with Live Server"

**Opción B: Python**
python -m http.server 8000

Abre: http://localhost:8000


**Opción C: Node.js**
npm install -g http-server
http-server -p 8000


</details>

---

## 📖 Estructura del Proyecto

starlink-manager/
│
├── 📂 src/main/java/com/starlink/
│ ├── 🎮 controller/ # Controladores REST API
│ ├── 🧠 service/ # Lógica de negocio
│ ├── 💾 repository/ # Acceso a datos JPA
│ ├── 📦 entity/ # Entidades de base de datos
│ ├── 📋 dto/ # Data Transfer Objects
│ ├── 🔄 mapper/ # Conversión DTO ↔ Entity
│ └── ⚙️ config/ # Configuración Spring
│
├── 📂 frontend/
│ ├── 🏠 index.html # HTML principal
│ └── 📂 assets/
│ ├── 🎨 css/ # Estilos (modular)
│ └── 📜 js/
│ ├── app.js # Router y main
│ ├── 🔧 core/ # Núcleo (API, config, utils)
│ ├── 🧩 components/ # Componentes (toast, modal)
│ └── 📦 modules/ # Módulos (dashboard, equipos, pagos)
│
├── 📂 database/
│ └── schema.sql # Schema de base de datos
│
├── 📄 README.md # Este archivo
├── 📄 .gitignore # Archivos ignorados
├── 📄 pom.xml # Configuración Maven
└── 📄 LICENSE # Licencia MIT



---

## 🔌 API REST Endpoints

### 📱 Equipos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/equipos` | Obtener todos los equipos |
| `GET` | `/api/equipos/{id}` | Obtener equipo por ID |
| `POST` | `/api/equipos` | Crear nuevo equipo |
| `PUT` | `/api/equipos/{id}` | Actualizar equipo |
| `DELETE` | `/api/equipos/{id}` | Eliminar equipo |

### 💰 Pagos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/pagos` | Obtener todos los pagos |
| `GET` | `/api/pagos/equipo/{equipoId}` | Pagos de un equipo |
| `POST` | `/api/pagos/equipo/{equipoId}` | **Registrar pago** ⚡ |
| `DELETE` | `/api/pagos/{id}` | Eliminar pago |

### 📊 Estadísticas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/estadisticas` | Dashboard completo |

### 🛰️ Starlink

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/starlink/stats` | Estado del terminal |

### 📝 Ejemplo de Request

POST http://localhost:8081/api/pagos/equipo/1
Content-Type: application/json

{
"monto": 2055.00,
"fechaPago": "2025-12-10",
"metodo": "Transferencia",
"descripcion": "Pago mensual diciembre"
}



**Respuesta:**
{
"success": true,
"message": "✅ Pago registrado - Vencimiento actualizado automáticamente",
"data": {
"id": 15,
"monto": 2055.00,
"fechaPago": "2025-12-10",
"nuevaFechaVencimiento": "2026-01-10"
}
}



---

## 🎨 Características Destacadas

### ⚡ Actualización Automática de Vencimientos

El sistema **actualiza automáticamente** la fecha de vencimiento al registrar un pago:

💡 Caso 1: Pago a Tiempo
Vencimiento actual: 2025-12-15
Pago realizado: 2025-12-10 ($2,055)
➡️ Nuevo vencimiento: 2026-01-15 ✅

💡 Caso 2: Pago Adelantado (3 meses)
Vencimiento actual: 2025-12-15
Pago realizado: 2025-12-01 ($6,165)
➡️ Nuevo vencimiento: 2026-03-15 ✅

💡 Caso 3: Pago Tardío
Vencimiento actual: 2025-12-15
Pago realizado: 2025-12-25 ($2,055)
➡️ Nuevo vencimiento: 2026-01-25 ✅

---

## 🗺️ Roadmap

### ✅ Versión 2.0 (Actual)
- [x] ✨ Sistema modular completo
- [x] 🔄 Actualización automática de vencimientos
- [x] 💵 Soporte para pagos adelantados
- [x] 📊 Dashboard con estadísticas en tiempo real
- [x] 📈 Gráficos interactivos
- [x] 🔔 Sistema de notificaciones
- [x] 🛰️ Monitor de Starlink

### 🚧 Versión 2.1 (Próxima - Q1 2026)
- [ ] 🔐 Autenticación JWT completa
- [ ] 👥 Sistema de roles (Admin, Usuario)
- [ ] 📥 Exportación de reportes (PDF, Excel)
- [ ] 📧 Notificaciones por email
- [ ] 💬 Integración con WhatsApp Business API
- [ ] 🔄 Backup automático de base de datos

### 🔮 Versión 3.0 (Futuro - Q3 2026)
- [ ] 📱 App móvil (React Native)
- [ ] 💳 Integración con pasarelas de pago
- [ ] 🎫 Sistema de tickets/soporte
- [ ] 🏢 Multi-tenant (múltiples empresas)
- [ ] 🌐 Portal para clientes
- [ ] 🧾 Facturación electrónica (CFDI México)

---

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! 🎉

### Cómo Contribuir

1. **Fork** el proyecto
2. Crea una **rama** para tu feature
   git checkout -b feature/nueva-funcionalidad

3. **Commit** tus cambios
   git commit -m 'feat: agregar nueva funcionalidad'


4. **Push** a tu rama
   git push origin feature/nueva-funcionalidad

5. Abre un **Pull Request**

### 📝 Convención de Commits

Usamos [Conventional Commits](https://www.conventionalcommits.org/):

feat: nueva funcionalidad
fix: corrección de bug
docs: cambios en documentación
style: formato de código
refactor: refactorización
test: agregar tests
chore: tareas de mantenimiento


---

## 🤖 Creado con Inteligencia Artificial

<div align="center">

### 💜 **Proyecto 100% desarrollado con IA**

Este proyecto fue creado completamente usando **Perplexity AI**, demostrando el potencial de la inteligencia artificial en el desarrollo de software moderno.

#### 🧠 Tecnologías IA Utilizadas:
- **Perplexity AI** - Arquitectura del sistema
- **Perplexity AI** - Generación de código (Backend + Frontend)
- **Perplexity AI** - Diseño de base de datos
- **Perplexity AI** - Documentación técnica
- **Perplexity AI** - Debugging y optimización

<p align="center">
  <img src="https://img.shields.io/badge/Powered%20by-Perplexity%20AI-purple?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTEyIDJMMyA3VjE3TDEyIDIyTDIxIDE3VjdMMTIgMloiIGZpbGw9IndoaXRlIi8+Cjwvc3ZnPg==" alt="AI Powered">
</p>

#### 📚 Inspiración:
Este proyecto demuestra que con las herramientas de IA adecuadas, es posible crear sistemas completos y profesionales con:
- ✅ Arquitectura escalable
- ✅ Código limpio y mantenible
- ✅ Mejores prácticas de la industria
- ✅ Documentación exhaustiva

</div>

---

## 📞 Contacto y Soporte

<div align="center">

### 👨‍💻 Autor

**Fabian VS**

[![Email](https://img.shields.io/badge/Email-fabian.intel1997%40outlook.com-blue?style=for-the-badge&logo=microsoft-outlook)](mailto:fabian.intel1997@outlook.com)
[![GitHub](https://img.shields.io/badge/GitHub-%40fabianvs1997-black?style=for-the-badge&logo=github)](https://github.com/fabianvs1997)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Conectar-blue?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/fabianvs1997)

---

### 💬 ¿Necesitas ayuda?

- 📧 **Email:** fabian.intel1997@outlook.com
- 💬 **Issues:** [GitHub Issues](https://github.com/fabianvs1997/starlink-manager/issues)
- 📖 **Wiki:** [Documentación completa](https://github.com/fabianvs1997/starlink-manager/wiki)
- 🐛 **Bug Reports:** [Reportar bug](https://github.com/fabianvs1997/starlink-manager/issues/new?template=bug_report.md)
- ✨ **Feature Requests:** [Sugerir feature](https://github.com/fabianvs1997/starlink-manager/issues/new?template=feature_request.md)

</div>

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

MIT License

Copyright (c) 2025 Fabian VS

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...



---

## 🙏 Agradecimientos

<div align="center">

Un agradecimiento especial a:

- 🤖 [**Perplexity AI**](https://perplexity.ai) - Por hacer posible este proyecto
- 🍃 [**Spring Boot**](https://spring.io) - Framework backend
- 📊 [**Chart.js**](https://www.chartjs.org/) - Gráficos interactivos
- 🛰️ [**Starlink**](https://www.starlink.com/) - Internet satelital

</div>

---

## ⭐ ¿Te gustó el proyecto?

<div align="center">

Si este proyecto te fue útil, considera darle una ⭐ en GitHub.

[![GitHub stars](https://img.shields.io/github/stars/fabianvs1997/starlink-manager?style=social)](https://github.com/fabianvs1997/starlink-manager/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/fabianvs1997/starlink-manager?style=social)](https://github.com/fabianvs1997/starlink-manager/network/members)
[![GitHub watchers](https://img.shields.io/github/watchers/fabianvs1997/starlink-manager?style=social)](https://github.com/fabianvs1997/starlink-manager/watchers)

### 📢 Comparte este proyecto

[![Twitter](https://img.shields.io/twitter/url?style=social&url=https%3A%2F%2Fgithub.com%2Ffabianvs1997%2Fstarlink-manager)](https://twitter.com/intent/tweet?text=Check%20out%20this%20amazing%20Starlink%20Manager%20project!&url=https%3A%2F%2Fgithub.com%2Ffabianvs1997%2Fstarlink-manager)

---

<p align="center">
  <b>Hecho con ❤️ en México 🇲🇽</b>
</p>

<p align="center">
  <i>Desarrollado completamente con Inteligencia Artificial 🤖✨</i>
</p>

<p align="center">
  <sub>2025 - Starlink Manager v2.0</sub>
</p>

</div>
# Merklys API - Sistema Web Integral de Gestión Comercial y Ventas

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-brightgreen?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven)

## 📝 Sobre Merklys
**Merklys** es un negocio familiar peruano emergente (desde 2020) dedicado a la fabricación y venta de ropa y accesorios para damas y niñas (desde los 8 años). Con presencia física en la galería “Luna Pizarro” (La Victoria) y un centro de operaciones en Santa Anita, la empresa se destaca por ofrecer tendencias de moda con calidad y accesibilidad a nivel nacional, atendiendo tanto al mercado mayorista como minorista.

## 💻 Descripción del Sistema
Backend para la gestión de:
* Productos
* Inventario
* Clientes
* Ventas
* Usuarios y roles

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java 25
* **Framework:** Spring Framework 7 / Spring Boot 4
* **Base de Datos:** PostgreSQL 15
* **Persistencia:** Spring Data JPA
* **Documentación:** OpenAPI 3 / Swagger UI
* **Mapeo de Datos:** MapStruct
* **Gestor de Dependencias:** Maven

## 🔧 Configuración del Entorno y Ejecución

### Pre-requisitos
* **OpenJDK 25** instalado.
* **Docker** (para la instancia de base de datos).

### 1. Preparación de la Base de Datos
El sistema requiere una base de datos en PostgreSQL.

### 2. Ejecución de la Aplicación
```bash
./mvnw spring-boot:run
```

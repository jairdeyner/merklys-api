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

<!-- Documentar sobre los perfiles creados -->
### 2. Descargar o clonar el proyecto
```bash
git clone <url-del-repositorio>
```

### 3. Configuración de las variables de entorno

Se tiene un archivo `.env.example` en la raíz del proyecto que contiene las variables de entorno que se deben configurar.

Este proyecto ha sido desarrollado en Google Antigravity donde nos ofrece poder configurar variables de entorno en archivos .env y poder usar dichas variables en la ejecución de la aplicación (Lo mismo en Vscode), a continuación se muestra los pasos para configurarlo en Google Antigravity.

* Crear los archivos `.env.dev` y `.env.prod` en la raíz del proyecto con el contenido que se encuentra en el archivo `.env.example`.

* Configurar el archivo `launch.json` ubicados en la caperta `.vscode` para poder ejecutar la aplicación con las variables de entorno configuradas.

    ```json
    {
        "version": "0.2.0",
        "configurations": [
            {
                "type": "java",
                "name": "Spring Boot (DEV)",
                "request": "launch",
                "cwd": "${workspaceFolder}",
                "mainClass": "com.merklys.api.ApiApplication",
                "projectName": "api",
                "args": "--spring.profiles.active=dev",
                "envFile": "${workspaceFolder}/.env.dev"
            },

            {
                "type": "java",
                "name": "Spring Boot (PROD)",
                "request": "launch",
                "cwd": "${workspaceFolder}",
                "mainClass": "com.merklys.api.ApiApplication",
                "projectName": "api",
                "args": "--spring.profiles.active=prod",
                "envFile": "${workspaceFolder}/.env.prod"
            }
        ]
    }
    ```

### 4. Ejecución de la Aplicación

Se puede ejecutar la aplicación desde Google Antigravity o Vscode seleccionando la opción Run and Debug y seleccionando la opción Spring Boot (DEV) o Spring Boot (PROD).


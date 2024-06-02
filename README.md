# Proyecto CRUD de Productos usando thymeleaf

Este es un proyecto practicando thymeleaf, para gestionar un CRUD  (Crear, Leer, Actualizar, Eliminar) de productos utilizando Java, Spring Boot y Thymeleaf. 

## Requisitos

Antes de comenzar, asegúrate de tener instalados los siguientes programas:

- [JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)

## Instalación

1. Clona este repositorio en tu máquina local:

    ```bash
    git clone https://github.com/AlexisMartinez1913/beststore-spring-boot-thymeleaf.git
    cd repositorio
    ```

2. Construye el proyecto con Maven:

    ```bash
    mvn clean install
    ```

3. Ejecuta la aplicación:

    ```bash
    mvn spring-boot:run
    ```

## Uso

1. Abre tu navegador web y navega a `http://localhost:8082`.
2. Utiliza la interfaz web para crear, leer, actualizar y eliminar productos.
3. Configura el application.properties con los datos de mysql local

## Estructura del Proyecto

El proyecto sigue la estructura estándar de un proyecto Spring Boot:
**controller**: Contiene los controladores Spring MVC.
- **model**: Contiene las entidades JPA.
- **repository**: Contiene los repositorios JPA.
- **service**: Contiene la lógica de negocio.
- **templates**: Contiene las vistas Thymeleaf.


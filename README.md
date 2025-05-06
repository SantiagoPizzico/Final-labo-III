# API REST - Gestión de Alumnos, Materias, Carreras y Profesores 

## Ejemplo de endpoints :https://santiago-4481794.postman.co/workspace/Santiago's-Workspace~1578b7e7-6076-4da7-8aeb-8a04220e629d/collection/44163533-43e8bd07-a1e1-4a38-bb72-b4121b88d098?action=share&creator=44163533

Este proyecto es una API REST desarrollada en Java con Spring Boot para gestionar alumnos, materias, carreras y profesores en una institución educativa. Utiliza almacenamiento en memoria (sin base de datos) y sigue una arquitectura en capas.

## Características principales

- **Alumnos:** Alta, consulta, inscripción a materias, cursado y aprobación de asignaturas.
- **Materias:** Alta, consulta, definición de correlatividades.
- **Carreras:** Alta, consulta, asociación de materias.
- **Profesores:** Alta, consulta, asignación de materias.
- **Validaciones:** Reglas de negocio como correlatividades, duplicados y datos obligatorios.
- **Manejo de errores:** Respuestas claras ante errores de validación o negocio.

## Cómo ejecutar

1. Clona el repositorio.
2. Ejecuta en la terminal:

   ```sh
   mvn spring-boot:run
   ```

3. Accede a la API en `http://localhost:8080`.

## Pruebas

Para ejecutar los tests:

```sh
mvn test
```

---

**Nota:**  
La información se almacena en memoria, por lo que se pierde al reiniciar la aplicación.

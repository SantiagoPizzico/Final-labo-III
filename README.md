# Final-labo-III

# API REST - Gestión de Alumnos, Materias, Carreras y Profesores

Este proyecto es una API REST desarrollada en Java con Spring Boot para gestionar alumnos, materias, carreras y profesores en una institución educativa. Utiliza almacenamiento en memoria (sin base de datos) y sigue una arquitectura en capas.

## Características principales

- **Alumnos:** Alta, consulta, inscripción a materias, cursado y aprobación de asignaturas.
- **Materias:** Alta, consulta, definición de correlatividades.
- **Carreras:** Alta, consulta, asociación de materias.
- **Profesores:** Alta, consulta, asignación de materias.
- **Validaciones:** Reglas de negocio como correlatividades, duplicados y datos obligatorios.
- **Manejo de errores:** Respuestas claras ante errores de validación o negocio.

## Ejemplo de endpoints

### Alumnos

- `POST /alumnos`  
  Crear un alumno.
- `GET /alumnos/{id}`  
  Consultar un alumno por ID.
- `POST /alumnos/{id}/asignaturas`  
  Inscribir a un alumno en una materia.
- `POST /alumnos/{alumnoId}/asignaturas/{asignaturaId}/cursar`  
  Marcar asignatura como cursada.
- `POST /alumnos/{alumnoId}/asignaturas/{asignaturaId}/aprobar?nota=8`  
  Aprobar una asignatura.

### Materias

- `POST /materias`  
  Crear una materia.
- `GET /materias/{id}`  
  Consultar materia por ID.
- `POST /materias/{id}/correlatividades`  
  Agregar correlatividad a una materia.

### Carreras

- `POST /carreras`  
  Crear una carrera.
- `GET /carreras/{nombre}`  
  Consultar carrera por nombre.
- `POST /carreras/{nombre}/materias`  
  Agregar materia a una carrera.

### Profesores

- `POST /profesores`  
  Crear un profesor.
- `GET /profesores/{id}`  
  Consultar profesor por ID.
- `POST /profesores/{id}/materias`  
  Asignar materia a un profesor.

## Ejemplo de flujo

1. Crear un profesor.
2. Crear una materia y asignarle el profesor.
3. Crear un alumno.
4. Inscribir al alumno en la materia.
5. Cursar y aprobar la materia.

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

package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;


import java.util.List;

public interface AlumnoService {

    Alumno crearAlumno(String nombre, String apellido, Long dni);
    Alumno buscarAlumnoPorId(long id);
    List<Alumno> buscarAlumnosPorCadena(String cadena);
    List<Alumno> obtenerTodosLosAlumnos();
    void agregarAsignaturaAAlumno(long id, int materiaId);
    void cursarAsignatura(long id, Asignatura asignatura);
    void aprobarAsignatura(long id, Asignatura asignatura, int nota);
    void eliminarAlumnoPorId(long id);
    void eliminarAsignaturaDeAlumno(long id, long asignaturaId);

}
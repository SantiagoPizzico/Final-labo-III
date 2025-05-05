package ar.edu.utn.frbb.tup.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlumnoTest {

    @Test
    void crearAlumnoYAgregarAsignatura() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);

        alumno.agregarAsignatura(asignatura);

        assertEquals(1, alumno.getAsignaturas().size());
        assertEquals("Laboratorio 1", alumno.getAsignaturas().get(0).getNombreAsignatura());
    }

    @Test
    void agregarAsignaturaDuplicadaLanzaExcepcion() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);

        alumno.agregarAsignatura(asignatura);
        assertThrows(ar.edu.utn.frbb.tup.exception.DuplicateResourceException.class,
            () -> alumno.agregarAsignatura(asignatura));
    }
}
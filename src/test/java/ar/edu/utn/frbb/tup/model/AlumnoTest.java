package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.BusinessRuleException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
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
        assertThrows(DuplicateResourceException.class,
            () -> alumno.agregarAsignatura(asignatura));
    }

    @Test
    void aprobarAsignaturaSinCorrelativas_ok() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);
        alumno.agregarAsignatura(asignatura);

        alumno.cursarAsignatura(asignatura);
        alumno.aprobarAsignatura(asignatura, 8);

        assertEquals(EstadoAsignatura.APROBADA, asignatura.getEstado());
        assertEquals(8, asignatura.getNota().orElse(-1));
    }

    @Test
    void aprobarAsignaturaSinCursarLanzaExcepcion() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);
        alumno.agregarAsignatura(asignatura);

        assertThrows(BusinessRuleException.class,
            () -> alumno.aprobarAsignatura(asignatura, 8));
    }

    @Test
    void cursarAsignaturaSinCorrelativasCursadasOAprobadasLanzaExcepcion() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia correlativa = new Materia("Matemática", 1, 1, profesor);
        Materia materia = new Materia("Laboratorio 2", 1, 2, profesor);
        materia.agregarCorrelatividad(correlativa);

        Asignatura asignaturaCorrelativa = new Asignatura(correlativa, 2L);
        Asignatura asignatura = new Asignatura(materia, 1L);

        alumno.agregarAsignatura(asignaturaCorrelativa);
        alumno.agregarAsignatura(asignatura);

        // No cursa ni aprueba la correlativa

        assertThrows(BusinessRuleException.class,
            () -> alumno.cursarAsignatura(asignatura));
    }

    @Test
    void actualizarAsignaturaActualizaEstadoYNota() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);

        alumno.agregarAsignatura(asignatura);

        asignatura.cursarAsignatura();
        asignatura.aprobarAsignatura(10);

        Asignatura nuevaAsignatura = new Asignatura(materia, 1L);
        nuevaAsignatura.cursarAsignatura();
        nuevaAsignatura.aprobarAsignatura(9);

        alumno.actualizarAsignatura(nuevaAsignatura);

        assertEquals(EstadoAsignatura.APROBADA, alumno.getAsignaturas().get(0).getEstado());
        assertEquals(9, alumno.getAsignaturas().get(0).getNota().orElse(-1));
    }
}
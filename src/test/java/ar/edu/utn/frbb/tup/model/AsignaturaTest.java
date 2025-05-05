package ar.edu.utn.frbb.tup.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AsignaturaTest {

    @Test
    void crearAsignaturaYEstados() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);

        assertEquals(EstadoAsignatura.NO_CURSADA, asignatura.getEstado());
        asignatura.cursarAsignatura();
        assertEquals(EstadoAsignatura.CURSADA, asignatura.getEstado());
        asignatura.aprobarAsignatura(8);
        assertEquals(EstadoAsignatura.APROBADA, asignatura.getEstado());
        assertEquals(8, asignatura.getNota().get());
    }

    @Test
    void aprobarAsignaturaSinCursarLanzaExcepcion() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        Asignatura asignatura = new Asignatura(materia, 1L);

        assertThrows(ar.edu.utn.frbb.tup.exception.BusinessRuleException.class,
            () -> asignatura.aprobarAsignatura(8));
    }
}
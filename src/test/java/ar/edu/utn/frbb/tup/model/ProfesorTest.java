package ar.edu.utn.frbb.tup.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProfesorTest {

    @Test
    void crearProfesorYAgregarMateria() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);

        profesor.agregarMateriaDictada(materia);

        assertEquals(1, profesor.getMateriasDictadas().size());
        assertEquals("Laboratorio 1", profesor.getMateriasDictadas().get(0).getNombre());
    }

    @Test
    void agregarMateriaDuplicadaLanzaExcepcion() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);

        profesor.agregarMateriaDictada(materia);
        assertThrows(ar.edu.utn.frbb.tup.exception.DuplicateResourceException.class,
            () -> profesor.agregarMateriaDictada(materia));
    }
}
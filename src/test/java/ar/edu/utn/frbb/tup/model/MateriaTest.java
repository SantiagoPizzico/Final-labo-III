package ar.edu.utn.frbb.tup.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MateriaTest {

    @Test
    void crearMateriaYAgregarCorrelatividad() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia m1 = new Materia("Laboratorio 1", 1, 1, profesor);
        Materia m2 = new Materia("Laboratorio 2", 1, 2, profesor);

        m2.agregarCorrelatividad(m1);

        assertEquals(1, m2.getCorrelatividades().size());
        assertEquals("Laboratorio 1", m2.getCorrelatividades().get(0).getNombre());
    }
}
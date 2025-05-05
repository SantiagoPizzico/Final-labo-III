package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MateriaDaoMemoryImplTest {

    private MateriaDaoMemoryImpl materiaDao;

    @BeforeEach
    void setUp() {
        materiaDao = new MateriaDaoMemoryImpl();
    }

    @Test
    void saveAndFindById() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        materiaDao.save(materia);
        Materia found = materiaDao.findById(materia.getMateriaId());
        assertNotNull(found);
        assertEquals("Laboratorio 1", found.getNombre());
    }

    @Test
    void findAll() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        materiaDao.save(new Materia("Laboratorio 1", 1, 1, profesor));
        materiaDao.save(new Materia("Laboratorio 2", 1, 2, profesor));
        assertEquals(2, materiaDao.findAll().size());
    }

    @Test
    void deleteById() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 1", 1, 1, profesor);
        materiaDao.save(materia);
        materiaDao.deleteById(materia.getMateriaId());
        assertNull(materiaDao.findById(materia.getMateriaId()));
    }
}
package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorDaoMemoryImplTest {

    private ProfesorDaoMemoryImpl profesorDao;

    @BeforeEach
    void setUp() {
        profesorDao = new ProfesorDaoMemoryImpl();
    }

    @Test
    void saveAndFindById() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        profesorDao.save(profesor);
        Profesor found = profesorDao.findById(profesor.getId());
        assertNotNull(found);
        assertEquals("Luciano", found.getNombre());
    }

    @Test
    void findAll() {
        profesorDao.save(new Profesor("Luciano", "Salotto", "Licenciado"));
        profesorDao.save(new Profesor("Ana", "Gomez", "Doctora"));
        assertEquals(2, profesorDao.findAll().size());
    }

    @Test
    void deleteById() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        profesorDao.save(profesor);
        profesorDao.deleteById(profesor.getId());
        assertNull(profesorDao.findById(profesor.getId()));
    }
}
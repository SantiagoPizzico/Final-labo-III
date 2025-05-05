package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarreraDaoMemoryImplTest {

    private CarreraDaoMemoryImpl carreraDao;

    @BeforeEach
    void setUp() {
        carreraDao = new CarreraDaoMemoryImpl();
    }

    @Test
    void saveAndFindByNombre() {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        carreraDao.save(carrera);
        Carrera found = carreraDao.findByNombre("Tecnicatura Universitaria en Programacion");
        assertNotNull(found);
        assertEquals(3, found.getCantidadAnios());
    }

    @Test
    void findAll() {
        carreraDao.save(new Carrera("Tecnicatura Universitaria en Programacion", 3));
        carreraDao.save(new Carrera("Tecnicatura Universitaria en Sistemas", 4));
        assertEquals(2, carreraDao.findAll().size());
    }

    @Test
    void deleteByNombre() {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        carreraDao.save(carrera);
        carreraDao.deleteByNombre("Tecnicatura Universitaria en Programacion");
        assertNull(carreraDao.findByNombre("Tecnicatura Universitaria en Programacion"));
    }
}
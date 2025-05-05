package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoDaoMemoryImplTest {

    private AlumnoDaoMemoryImpl alumnoDao;

    @BeforeEach
    void setUp() {
        alumnoDao = new AlumnoDaoMemoryImpl();
    }

    @Test
    void saveAndFindById() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        alumnoDao.save(alumno);
        Alumno found = alumnoDao.findById(alumno.getId());
        assertNotNull(found);
        assertEquals("Juan", found.getNombre());
    }

    @Test
    void updateAlumno() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        alumnoDao.save(alumno);
        alumno.setNombre("Carlos");
        alumnoDao.update(alumno);
        Alumno updated = alumnoDao.findById(alumno.getId());
        assertEquals("Carlos", updated.getNombre());
    }

    @Test
    void findAll() {
        alumnoDao.save(new Alumno("Juan", "Perez", 12345678L));
        alumnoDao.save(new Alumno("Ana", "Gomez", 87654321L));
        assertEquals(2, alumnoDao.findAll().size());
    }

    @Test
    void deleteById() {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        alumnoDao.save(alumno);
        alumnoDao.deleteById(alumno.getId());
        assertNull(alumnoDao.findById(alumno.getId()));
    }
}
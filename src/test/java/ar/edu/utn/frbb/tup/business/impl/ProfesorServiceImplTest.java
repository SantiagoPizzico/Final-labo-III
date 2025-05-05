package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfesorServiceImplTest {

    private ProfesorDao profesorDao;
    private ProfesorServiceImpl profesorService;

    @BeforeEach
    void setUp() {
        profesorDao = mock(ProfesorDao.class);
        profesorService = new ProfesorServiceImpl(profesorDao);
    }

    @Test
    void crearProfesor_ok() {
        when(profesorDao.findAll()).thenReturn(new ArrayList<>());
        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado");
        when(profesorDao.save(any(Profesor.class))).thenReturn(profesor);

        Profesor creado = profesorService.crearProfesor("Juan", "Perez", "Licenciado");

        assertEquals("Juan", creado.getNombre());
        assertEquals("Perez", creado.getApellido());
        assertEquals("Licenciado", creado.getTitulo());
    }

    @Test
    void crearProfesor_nombreVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> profesorService.crearProfesor("", "Perez", "Licenciado"));
        assertTrue(ex.getMessage().contains("nombre"));
    }

    @Test
    void crearProfesor_apellidoVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> profesorService.crearProfesor("Juan", "", "Licenciado"));
        assertTrue(ex.getMessage().contains("apellido"));
    }

    @Test
    void crearProfesor_tituloVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> profesorService.crearProfesor("Juan", "Perez", ""));
        assertTrue(ex.getMessage().contains("título"));
    }

    @Test
    void crearProfesor_duplicado_lanzaExcepcion() {
        Profesor existente = new Profesor("Juan", "Perez", "Licenciado");
        when(profesorDao.findAll()).thenReturn(List.of(existente));
        assertThrows(DuplicateResourceException.class,
                () -> profesorService.crearProfesor("Juan", "Perez", "Licenciado"));
    }

    @Test
    void buscarProfesorPorId_ok() {
        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado");
        when(profesorDao.findById(1L)).thenReturn(profesor);

        Profesor resultado = profesorService.buscarProfesorPorId(1L);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void buscarProfesorPorId_noExiste_lanzaExcepcion() {
        when(profesorDao.findById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> profesorService.buscarProfesorPorId(1L));
    }

    @Test
    void buscarProfesoresPorCadena_filtraCorrectamente() {
        Profesor p1 = new Profesor("Juan", "Perez", "Licenciado");
        Profesor p2 = new Profesor("Ana", "Gomez", "Doctora");
        when(profesorDao.findAll()).thenReturn(List.of(p1, p2));
        List<Profesor> resultado = profesorService.buscarProfesoresPorCadena("juan");
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void obtenerTodosLosProfesores_ok() {
        Profesor p1 = new Profesor("Juan", "Perez", "Licenciado");
        Profesor p2 = new Profesor("Ana", "Gomez", "Doctora");
        when(profesorDao.findAll()).thenReturn(List.of(p1, p2));
        List<Profesor> resultado = profesorService.obtenerTodosLosProfesores();
        assertEquals(2, resultado.size());
    }

    @Test
    void asignarMateriaAProfesor_ok() {
        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado");
        when(profesorDao.findById(1L)).thenReturn(profesor);
        Materia materia = new Materia();
        materia.setMateriaId(10);
        materia.setNombre("Matemática");

        profesorService.asignarMateriaAProfesor(1L, materia);

        assertTrue(profesor.getMateriasDictadas().contains(materia));
        verify(profesorDao).save(profesor);
    }

    @Test
    void asignarMateriaAProfesor_materiaNula_lanzaExcepcion() {
        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado");
        when(profesorDao.findById(1L)).thenReturn(profesor);

        assertThrows(ValidationException.class, () -> profesorService.asignarMateriaAProfesor(1L, null));
    }

    @Test
    void eliminarProfesorPorId_ok() {
        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado");
        when(profesorDao.findById(1L)).thenReturn(profesor);

        profesorService.eliminarProfesorPorId(1L);

        verify(profesorDao).deleteById(1L);
    }

    @Test
    void eliminarProfesorPorId_noExiste_lanzaExcepcion() {
        when(profesorDao.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> profesorService.eliminarProfesorPorId(1L));
    }
}
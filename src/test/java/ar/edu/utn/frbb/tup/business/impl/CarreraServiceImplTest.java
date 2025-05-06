package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarreraServiceImplTest {

    private CarreraDao carreraDao;
    private MateriaService materiaService;
    private CarreraServiceImpl carreraService;

    @BeforeEach
    void setUp() {
        carreraDao = mock(CarreraDao.class);
        materiaService = mock(MateriaService.class);
        carreraService = new CarreraServiceImpl(carreraDao, materiaService);
    }

    @Test
    void crearCarrera_ok() {
        when(carreraDao.findAll()).thenReturn(new ArrayList<>());
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        when(carreraDao.save(any(Carrera.class))).thenReturn(carrera);

        Carrera creada = carreraService.crearCarrera("Tecnicatura Universitaria en Programacion", 3);

        assertEquals("Tecnicatura Universitaria en Programacion", creada.getNombre());
        assertEquals(3, creada.getCantidadAnios());
    }

    @Test
    void crearCarrera_nombreVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> carreraService.crearCarrera("", 3));
        assertTrue(ex.getMessage().contains("nombre"));
    }

    @Test
    void crearCarrera_cantidadAniosInvalida_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> carreraService.crearCarrera("Tecnicatura Universitaria en Programacion", 0));
        assertTrue(ex.getMessage().contains("años"));
    }

    @Test
    void crearCarrera_duplicada_lanzaExcepcion() {
        Carrera existente = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        when(carreraDao.findAll()).thenReturn(List.of(existente));
        assertThrows(DuplicateResourceException.class,
                () -> carreraService.crearCarrera("Tecnicatura Universitaria en Programacion", 3));
    }

    @Test
    void buscarCarreraPorNombre_ok() {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        when(carreraDao.findByNombre("Tecnicatura Universitaria en Programacion")).thenReturn(carrera);

        Carrera resultado = carreraService.buscarCarreraPorNombre("Tecnicatura Universitaria en Programacion");
        assertEquals("Tecnicatura Universitaria en Programacion", resultado.getNombre());
    }

    @Test
    void buscarCarreraPorNombre_noExiste_lanzaExcepcion() {
        when(carreraDao.findByNombre("Tecnicatura Universitaria en Programacion")).thenReturn(null);
        assertThrows(ResourceNotFoundException.class,
                () -> carreraService.buscarCarreraPorNombre("Tecnicatura Universitaria en Programacion"));
    }

    @Test
    void buscarCarrerasPorCadena_ok() {
        Carrera c1 = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        Carrera c2 = new Carrera("Tecnicatura Universitaria en Sistemas", 4);
        when(carreraDao.findAll()).thenReturn(List.of(c1, c2));
        List<Carrera> resultado = carreraService.buscarCarrerasPorCadena("program");
        assertEquals(1, resultado.size());
        assertEquals("Tecnicatura Universitaria en Programacion", resultado.get(0).getNombre());
    }

    @Test
    void obtenerTodasLasCarreras_ok() {
        Carrera c1 = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        Carrera c2 = new Carrera("Tecnicatura Universitaria en Sistemas", 4);
        when(carreraDao.findAll()).thenReturn(List.of(c1, c2));
        List<Carrera> resultado = carreraService.obtenerTodasLasCarreras();
        assertEquals(2, resultado.size());
    }

    @Test
    void agregarMateriaACarrera_ok() {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        when(carreraDao.findByNombre("Tecnicatura Universitaria en Programacion")).thenReturn(carrera);
        Materia materia = new Materia();
        materia.setMateriaId(1);
        materia.setNombre("Programación I");
        when(materiaService.buscarMateriaPorId(1)).thenReturn(materia);

        carreraService.agregarMateriaACarrera("Tecnicatura Universitaria en Programacion", 1);

        assertEquals(1, carrera.getMateriasList().size());
        assertEquals("Programación I", carrera.getMateriasList().get(0).getNombre());
        verify(carreraDao).update(carrera);
    }

    @Test
    void agregarMateriaACarrera_materiaNoExiste_lanzaExcepcion() {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        when(carreraDao.findByNombre("Tecnicatura Universitaria en Programacion")).thenReturn(carrera);
        when(materiaService.buscarMateriaPorId(1)).thenThrow(new ResourceNotFoundException("Materia", "1"));

        assertThrows(ValidationException.class,
                () -> carreraService.agregarMateriaACarrera("Tecnicatura Universitaria en Programacion", 1));
    }

    @Test
    void obtenerMaterias_ok() {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        Materia materia = new Materia();
        materia.setMateriaId(1);
        materia.setNombre("Programación I");
        carrera.agregarMateria(materia);
        when(carreraDao.findByNombre("Tecnicatura Universitaria en Programacion")).thenReturn(carrera);

        List<Materia> materias = carreraService.obtenerMaterias("Tecnicatura Universitaria en Programacion");
        assertEquals(1, materias.size());
        assertEquals("Programación I", materias.get(0).getNombre());
    }
}
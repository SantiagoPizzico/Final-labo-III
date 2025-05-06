package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.exception.BusinessRuleException;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MateriaServiceImplTest {

    private MateriaDao materiaDao;
    private ProfesorService profesorService;
    private MateriaServiceImpl materiaService;

    @BeforeEach
    void setUp() {
        materiaDao = mock(MateriaDao.class);
        profesorService = mock(ProfesorService.class);
        materiaService = new MateriaServiceImpl(materiaDao, profesorService);
    }

    @Test
    void crearMateria_ok() {
        when(materiaDao.findAll()).thenReturn(new ArrayList<>());
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        when(profesorService.buscarProfesorPorId(1L)).thenReturn(profesor);
        Materia materia = new Materia("Laboratorio 3", 2, 1, profesor);
        when(materiaDao.save(any(Materia.class))).thenReturn(materia);

        Materia creada = materiaService.crearMateria("Laboratorio 3", 2, 1, 1L);

        assertEquals("Laboratorio 3", creada.getNombre());
        assertEquals(2, creada.getAnio());
        assertEquals(1, creada.getCuatrimestre());
        assertEquals(profesor, creada.getProfesor());
    }

    @Test
    void crearMateria_nombreVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> materiaService.crearMateria("", 2, 1, 1L));
        assertTrue(ex.getMessage().contains("nombre"));
    }

    @Test
    void crearMateria_anioInvalido_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> materiaService.crearMateria("Laboratorio 3", 0, 1, 1L));
        assertTrue(ex.getMessage().contains("año"));
    }

    @Test
    void crearMateria_cuatrimestreInvalido_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> materiaService.crearMateria("Laboratorio 3", 2, 0, 1L));
        assertTrue(ex.getMessage().contains("cuatrimestre"));
    }

    @Test
    void crearMateria_profesorIdNulo_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> materiaService.crearMateria("Laboratorio 3", 2, 1, null));
        assertTrue(ex.getMessage().contains("profesor"));
    }

    @Test
    void crearMateria_duplicada_lanzaExcepcion() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia existente = new Materia("Laboratorio 3", 2, 1, profesor);
        when(materiaDao.findAll()).thenReturn(List.of(existente));
        assertThrows(DuplicateResourceException.class,
                () -> materiaService.crearMateria("Laboratorio 3", 2, 1, 1L));
    }

    @Test
    void crearMateria_profesorNoExiste_lanzaExcepcion() {
        when(materiaDao.findAll()).thenReturn(new ArrayList<>());
        when(profesorService.buscarProfesorPorId(1L)).thenThrow(new ResourceNotFoundException("Profesor", "1"));
        assertThrows(ValidationException.class,
                () -> materiaService.crearMateria("Laboratorio 3", 2, 1, 1L));
    }

    @Test
    void buscarMateriaPorId_ok() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 3", 2, 1, profesor);
        when(materiaDao.findById(1)).thenReturn(materia);

        Materia resultado = materiaService.buscarMateriaPorId(1);
        assertEquals("Laboratorio 3", resultado.getNombre());
    }

    @Test
    void buscarMateriaPorId_noExiste_lanzaExcepcion() {
        when(materiaDao.findById(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> materiaService.buscarMateriaPorId(1));
    }

    @Test
    void obtenerTodasLasMaterias_ok() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia m1 = new Materia("Laboratorio 3", 2, 1, profesor);
        Materia m2 = new Materia("Programación I", 1, 1, profesor);
        when(materiaDao.findAll()).thenReturn(List.of(m1, m2));
        List<Materia> resultado = materiaService.obtenerTodasLasMaterias();
        assertEquals(2, resultado.size());
    }

    @Test
    void agregarCorrelatividad_ok() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 3", 2, 1, profesor);
        materia.setMateriaId(1);
        Materia correlativa = new Materia("Programación I", 1, 1, profesor);
        correlativa.setMateriaId(2);

        when(materiaDao.findById(1)).thenReturn(materia);
        when(materiaDao.findById(2)).thenReturn(correlativa);

        materiaService.agregarCorrelatividad(1, correlativa);

        assertEquals(1, materia.getCorrelatividades().size());
        assertEquals("Programación I", materia.getCorrelatividades().get(0).getNombre());
        verify(materiaDao).update(materia);
    }

    @Test
    void agregarCorrelatividad_aSiMisma_lanzaExcepcion() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 3", 2, 1, profesor);
        materia.setMateriaId(1);

        when(materiaDao.findById(1)).thenReturn(materia);

        Materia correlativa = new Materia("Laboratorio 3", 2, 1, profesor);
        correlativa.setMateriaId(1);

        assertThrows(BusinessRuleException.class, () -> materiaService.agregarCorrelatividad(1, correlativa));
    }

    @Test
    void agregarCorrelatividad_noExiste_lanzaExcepcion() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 3", 2, 1, profesor);
        materia.setMateriaId(1);

        when(materiaDao.findById(1)).thenReturn(materia);
        when(materiaDao.findById(2)).thenReturn(null);

        Materia correlativa = new Materia("Programación I", 1, 1, profesor);
        correlativa.setMateriaId(2);

        assertThrows(ResourceNotFoundException.class, () -> materiaService.agregarCorrelatividad(1, correlativa));
    }

    @Test
    void agregarCorrelatividad_duplicada_lanzaExcepcion() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Materia materia = new Materia("Laboratorio 3", 2, 1, profesor);
        materia.setMateriaId(1);
        Materia correlativa = new Materia("Programación I", 1, 1, profesor);
        correlativa.setMateriaId(2);
        materia.getCorrelatividades().add(correlativa);

        when(materiaDao.findById(1)).thenReturn(materia);
        when(materiaDao.findById(2)).thenReturn(correlativa);

        assertThrows(DuplicateResourceException.class, () -> materiaService.agregarCorrelatividad(1, correlativa));
    }
}
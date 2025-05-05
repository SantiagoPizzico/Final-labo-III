package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumnoServiceImplTest {

    private AlumnoDao alumnoDao;
    private MateriaService materiaService;
    private AlumnoServiceImpl alumnoService;

    @BeforeEach
    void setUp() {
        alumnoDao = mock(AlumnoDao.class);
        materiaService = mock(MateriaService.class);
        alumnoService = new AlumnoServiceImpl(alumnoDao, materiaService);
    }

    @Test
    void crearAlumno_ok() {
        when(alumnoDao.findAll()).thenReturn(new ArrayList<>());
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.save(any(Alumno.class))).thenReturn(alumno);

        Alumno creado = alumnoService.crearAlumno("Santiago", "Pizzico", 12345678L);

        assertEquals("Santiago", creado.getNombre());
        assertEquals("Pizzico", creado.getApellido());
        assertEquals(12345678L, creado.getDni());
    }

    @Test
    void crearAlumno_nombreVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> alumnoService.crearAlumno("", "Pizzico", 12345678L));
        assertTrue(ex.getMessage().contains("nombre"));
    }

    @Test
    void crearAlumno_apellidoVacio_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> alumnoService.crearAlumno("Santiago", "", 12345678L));
        assertTrue(ex.getMessage().contains("apellido"));
    }

    @Test
    void crearAlumno_dniNulo_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> alumnoService.crearAlumno("Santiago", "Pizzico", null));
        assertTrue(ex.getMessage().contains("DNI"));
    }

    @Test
    void crearAlumno_dniIncorrecto_lanzaExcepcion() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> alumnoService.crearAlumno("Santiago", "Pizzico", 1234L));
        assertTrue(ex.getMessage().contains("DNI"));
    }

    @Test
    void crearAlumno_duplicado_lanzaExcepcion() {
        Alumno existente = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findAll()).thenReturn(List.of(existente));
        assertThrows(DuplicateResourceException.class,
                () -> alumnoService.crearAlumno("Santiago", "Pizzico", 12345678L));
    }

    @Test
    void buscarAlumnoPorId_ok() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);
        Alumno resultado = alumnoService.buscarAlumnoPorId(1L);
        assertEquals("Santiago", resultado.getNombre());
    }

    @Test
    void buscarAlumnoPorId_noExiste_lanzaExcepcion() {
        when(alumnoDao.findById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> alumnoService.buscarAlumnoPorId(1L));
    }

    @Test
    void buscarAlumnosPorCadena_filtraCorrectamente() {
        Alumno a1 = new Alumno("Santiago", "Pizzico", 12345678L);
        Alumno a2 = new Alumno("Juan", "Perez", 87654321L);
        when(alumnoDao.findAll()).thenReturn(List.of(a1, a2));
        List<Alumno> resultado = alumnoService.buscarAlumnosPorCadena("santiago");
        assertEquals(1, resultado.size());
        assertEquals("Santiago", resultado.get(0).getNombre());
    }

    @Test
    void agregarAsignaturaAAlumno_ok() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        materia.setNombre("Matemática");
        when(materiaService.buscarMateriaPorId(2)).thenReturn(materia);

        alumnoService.agregarAsignaturaAAlumno(1L, 2);

        assertEquals(1, alumno.getAsignaturas().size());
        assertEquals("Matemática", alumno.getAsignaturas().get(0).getNombreAsignatura());
        verify(alumnoDao).update(alumno);
    }

    @Test
    void agregarAsignaturaAAlumno_materiaNoExiste_lanzaExcepcion() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);
        when(materiaService.buscarMateriaPorId(2)).thenThrow(new ResourceNotFoundException("Materia", "2"));
        assertThrows(ValidationException.class, () -> alumnoService.agregarAsignaturaAAlumno(1L, 2));
    }

    @Test
    void agregarAsignaturaAAlumno_duplicada_lanzaExcepcion() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        materia.setNombre("Matemática");
        Asignatura asignatura = new Asignatura(materia, 1L);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDao.findById(1L)).thenReturn(alumno);
        when(materiaService.buscarMateriaPorId(2)).thenReturn(materia);

        assertThrows(DuplicateResourceException.class, () -> alumnoService.agregarAsignaturaAAlumno(1L, 2));
    }

    @Test
    void cursarAsignatura_ok() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        materia.setNombre("Matemática");
        Asignatura asignatura = new Asignatura(materia, 1L);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDao.findById(1L)).thenReturn(alumno);

        alumnoService.cursarAsignatura(1L, asignatura);

        asignatura.getEstado();
        assertEquals(asignatura.getEstado(), EstadoAsignatura.CURSADA);
        verify(alumnoDao).update(alumno);
    }

    @Test
    void cursarAsignatura_noExiste_lanzaExcepcion() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        Asignatura asignatura = new Asignatura(materia, 1L);

        assertThrows(ResourceNotFoundException.class, () -> alumnoService.cursarAsignatura(1L, asignatura));
    }

    @Test
    void aprobarAsignatura_ok() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        materia.setNombre("Matemática");
        Asignatura asignatura = new Asignatura(materia, 1L);
        asignatura.getEstado();
        asignatura.setEstado(EstadoAsignatura.CURSADA);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDao.findById(1L)).thenReturn(alumno);

        alumnoService.aprobarAsignatura(1L, asignatura, 8);

        asignatura.getEstado();
        assertEquals(asignatura.getEstado(), EstadoAsignatura.APROBADA);
        assertEquals(8, asignatura.getNota().get());
        verify(alumnoDao).update(alumno);
    }

    @Test
    void aprobarAsignatura_noExiste_lanzaExcepcion() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        Asignatura asignatura = new Asignatura(materia, 1L);

        assertThrows(ResourceNotFoundException.class, () -> alumnoService.aprobarAsignatura(1L, asignatura, 8));
    }

    @Test
    void aprobarAsignatura_notaInvalida_lanzaExcepcion() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        materia.setNombre("Matemática");
        Asignatura asignatura = new Asignatura(materia, 1L);
        asignatura.getEstado();
        asignatura.setEstado(EstadoAsignatura.CURSADA);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDao.findById(1L)).thenReturn(alumno);

        assertThrows(ValidationException.class, () -> alumnoService.aprobarAsignatura(1L, asignatura, 0));
    }

    @Test
    void eliminarAsignaturaDeAlumno_ok() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        Materia materia = new Materia();
        materia.setMateriaId(2);
        materia.setNombre("Matemática");
        Asignatura asignatura = new Asignatura(materia, 1L);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDao.findById(1L)).thenReturn(alumno);

        alumnoService.eliminarAsignaturaDeAlumno(1L, 1L);

        assertTrue(alumno.getAsignaturas().isEmpty());
        verify(alumnoDao).update(alumno);
    }

    @Test
    void eliminarAsignaturaDeAlumno_noExiste_lanzaExcepcion() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);

        assertThrows(ResourceNotFoundException.class, () -> alumnoService.eliminarAsignaturaDeAlumno(1L, 1L));
    }

    @Test
    void eliminarAlumnoPorId_ok() {
        Alumno alumno = new Alumno("Santiago", "Pizzico", 12345678L);
        when(alumnoDao.findById(1L)).thenReturn(alumno);

        alumnoService.eliminarAlumnoPorId(1L);

        verify(alumnoDao).deleteById(1L);
    }

    @Test
    void eliminarAlumnoPorId_noExiste_lanzaExcepcion() {
        when(alumnoDao.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> alumnoService.eliminarAlumnoPorId(1L));
    }
}
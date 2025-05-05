package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoDao alumnoDao;
    private final MateriaService materiaService;

    public AlumnoServiceImpl(AlumnoDao alumnoDao, MateriaService materiaService) {
        this.alumnoDao = alumnoDao;
        this.materiaService = materiaService;
    }

    @Override
    @Transactional
    public Alumno crearAlumno(String nombre, String apellido, Long dni) {
        // Validación de inputs
        if (nombre == null || nombre.isBlank()) {
            throw new ValidationException("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new ValidationException("El apellido no puede estar vacío.");
        }
        if (dni == null) {
            throw new ValidationException("El DNI no puede ser nulo.");
        }
        if (String.valueOf(dni).length() != 8) {
            throw new ValidationException("El DNI debe tener exactamente 8 números.");
        }
        // Validación de duplicados por DNI
        boolean exists = alumnoDao.findAll().stream()
            .anyMatch(a -> a.getDni() != null && a.getDni().equals(dni));
        if (exists) {
            throw new DuplicateResourceException("Alumno", "dni", String.valueOf(dni));
        }
        Alumno nuevoAlumno = new Alumno(nombre, apellido, dni);
        Alumno alumnoGuardado = alumnoDao.save(nuevoAlumno);
        return alumnoGuardado;
    }

    @Override
    public Alumno buscarAlumnoPorId(long id) {
        Alumno alumno = alumnoDao.findById(id);
        if (alumno == null) {
            throw new ResourceNotFoundException("Alumno", String.valueOf(id));
        }
        return alumno;
    }

    @Override
    public List<Alumno> buscarAlumnosPorCadena(String cadena) {
        String lower = cadena.toLowerCase();
        return alumnoDao.findAll().stream()
            .filter(a ->
                (a.getNombre() != null && a.getNombre().toLowerCase().contains(lower)) ||
                (a.getApellido() != null && a.getApellido().toLowerCase().contains(lower)) ||
                (a.getDni() != null && String.valueOf(a.getDni()).contains(cadena))
            )
            .toList();
    }

    @Override
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoDao.findAll();
    }

@Override
@Transactional
public void agregarAsignaturaAAlumno(long id, int materiaId) {
    Alumno alumno = buscarAlumnoPorId(id);
    Materia materia;
    try {
        materia = materiaService.buscarMateriaPorId(materiaId);
    } catch (ResourceNotFoundException e) {
        throw new ValidationException("La materia con id " + materiaId + " no existe.");
    }
    boolean exists = alumno.obtenerListaAsignaturas().stream()
        .anyMatch(a -> a.getMateria().getMateriaId() == materiaId);
    if (exists) {
        throw new DuplicateResourceException("Asignatura", "materia", materia.getNombre());
    }
    // Validar correlatividades: todas deben estar CURSADA o APROBADA
    for (Materia correlativa : materia.getCorrelatividades()) {
        boolean correlativaCursadaOAprobada = alumno.obtenerListaAsignaturas().stream()
            .filter(a -> a.getMateria().getMateriaId() == correlativa.getMateriaId())
            .anyMatch(a -> a.getEstado() == ar.edu.utn.frbb.tup.model.EstadoAsignatura.CURSADA
                        || a.getEstado() == ar.edu.utn.frbb.tup.model.EstadoAsignatura.APROBADA);
        if (!correlativaCursadaOAprobada) {
            throw new ValidationException("Debe tener la correlativa '" + correlativa.getNombre() + "' cursada o aprobada para inscribirse en '" + materia.getNombre() + "'.");
        }
    }
    long nuevoAsignaturaId = generarNuevoAsignaturaId();
    Asignatura nuevaAsignatura = new Asignatura(materia, nuevoAsignaturaId);
    alumno.agregarAsignatura(nuevaAsignatura);
    alumnoDao.update(alumno);
}

    @Override
    @Transactional
    public void cursarAsignatura(long id, Asignatura asignatura) {
        Alumno alumno = buscarAlumnoPorId(id);
        if (!alumno.getAsignaturas().contains(asignatura)) {
            throw new ResourceNotFoundException("Asignatura", String.valueOf(asignatura.getAsignaturaId()));
        }
        alumno.cursarAsignatura(asignatura);
        alumnoDao.update(alumno);
    }
    
    @Override
    @Transactional
    public void aprobarAsignatura(long id, Asignatura asignatura, int nota) {
        Alumno alumno = buscarAlumnoPorId(id);
        if (!alumno.getAsignaturas().contains(asignatura)) {
            throw new ResourceNotFoundException("Asignatura", String.valueOf(asignatura.getAsignaturaId()));
        }
        if (nota < 1 || nota > 10) {
            throw new ValidationException("La nota debe estar entre 1 y 10.");
        }
        alumno.aprobarAsignatura(asignatura, nota);
        alumnoDao.update(alumno);
    }

    @Override
    @Transactional
    public void eliminarAsignaturaDeAlumno(long id, long asignaturaId) {
        Alumno alumno = buscarAlumnoPorId(id);
        boolean removed = alumno.getAsignaturas().removeIf(a -> asignaturaId == a.getAsignaturaId());
        if (!removed) {
            throw new ResourceNotFoundException("Asignatura", String.valueOf(asignaturaId));
        }
        alumnoDao.update(alumno);
    }

    @Override
    @Transactional
    public void eliminarAlumnoPorId(long id) {
        Alumno alumno = alumnoDao.findById(id);
        if (alumno == null) {
            throw new ResourceNotFoundException("Alumno", String.valueOf(id));
        }
        alumnoDao.deleteById(id);
    }

    private static long asignaturaIdSequence = 1;

    private synchronized long generarNuevoAsignaturaId() {
        return asignaturaIdSequence++;
    }

}
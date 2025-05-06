package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorDao profesorDao;

    public ProfesorServiceImpl(ProfesorDao profesorDao) {
        this.profesorDao = profesorDao;
    }

    @Override
    @Transactional
    public Profesor crearProfesor(String nombre, String apellido, String titulo) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidationException("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new ValidationException("El apellido no puede estar vacío.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new ValidationException("El título no puede estar vacío.");
        }
        List<Profesor> profesores = profesorDao.findAll();
        boolean exists = profesores.stream()
            .anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre)
                        && p.getApellido().equalsIgnoreCase(apellido));
        if (exists) {
            throw new DuplicateResourceException("Profesor", "nombre y apellido", nombre + " " + apellido);
        }
        Profesor nuevoProfesor = new Profesor(nombre, apellido, titulo);
        return profesorDao.save(nuevoProfesor);
    }

    @Override
    public Profesor buscarProfesorPorId(long id) {
        Profesor profesor = profesorDao.findById(id);
        if (profesor == null) {
            throw new ResourceNotFoundException("Profesor", String.valueOf(id));
        }
        return profesor;
    }

    @Override
    public List<Profesor> buscarProfesoresPorCadena(String cadena) {
        String lower = cadena.toLowerCase();
        return profesorDao.findAll().stream()
            .filter(p ->
                (p.getNombre() != null && p.getNombre().toLowerCase().contains(lower)) ||
                (p.getApellido() != null && p.getApellido().toLowerCase().contains(lower)) ||
                (p.getTitulo() != null && p.getTitulo().toLowerCase().contains(lower))
            )
            .toList();
    }

    @Override
    public List<Profesor> obtenerTodosLosProfesores() {
        return profesorDao.findAll();
    }

    @Override
    @Transactional
    public void asignarMateriaAProfesor(long profesorId, Materia materia) {
        Profesor profesor = buscarProfesorPorId(profesorId);
        if (materia == null) {
            throw new ValidationException("La materia no puede ser nula.");
        }
        profesor.agregarMateriaDictada(materia);
        profesorDao.update(profesor);
    }
    
    @Override
    @Transactional
    public void eliminarProfesorPorId(long id) {
        Profesor profesor = profesorDao.findById(id);
        if (profesor == null) {
            throw new ResourceNotFoundException("Profesor", String.valueOf(id));
        }
        profesorDao.deleteById(id);
    }
}
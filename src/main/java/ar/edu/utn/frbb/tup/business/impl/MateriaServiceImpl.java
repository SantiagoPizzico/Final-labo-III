package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.exception.BusinessRuleException;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaDao materiaDao;
    private final ProfesorService profesorService;

    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorService profesorService) {
        this.materiaDao = materiaDao;
        this.profesorService = profesorService;
    }

    @Override
    @Transactional
    public Materia crearMateria(String nombre, int anio, int cuatrimestre, Long profesorId) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidationException("El nombre de la materia no puede estar vacío.");
        }
        if (anio <= 0) {
            throw new ValidationException("El año debe ser mayor a 0.");
        }
        if (cuatrimestre <= 0) {
            throw new ValidationException("El cuatrimestre debe ser mayor a 0.");
        }
        if (profesorId == null) {
            throw new ValidationException("El id del profesor no puede ser nulo.");
        }
        boolean exists = materiaDao.findAll().stream()
            .anyMatch(m -> m.getNombre().equalsIgnoreCase(nombre) && m.getAnio() == anio && m.getCuatrimestre() == cuatrimestre);
        if (exists) {
            throw new DuplicateResourceException("Materia", "nombre/año/cuatrimestre", nombre + "/" + anio + "/" + cuatrimestre);
        }
        Profesor profesor;
        try {
            profesor = profesorService.buscarProfesorPorId(profesorId);
        } catch (ResourceNotFoundException e) {
            throw new ValidationException("El profesor con id " + profesorId + " no existe.");
        }
        Materia nuevaMateria = new Materia(nombre, anio, cuatrimestre, profesor);
        return materiaDao.save(nuevaMateria);
    }

    @Override
    public Materia buscarMateriaPorId(int id) {
        Materia materia = materiaDao.findById(id);
        if (materia == null) {
            throw new ResourceNotFoundException("Materia", String.valueOf(id));
        }
        return materia;
    }

    @Override
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaDao.findAll();
    }

    @Override
    @Transactional
    public void agregarCorrelatividad(int materiaId, Materia correlativa) {
        Materia materia = buscarMateriaPorId(materiaId);
    
        if (materia.getMateriaId() == correlativa.getMateriaId()) {
            throw new BusinessRuleException("Una materia no puede ser correlativa de sí misma.");
        }
    
        Materia correlativaExistente = materiaDao.findById(correlativa.getMateriaId());
        if (correlativaExistente == null) {
            throw new ResourceNotFoundException("Materia correlativa", String.valueOf(correlativa.getMateriaId()));
        }
    
        boolean yaCorrelativa = materia.getCorrelatividades().stream()
            .anyMatch(m -> m.getMateriaId() == correlativaExistente.getMateriaId());
        if (yaCorrelativa) {
            throw new DuplicateResourceException("Correlatividad", "materiaId", String.valueOf(correlativaExistente.getMateriaId()));
        }
    
        Materia correlativaSimple = new Materia();
        correlativaSimple.setMateriaId(correlativaExistente.getMateriaId());
        correlativaSimple.setNombre(correlativaExistente.getNombre());
        materia.agregarCorrelatividad(correlativaSimple);
        materiaDao.save(materia);
    }
}
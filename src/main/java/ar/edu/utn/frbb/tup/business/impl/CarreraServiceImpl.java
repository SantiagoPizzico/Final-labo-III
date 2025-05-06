package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarreraServiceImpl implements CarreraService {

    private final CarreraDao carreraDao;
    private final MateriaService materiaService;

    public CarreraServiceImpl(CarreraDao carreraDao, MateriaService materiaService) {
        this.carreraDao = carreraDao;
        this.materiaService = materiaService;
    }

    @Override
    @Transactional
    public Carrera crearCarrera(String nombre, int cantidadAnios) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidationException("El nombre de la carrera no puede estar vacío.");
        }
        if (cantidadAnios <= 0) {
            throw new ValidationException("La cantidad de años debe ser mayor a 0.");
        }
        boolean exists = carreraDao.findAll().stream()
            .anyMatch(c -> c.getNombre().equalsIgnoreCase(nombre));
        if (exists) {
            throw new DuplicateResourceException("Carrera", "nombre", nombre);
        }
        Carrera nuevaCarrera = new Carrera(nombre, cantidadAnios);
        return carreraDao.save(nuevaCarrera);
    }

    @Override
    public Carrera buscarCarreraPorNombre(String nombre) {
        Carrera carrera = carreraDao.findByNombre(nombre);
        if (carrera == null) {
            throw new ResourceNotFoundException("Carrera", nombre);
        }
        return carrera;
    }


    @Override
    public List<Carrera> buscarCarrerasPorCadena(String cadena) {
        String lower = cadena.toLowerCase();
        return carreraDao.findAll().stream()
            .filter(c -> c.getNombre() != null && c.getNombre().toLowerCase().contains(lower))
            .toList();
    }

    @Override
    public List<Carrera> obtenerTodasLasCarreras() {
        return carreraDao.findAll();
    }

    @Override
    @Transactional
    public void agregarMateriaACarrera(String nombre, int materiaId) {
        Carrera carrera = buscarCarreraPorNombre(nombre);
        Materia materia;
        try {
            materia = materiaService.buscarMateriaPorId(materiaId);
        } catch (ResourceNotFoundException e) {
            throw new ValidationException("La materia con id " + materiaId + " no existe.");
        }
        // Validación de duplicados ya está en Carrera.agregarMateria
        carrera.agregarMateria(materia);
        carreraDao.update(carrera);
    }

    @Override
    public List<Materia> obtenerMaterias(String nombreCarrera) {
        Carrera carrera = buscarCarreraPorNombre(nombreCarrera);
        return carrera.getMateriasList();
    }
}
package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarreraDaoMemoryImpl implements CarreraDao {

    private static final Map<String, Carrera> repositorioCarreras = new HashMap<>();

    @Override
    public Carrera save(Carrera carrera) {
        repositorioCarreras.put(carrera.getNombre(), carrera);
        return carrera;
    }

    @Override
    public Carrera findByNombre(String nombre) {
        return repositorioCarreras.get(nombre);
    }

    @Override
    public List<Carrera> findAll() {
        return new ArrayList<>(repositorioCarreras.values());
    }

    @Override
    public void deleteByNombre(String nombre) {
        repositorioCarreras.remove(nombre);
    }
}
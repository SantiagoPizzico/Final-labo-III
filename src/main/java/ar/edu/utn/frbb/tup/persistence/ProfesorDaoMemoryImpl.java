package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfesorDaoMemoryImpl implements ProfesorDao {

    private static final Map<Long, Profesor> repositorioProfesores = new HashMap<>();
    private static long currentId = 0;

    @Override
    public Profesor save(Profesor profesor) {
        profesor.setId(++currentId);
        repositorioProfesores.put(profesor.getId(), profesor);
        return profesor;
    }

    @Override
    public Profesor findById(long id) {
        return repositorioProfesores.get(id);
    }

    @Override
    public List<Profesor> findAll() {
        return new ArrayList<>(repositorioProfesores.values());
    }

    @Override
    public void deleteById(long id) {
        repositorioProfesores.remove(id);
    }
}
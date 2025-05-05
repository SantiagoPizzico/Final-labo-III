package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MateriaDaoMemoryImpl implements MateriaDao {

    private static final Map<Integer, Materia> repositorioMaterias = new HashMap<>();
    private static int currentId = 1;

    @Override
    public Materia save(Materia materia) {
        // Solo asigna un nuevo ID si la materia no tiene uno (es nueva)
        if (materia.getMateriaId() == 0) {
            materia.setMateriaId(currentId++);
        }
        repositorioMaterias.put(materia.getMateriaId(), materia);
        return materia;
    }

    @Override
    public Materia findById(int id) {
        return repositorioMaterias.get(id);
    }

    @Override
    public List<Materia> findAll() {
        return new ArrayList<>(repositorioMaterias.values());
    }

    @Override
    public void deleteById(int id) {
        repositorioMaterias.remove(id);
    }
}
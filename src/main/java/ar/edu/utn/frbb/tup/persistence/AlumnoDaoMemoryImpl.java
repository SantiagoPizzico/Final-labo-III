package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    private static final Map<Long, Alumno> repositorioAlumnos = new HashMap<>();
    private static long currentId = 0;

    @Override
    public Alumno save(Alumno alumno) {
        alumno.setId(++currentId);
        repositorioAlumnos.put(alumno.getId(), alumno);
        return alumno;
    }

    @Override
    public Alumno update(Alumno alumno) {
        repositorioAlumnos.put(alumno.getId(), alumno);
        return alumno;
    }

    @Override
    public Alumno findById(long id) {
        return repositorioAlumnos.get(id);
    }

    @Override
    public List<Alumno> findAll() {
        return new ArrayList<>(repositorioAlumnos.values());
    }

    @Override
    public Alumno deleteById(long id) {
        return repositorioAlumnos.remove(id);
    }
}
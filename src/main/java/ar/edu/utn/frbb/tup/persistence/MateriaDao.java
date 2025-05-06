package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;

import java.util.List;

public interface MateriaDao {
    Materia save(Materia materia);

    Materia update(Materia materia);

    Materia findById(int id);

    List<Materia> findAll();

    void deleteById(int id);
}
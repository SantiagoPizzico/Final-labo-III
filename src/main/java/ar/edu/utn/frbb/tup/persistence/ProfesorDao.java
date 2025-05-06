package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;

import java.util.List;

public interface ProfesorDao {
    Profesor save(Profesor profesor);

    Profesor update(Profesor profesor);

    Profesor findById(long id);

    List<Profesor> findAll();

    void deleteById(long id);
}
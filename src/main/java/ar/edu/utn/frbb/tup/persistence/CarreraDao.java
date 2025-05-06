package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;

import java.util.List;

public interface CarreraDao {
    Carrera save(Carrera carrera);

    Carrera update(Carrera carrera);

    Carrera findByNombre(String nombre);

    List<Carrera> findAll();

    void deleteByNombre(String nombre);
}
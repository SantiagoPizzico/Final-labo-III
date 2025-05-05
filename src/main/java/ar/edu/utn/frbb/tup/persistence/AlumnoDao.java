package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;

import java.util.List;


public interface AlumnoDao {
    Alumno save(Alumno alumno);

    Alumno update(Alumno alumno);

    Alumno findById(long id);

    List<Alumno> findAll();

    Alumno deleteById(long id);
}
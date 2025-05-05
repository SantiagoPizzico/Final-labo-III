package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;

import java.util.List;

public interface ProfesorService {

    Profesor crearProfesor(String nombre, String apellido, String titulo);
    Profesor buscarProfesorPorId(long id);
    List<Profesor> buscarProfesoresPorCadena(String cadena);
    List<Profesor> obtenerTodosLosProfesores();
    void asignarMateriaAProfesor(long profesorId, Materia materia);
    void eliminarProfesorPorId(long id);
    
}
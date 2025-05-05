package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Materia;


import java.util.List;

public interface MateriaService {
    
    Materia crearMateria(String nombre, int anio, int cuatrimestre, Long profesorId);
    Materia buscarMateriaPorId(int id);
    List<Materia> obtenerTodasLasMaterias();
    void agregarCorrelatividad(int materiaId, Materia correlativa);

}
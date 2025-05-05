package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;


import java.util.List;

public interface CarreraService {
    
    Carrera crearCarrera(String nombre, int cantidadAnios);
    Carrera buscarCarreraPorNombre(String nombre);
    List<Carrera> obtenerTodasLasCarreras();
    void agregarMateriaACarrera(String nombreCarrera, int materiaId);
    List<Materia> obtenerMaterias(String nombreCarrera);

}
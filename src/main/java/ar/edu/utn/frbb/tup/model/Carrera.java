package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Carrera {

    private final String nombre;
    private int cantidadAnios;
    private List<Materia> materiasList;

    public Carrera(String nombre, int cantidadAnios) {
        this.nombre = nombre;
        this.cantidadAnios = cantidadAnios;
        this.materiasList = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadAnios() {
        return cantidadAnios;
    }

    public List<Materia> getMateriasList() {
        return materiasList;
    }

    public void agregarMateria(Materia materia) {
        if (materiasList.contains(materia)) {
            throw new DuplicateResourceException("Materia", "nombre", materia.getNombre());
        }
        materiasList.add(materia);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrera carrera = (Carrera) o;
        return Objects.equals(nombre, carrera.nombre) &&
            cantidadAnios == carrera.cantidadAnios &&
            Objects.equals(materiasList, carrera.materiasList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, cantidadAnios, materiasList);
    }
}
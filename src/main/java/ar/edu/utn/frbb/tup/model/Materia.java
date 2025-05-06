package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.util.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Materia {

    private int materiaId;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private Profesor profesor;
    private List<Materia> correlatividades;

    public Materia() {
        correlatividades = new ArrayList<>();
    }

    public Materia(String nombre, int anio, int cuatrimestre, Profesor profesor) {
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
        correlatividades = new ArrayList<>();
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        if (profesor != null) {
            this.profesor = profesor;
            profesor.getMateriasDictadas().add(this);
        }
    }

    public List<Materia> getCorrelatividades() {
        return correlatividades;
    }

    public void setCorrelatividades(List<Materia> correlatividades) {
        this.correlatividades = correlatividades;
    }

    public void agregarCorrelatividad(Materia m) {
        this.correlatividades.add(m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return materiaId == materia.materiaId &&
            anio == materia.anio &&
            cuatrimestre == materia.cuatrimestre &&
            Objects.equals(nombre, materia.nombre) &&
            Objects.equals(profesor, materia.profesor) &&
            Objects.equals(correlatividades, materia.correlatividades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materiaId, nombre, anio, cuatrimestre, profesor, correlatividades);
    }
}
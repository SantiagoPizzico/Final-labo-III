package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.util.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Profesor {

    private long id;
    private String nombre;
    private String apellido;
    private String titulo;
    @JsonIgnore
    private List<Materia> materiasDictadas;

    public Profesor() {
        this.materiasDictadas = new ArrayList<>();
    }

    public Profesor(String nombre, String apellido, String titulo) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Materia> getMateriasDictadas() {
        return materiasDictadas;
    }

    public void agregarMateriaDictada(Materia materiaDictada) {
        Valid.validarMateriaNoDuplicada(this.materiasDictadas, materiaDictada);
        this.materiasDictadas.add(materiaDictada);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return id == profesor.id &&
            Objects.equals(nombre, profesor.nombre) &&
            Objects.equals(apellido, profesor.apellido) &&
            Objects.equals(titulo, profesor.titulo) &&
            Objects.equals(materiasDictadas, profesor.materiasDictadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, titulo, materiasDictadas);
    }
}
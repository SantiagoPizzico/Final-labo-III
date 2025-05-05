package ar.edu.utn.frbb.tup.model.dto;

import java.util.List;

public class ProfesorDto {
    private long id;
    private String nombre;
    private String apellido;
    private String titulo;
    private List<MateriaDto> materiasDictadas;

    // Getters y Setters
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

    public List<MateriaDto> getMateriasDictadas() {
        return materiasDictadas;
    }

    public void setMateriasDictadas(List<MateriaDto> materiasDictadas) {
        this.materiasDictadas = materiasDictadas;
    }
}
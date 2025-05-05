package ar.edu.utn.frbb.tup.model.dto;

import ar.edu.utn.frbb.tup.model.Asignatura;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDto {
    private long id;
    private String nombre;
    private String apellido;
    private Long dni;
    private List<Asignatura> asignaturas;

    public AlumnoDto() {
    }

    public AlumnoDto(long id, String nombre, String apellido, Long dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public AlumnoDto(long id, String nombre, String apellido, Long dni, List<Asignatura> asignaturas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.asignaturas = asignaturas;
    }

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

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public List<Asignatura> getAsignaturas() {
        if (asignaturas == null) {
            asignaturas = new ArrayList<>();
        }
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
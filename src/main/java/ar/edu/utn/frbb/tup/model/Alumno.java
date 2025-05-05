package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.util.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alumno {

    private long id;
    private String nombre;
    private String apellido;
    private Long dni;
    private List<Asignatura> asignaturas;

    public Alumno() {
        this.asignaturas = new ArrayList<>();
    }

    public Alumno(String nombre, String apellido, Long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.asignaturas = new ArrayList<>();
    }

    public Alumno(long id, String nombre, String apellido, Long dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.asignaturas = new ArrayList<>();
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

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public void agregarAsignatura(Asignatura a) {
        Valid.validarAsignaturaNoDuplicada(this.asignaturas, a);
        this.asignaturas.add(a);
    }

    public List<Asignatura> obtenerListaAsignaturas() {
        return this.asignaturas;
    }

    public void aprobarAsignatura(Asignatura asignatura, int nota) {
        Valid.validarAsignaturaEnAlumno(this.asignaturas, asignatura);
        Valid.validarCorrelatividadesAprobadas(this.asignaturas, asignatura.getCorrelatividades(), asignatura.getNombreAsignatura());
        Valid.validarNota(nota);
        asignatura.aprobarAsignatura(nota);
    }

    public void cursarAsignatura(Asignatura asignatura) {
        Valid.validarAsignaturaEnAlumno(this.asignaturas, asignatura);
        Valid.validarCorrelatividadesAprobadas(this.asignaturas, asignatura.getCorrelatividades(), asignatura.getNombreAsignatura());
        asignatura.cursarAsignatura();
    }

    public void actualizarAsignatura(Asignatura asignatura) {
        asignaturas.stream()
            .filter(a -> a.getNombreAsignatura().equals(asignatura.getNombreAsignatura()))
            .findFirst()
            .ifPresent(a -> {
                a.setEstado(asignatura.getEstado());
                a.setNota(asignatura.getNota().orElse(null));
            });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return id == alumno.id &&
            Objects.equals(dni, alumno.dni) &&
            Objects.equals(nombre, alumno.nombre) &&
            Objects.equals(apellido, alumno.apellido) &&
            Objects.equals(asignaturas, alumno.asignaturas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, dni, asignaturas);
    }
}
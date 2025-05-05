package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.exception.BusinessRuleException;
import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.util.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Asignatura {
    @JsonIgnore
    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;
    private Long asignaturaId;

    public Asignatura() {
    }

    public Asignatura(Materia materia, long asignaturaId) {
        this.asignaturaId = asignaturaId;
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
    }

    public Long getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    public Optional<Integer> getNota() {
        return Optional.ofNullable(nota);
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getNombreAsignatura() {
        return this.materia.getNombre();
    }

    public List<Materia> getCorrelatividades() {
        return this.materia.getCorrelatividades();
    }

    public void cursarAsignatura() {
        if (this.estado.equals(EstadoAsignatura.CURSADA)) {
            throw new BusinessRuleException("La materia " + materia.getNombre() + " ya se encuentra cursada.");
        }
        if (this.estado.equals(EstadoAsignatura.APROBADA)) {
            throw new BusinessRuleException("La materia " + materia.getNombre() + " ya se encuentra aprobada.");
        }
        this.estado = EstadoAsignatura.CURSADA;
    }

    public void aprobarAsignatura(int nota) {
        Valid.validarNota(nota);
        if (this.estado.equals(EstadoAsignatura.APROBADA)) {
            throw new BusinessRuleException("La asignatura " + materia.getNombre() + " ya está aprobada [NOTA: " + this.nota + "].");
        }
        if (!this.estado.equals(EstadoAsignatura.CURSADA)) {
            throw new BusinessRuleException("La asignatura " + materia.getNombre() + " debe estar cursada, para poder aprobarse.");
        }
        if (nota < 4) {
            throw new ValidationException("Si desea aprobar la asignatura, la nota debe ser igual o mayor a 4.");
        }
        this.estado = EstadoAsignatura.APROBADA;
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asignatura that = (Asignatura) o;
        return Objects.equals(asignaturaId, that.asignaturaId) &&
            Objects.equals(materia, that.materia) &&
            estado == that.estado &&
            Objects.equals(nota, that.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asignaturaId, materia, estado, nota);
    }
}
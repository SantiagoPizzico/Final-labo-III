package ar.edu.utn.frbb.tup.util;

import ar.edu.utn.frbb.tup.exception.ValidationException;
import ar.edu.utn.frbb.tup.exception.DuplicateResourceException;
import ar.edu.utn.frbb.tup.exception.ResourceNotFoundException;
import ar.edu.utn.frbb.tup.exception.BusinessRuleException;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;

import java.util.List;

public class Valid {

    // Valida que la nota esté en el rango permitido
    public static void validarNota(int nota) {
        if (nota < 1 || nota > 10) {
            throw new ValidationException("La nota debe estar entre 1 y 10.");
        }
    }

    // Valida que una materia no esté duplicada en una lista
    public static void validarMateriaNoDuplicada(List<Materia> materias, Materia materia) {
        if (materias.contains(materia)) {
            throw new DuplicateResourceException("Materia", "nombre", materia.getNombre());
        }
    }

    // Valida que una asignatura no esté duplicada en una lista
    public static void validarAsignaturaNoDuplicada(List<Asignatura> asignaturas, Asignatura asignatura) {
        if (asignaturas.contains(asignatura)) {
            throw new DuplicateResourceException("Asignatura", "id", String.valueOf(asignatura.getAsignaturaId()));
        }
    }

    // Valida que una asignatura exista en la lista del alumno
    public static void validarAsignaturaEnAlumno(List<Asignatura> asignaturas, Asignatura asignatura) {
        if (!asignaturas.contains(asignatura)) {
            throw new ResourceNotFoundException("Asignatura", String.valueOf(asignatura.getAsignaturaId()));
        }
    }
    
    // Valida que todas las correlatividades estén cursadas o aprobadas antes de cursar/aprobar
    public static void validarCorrelatividadesAprobadas(List<Asignatura> asignaturas, List<Materia> correlatividades, String nombreAsignatura) {
        for (Materia correlativa : correlatividades) {
            boolean cursadaOAprobada = asignaturas.stream()
                .filter(a -> correlativa.getNombre().equals(a.getNombreAsignatura()))
                .anyMatch(a -> EstadoAsignatura.CURSADA.equals(a.getEstado()) || EstadoAsignatura.APROBADA.equals(a.getEstado()));
            if (!cursadaOAprobada) {
                throw new BusinessRuleException("La asignatura " + correlativa.getNombre() + " debe estar cursada o aprobada para cursar/aprobar " + nombreAsignatura);
            }
        }
    }
}
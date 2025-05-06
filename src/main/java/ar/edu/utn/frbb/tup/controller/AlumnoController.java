package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@Valid @RequestBody AlumnoDto alumnoDto) {
        Alumno nuevoAlumno = alumnoService.crearAlumno(
            alumnoDto.getNombre(),
            alumnoDto.getApellido(),
            alumnoDto.getDni()
        );
        return ResponseEntity.ok(nuevoAlumno);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> obtenerAlumnoPorId(@PathVariable long id) {
        Alumno alumno = alumnoService.buscarAlumnoPorId(id);
        AlumnoDto alumnoDto = new AlumnoDto(
            alumno.getId(),
            alumno.getNombre(),
            alumno.getApellido(),
            alumno.getDni(),
            alumno.getAsignaturas()
        );
        return ResponseEntity.ok(alumnoDto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AlumnoDto>> buscarAlumnos(@RequestParam String q) {
        List<AlumnoDto> alumnos = alumnoService.buscarAlumnosPorCadena(q)
            .stream()
            .map(alumno -> new AlumnoDto(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni(),
                alumno.getAsignaturas()
            ))
            .toList();
        return ResponseEntity.ok(alumnos);
    }

    @GetMapping
    public ResponseEntity<List<AlumnoDto>> obtenerTodosLosAlumnos() {
        List<AlumnoDto> alumnos = alumnoService.obtenerTodosLosAlumnos()
                .stream()
                .map(alumno -> new AlumnoDto(
                    alumno.getId(),
                    alumno.getNombre(),
                    alumno.getApellido(),
                    alumno.getDni(),
                    alumno.getAsignaturas()
                ))
                .toList();
        return ResponseEntity.ok(alumnos);
    }

    @PostMapping("/{id}/asignaturas")
    public ResponseEntity<?> agregarAsignaturaAAlumno(
            @PathVariable long id,
            @RequestBody AsignaturaDto asignaturaDto) {
        alumnoService.agregarAsignaturaAAlumno(id, asignaturaDto.getMateriaId());
        return ResponseEntity.ok(
            Map.of(
                "mensaje", "Asignatura agregada correctamente al alumno",
                "alumnoId", id,
                "materiaId", asignaturaDto.getMateriaId()
            )
        );
    }

    @GetMapping("/{id}/asignaturas")
    public ResponseEntity<List<Asignatura>> obtenerAsignaturasDeAlumno(@PathVariable long id) {
        Alumno alumno = alumnoService.buscarAlumnoPorId(id);
        return ResponseEntity.ok(alumno.getAsignaturas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable long id) {
        alumnoService.eliminarAlumnoPorId(id);
        return ResponseEntity.ok(
            java.util.Collections.singletonMap("Alumno eliminado correctamente.", id)
        );
    }

    @PostMapping("/{alumnoId}/asignaturas/{asignaturaId}/cursar")
    public ResponseEntity<?> cursarAsignatura(
            @PathVariable long alumnoId,
            @PathVariable long asignaturaId) {
        Alumno alumno = alumnoService.buscarAlumnoPorId(alumnoId);
        Asignatura asignatura = alumno.getAsignaturas().stream()
                .filter(a -> asignaturaId == a.getAsignaturaId())
                .findFirst()
                .orElseThrow(() -> new ar.edu.utn.frbb.tup.exception.ResourceNotFoundException("Asignatura", String.valueOf(asignaturaId)));
        alumnoService.cursarAsignatura(alumnoId, asignatura);
        return ResponseEntity.ok(java.util.Collections.singletonMap("Asignatura cursada correctamente", asignatura));
    }

    @PostMapping("/{alumnoId}/asignaturas/{asignaturaId}/aprobar")
    public ResponseEntity<?> aprobarAsignatura(
            @PathVariable long alumnoId,
            @PathVariable long asignaturaId,
            @RequestParam int nota) {
        if (nota < 4 || nota > 10) {
            throw new ar.edu.utn.frbb.tup.exception.ValidationException("La nota debe ser mayor o igual a 4 y menor o igual a 10.");
        }
        Alumno alumno = alumnoService.buscarAlumnoPorId(alumnoId);
        Asignatura asignatura = alumno.getAsignaturas().stream()
                .filter(a -> asignaturaId == a.getAsignaturaId())
                .findFirst()
                .orElseThrow(() -> new ar.edu.utn.frbb.tup.exception.ResourceNotFoundException("Asignatura", String.valueOf(asignaturaId)));
        alumnoService.aprobarAsignatura(alumnoId, asignatura, nota);
        return ResponseEntity.ok(java.util.Collections.singletonMap("Asignatura aprobada correctamente", asignatura));
    }
    
    @DeleteMapping("/{id}/asignaturas/{asignaturaId}")
    public ResponseEntity<?> eliminarAsignaturaDeAlumno(@PathVariable long id, @PathVariable long asignaturaId) {
        alumnoService.eliminarAsignaturaDeAlumno(id, asignaturaId);
        return ResponseEntity.ok(java.util.Collections.singletonMap("Asignatura eliminada correctamente", asignaturaId));
    }
}
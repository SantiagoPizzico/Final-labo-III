package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@Valid @RequestBody ProfesorDto profesorDto) {
        Profesor nuevoProfesor = profesorService.crearProfesor(
            profesorDto.getNombre(),
            profesorDto.getApellido(),
            profesorDto.getTitulo()
        );
        return ResponseEntity.ok(nuevoProfesor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerProfesorPorId(@PathVariable long id) {
        Profesor profesor = profesorService.buscarProfesorPorId(id);
        return ResponseEntity.ok(profesor);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Profesor>> buscarProfesores(@RequestParam String q) {
        return ResponseEntity.ok(profesorService.buscarProfesoresPorCadena(q));
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodosLosProfesores() {
        List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
        return ResponseEntity.ok(profesores);
    }

    @PostMapping("/{id}/materias")
    public ResponseEntity<Void> asignarMateriaAProfesor(@PathVariable long id, @Valid @RequestBody Materia materia) {
        profesorService.asignarMateriaAProfesor(id, materia);
        return 
        ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/materias")
    public ResponseEntity<List<Materia>> obtenerMateriasDictadasPorProfesor(@PathVariable long id) {
        Profesor profesor = profesorService.buscarProfesorPorId(id);
        return ResponseEntity.ok(profesor.getMateriasDictadas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProfesor(@PathVariable long id) {
        profesorService.eliminarProfesorPorId(id);
        return ResponseEntity.ok(
            java.util.Collections.singletonMap("mensaje", "Profesor eliminado correctamente")
        );
    }
}
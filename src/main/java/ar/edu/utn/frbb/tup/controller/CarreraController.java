package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    // Endpoint para crear una nueva carrera
    @PostMapping
    public ResponseEntity<Carrera> crearCarrera(@Valid @RequestBody CarreraDto carreraDto) {
        Carrera nuevaCarrera = carreraService.crearCarrera(
            carreraDto.getNombre(),
            carreraDto.getCantidadAnios());
        return ResponseEntity.ok(nuevaCarrera);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Carrera>> buscarCarrerasPorCadena(@RequestParam("q") String cadena) {
        List<Carrera> carreras = carreraService.buscarCarrerasPorCadena(cadena);
        return ResponseEntity.ok(carreras);
    }


    @GetMapping("/{nombre}")
    public ResponseEntity<Carrera> obtenerCarreraPorNombre(@PathVariable String nombre) {
        Carrera carrera = carreraService.buscarCarreraPorNombre(nombre);
        return ResponseEntity.ok(carrera);
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> obtenerTodasLasCarreras() {
        List<Carrera> carreras = carreraService.obtenerTodasLasCarreras();
        return ResponseEntity.ok(carreras);
    }

    @PostMapping("/{nombre}/materias")
    public ResponseEntity<?> agregarMateriaACarrera(
            @PathVariable String nombre,
            @RequestBody Map<String, Integer> body) {
        int materiaId = body.get("materiaId");
        carreraService.agregarMateriaACarrera(nombre, materiaId);
        return ResponseEntity.ok(
            Map.of(
                "mensaje", "Materia agregada correctamente a la carrera",
                "carreraId", nombre,
                "materiaId", materiaId
            )
        );
    }

    @GetMapping("/{nombre}/materias")
    public ResponseEntity<List<Materia>> obtenerMateriasDeCarrera(@PathVariable String nombre) {
        List<Materia> materias = carreraService.obtenerMaterias(nombre);
        return ResponseEntity.ok(materias);
    }
}
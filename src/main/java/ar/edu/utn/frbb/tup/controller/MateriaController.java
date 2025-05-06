package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody MateriaDto materiaDto) {
        Materia nuevaMateria = materiaService.crearMateria(
            materiaDto.getNombre(),
            materiaDto.getAnio(),
            materiaDto.getCuatrimestre(),
            materiaDto.getProfesorId()
        );
        return ResponseEntity.ok(nuevaMateria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerMateriaPorId(@PathVariable int id) {
        Materia materia = materiaService.buscarMateriaPorId(id);
        return ResponseEntity.ok(materia);
    }

    @GetMapping
    public ResponseEntity<List<Materia>> obtenerTodasLasMaterias() {
        List<Materia> materias = materiaService.obtenerTodasLasMaterias();
        return ResponseEntity.ok(materias);
    }

    @PostMapping("/{id}/correlatividades")
    public ResponseEntity<?> agregarCorrelatividad(
            @PathVariable int id,
            @RequestBody Materia correlativa) {
        Materia correlativaSimple = new Materia();
        correlativaSimple.setMateriaId(correlativa.getMateriaId());
        correlativaSimple.setNombre(correlativa.getNombre());
        materiaService.agregarCorrelatividad(id, correlativaSimple);
        return ResponseEntity.ok(
            java.util.Collections.singletonMap("Correlativa agregada correctamente.", correlativaSimple)
        );
    }
}
package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdmController {

    private static final String PASSWORD = "a9B7c2D4e5";

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private MateriaService materiaService;

    @GetMapping("/data/{password}")
    public ResponseEntity<?> getData(@PathVariable String password) {
        if (!PASSWORD.equals(password)) {
            return ResponseEntity.status(403).body(Map.of("error", "Acceso denegado"));
        }
        List<Alumno> alumnos = alumnoService.obtenerTodosLosAlumnos();
        List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
        List<Carrera> carreras = carreraService.obtenerTodasLasCarreras();
        List<Materia> materias = materiaService.obtenerTodasLasMaterias();

        Map<String, Object> data = new HashMap<>();
        data.put("alumnos", alumnos);
        data.put("profesores", profesores);
        data.put("carreras", carreras);
        data.put("materias", materias);

        return ResponseEntity.ok(data);
    }
}
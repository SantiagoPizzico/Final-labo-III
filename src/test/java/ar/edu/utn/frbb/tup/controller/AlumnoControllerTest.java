package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearAlumno_ok() throws Exception {
        AlumnoDto dto = new AlumnoDto(0, "Juan", "Perez", 12345678L);
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Mockito.when(alumnoService.crearAlumno(any(), any(), any())).thenReturn(alumno);

        mockMvc.perform(post("/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerAlumnoPorId_ok() throws Exception {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        alumno.setId(1L);
        Mockito.when(alumnoService.buscarAlumnoPorId(1L)).thenReturn(alumno);

        mockMvc.perform(get("/alumnos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void buscarAlumnos_ok() throws Exception {
        Alumno alumno = new Alumno("Juan", "Perez", 12345678L);
        Mockito.when(alumnoService.buscarAlumnosPorCadena("Juan")).thenReturn(List.of(alumno));

        mockMvc.perform(get("/alumnos/buscar?q=Juan"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarAsignaturaAAlumno_ok() throws Exception {
        AsignaturaDto dto = new AsignaturaDto();
        dto.setMateriaId(2);
        Mockito.doNothing().when(alumnoService).agregarAsignaturaAAlumno(eq(1L), eq(2));

        mockMvc.perform(post("/alumnos/1/asignaturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarAlumno_ok() throws Exception {
        Mockito.doNothing().when(alumnoService).eliminarAlumnoPorId(1L);

        mockMvc.perform(delete("/alumnos/1"))
                .andExpect(status().isOk());
    }
}
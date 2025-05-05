package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfesorController.class)
class ProfesorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesorService profesorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearProfesor_ok() throws Exception {
        ProfesorDto dto = new ProfesorDto();
        dto.setNombre("Luciano");
        dto.setApellido("Salotto");
        dto.setTitulo("Licenciado");
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Mockito.when(profesorService.crearProfesor(any(), any(), any())).thenReturn(profesor);

        mockMvc.perform(post("/profesores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProfesorPorId_ok() throws Exception {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Mockito.when(profesorService.buscarProfesorPorId(1L)).thenReturn(profesor);

        mockMvc.perform(get("/profesores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Luciano"));
    }

    @Test
    void buscarProfesores_ok() throws Exception {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Licenciado");
        Mockito.when(profesorService.buscarProfesoresPorCadena("Luciano")).thenReturn(List.of(profesor));

        mockMvc.perform(get("/profesores/buscar?q=Luciano"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarProfesor_ok() throws Exception {
        Mockito.doNothing().when(profesorService).eliminarProfesorPorId(1L);

        mockMvc.perform(delete("/profesores/1"))
                .andExpect(status().isOk());
    }
}
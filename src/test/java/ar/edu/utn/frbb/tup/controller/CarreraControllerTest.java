package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
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

@WebMvcTest(CarreraController.class)
class CarreraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarreraService carreraService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearCarrera_ok() throws Exception {
        CarreraDto dto = new CarreraDto();
        dto.setNombre("Tecnicatura Universitaria en Programacion");
        dto.setCantidadAnios(3);
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        Mockito.when(carreraService.crearCarrera(any(), any(Integer.class))).thenReturn(carrera);

        mockMvc.perform(post("/carreras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerCarreraPorNombre_ok() throws Exception {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        Mockito.when(carreraService.buscarCarreraPorNombre("Tecnicatura Universitaria en Programacion")).thenReturn(carrera);

        mockMvc.perform(get("/carreras/Tecnicatura Universitaria en Programacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tecnicatura Universitaria en Programacion"));
    }

    @Test
    void obtenerTodasLasCarreras_ok() throws Exception {
        Carrera carrera = new Carrera("Tecnicatura Universitaria en Programacion", 3);
        Mockito.when(carreraService.obtenerTodasLasCarreras()).thenReturn(List.of(carrera));

        mockMvc.perform(get("/carreras"))
                .andExpect(status().isOk());
    }
}

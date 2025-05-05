package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
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

@WebMvcTest(MateriaController.class)
class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MateriaService materiaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearMateria_ok() throws Exception {
        MateriaDto dto = new MateriaDto();
        dto.setNombre("Laboratorio 1");
        dto.setAnio(1);
        dto.setCuatrimestre(1);
        dto.setProfesorId(1L);
        Materia materia = new Materia("Laboratorio 1", 1, 1, null);
        Mockito.when(materiaService.crearMateria(any(), any(Integer.class), any(Integer.class), any(Long.class))).thenReturn(materia);

        mockMvc.perform(post("/materias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerMateriaPorId_ok() throws Exception {
        Materia materia = new Materia("Laboratorio 1", 1, 1, null);
        Mockito.when(materiaService.buscarMateriaPorId(1)).thenReturn(materia);

        mockMvc.perform(get("/materias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laboratorio 1"));
    }

    @Test
    void obtenerTodasLasMaterias_ok() throws Exception {
        Materia materia = new Materia("Laboratorio 1", 1, 1, null);
        Mockito.when(materiaService.obtenerTodasLasMaterias()).thenReturn(List.of(materia));

        mockMvc.perform(get("/materias"))
                .andExpect(status().isOk());
    }
}

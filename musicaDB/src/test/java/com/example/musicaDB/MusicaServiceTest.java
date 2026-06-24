package com.example.musicaDB;

import com.example.musicaDB.dto.MusicaRequestDTO;
import com.example.musicaDB.dto.MusicaResponseDTO;
import com.example.musicaDB.model.Musica;
import com.example.musicaDB.repository.MusicaRepository;
import com.example.musicaDB.service.MusicaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MusicaServiceTest {

    @Mock
    private MusicaRepository musicaRepository;

    @InjectMocks
    private MusicaService musicaService;

    private Musica musicaMock;
    private MusicaRequestDTO musicaRequestDTOMock;

    @BeforeEach
    void setUp() {
        // Preparamos los objetos falsos antes de cada test
        musicaMock = new Musica(1L, "Tití Me Preguntó", "Bad Bunny", "Un Verano Sin Ti", "Reggaeton", 199, "2022-05-06");
        musicaRequestDTOMock = new MusicaRequestDTO("Tití Me Preguntó", "Bad Bunny", "Un Verano Sin Ti", "Reggaeton", 199, "2022-05-06");
    }

    @Test
    void guardarMusica_Exitoso() {
        // GIVEN: El repositorio finge guardar el objeto y retorna musicaMock
        when(musicaRepository.save(any(Musica.class))).thenReturn(musicaMock);

        // WHEN: Llamada real al servicio
        MusicaResponseDTO resultado = musicaService.guardar(musicaRequestDTOMock);

        // THEN: Aseguramos que los mapeos y datos sean los correctos
        assertNotNull(resultado);
        assertEquals("Tití Me Preguntó", resultado.getNombreMusica());
        assertEquals("Un Verano Sin Ti", resultado.getAlbum());
        assertEquals(199, resultado.getDuracion());
        
        verify(musicaRepository, times(1)).save(any(Musica.class));
    }

    @Test
    void buscarPorId_Exitoso() {
        // GIVEN
        when(musicaRepository.findById(1L)).thenReturn(Optional.of(musicaMock));

        // WHEN
        Optional<MusicaResponseDTO> resultado = musicaService.buscarPorId(1L);

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Tití Me Preguntó", resultado.get().getNombreMusica());
    }

    @Test
    void eliminar_Exitoso() {
        // GIVEN: Para métodos void, doNothing() es el comportamiento por defecto de un mock, 
        // pero la prueba sirve para verificar que el flujo pase por ahí.
        doNothing().when(musicaRepository).deleteById(1L);

        // WHEN
        musicaService.eliminar(1L);

        // THEN: Verificamos que el método de borrar en el repositorio se ejecutó una vez
        verify(musicaRepository, times(1)).deleteById(1L);
    }
}

package com.example.musicaDB;

import com.example.musicaDB.dto.ArtistaRequestDTO;
import com.example.musicaDB.dto.ArtistaResponseDTO;
import com.example.musicaDB.model.Artista;
import com.example.musicaDB.repository.ArtistaRepository;
import com.example.musicaDB.service.ArtistaService;

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
class ArtistaServiceTest {

    @Mock
    private ArtistaRepository artistaRepository;

    @InjectMocks
    private ArtistaService artistaService;

    private Artista artistaMock;
    private ArtistaRequestDTO artistaRequestDTOMock;

    @BeforeEach
    void setUp() {
        // Configuramos datos de prueba que se usarán antes de cada test
        artistaMock = new Artista(1L, "Benito Martinez", "Bad Bunny", "Puertorriqueña", 30, true, "Reggaeton");
        
        artistaRequestDTOMock = new ArtistaRequestDTO("Benito Martinez", "Bad Bunny", "Puertorriqueña", 30, true, "Reggaeton");
    }

    @Test
    void guardarArtista_Exitoso() {
        // GIVEN (Dado que): Simulamos que el repositorio guardará y devolverá el artistaMock
        when(artistaRepository.save(any(Artista.class))).thenReturn(artistaMock);

        // WHEN (Cuando): Llamamos al método real del servicio
        ArtistaResponseDTO resultado = artistaService.saveArtista(artistaRequestDTOMock);

        // THEN (Entonces): Verificamos con Asserts que el resultado es el esperado
        assertNotNull(resultado);
        assertEquals("Bad Bunny", resultado.getNombreArtistico());
        assertEquals("Reggaeton", resultado.getGeneroMusical());
        
        // Verificamos que el repositorio fue llamado exactamente una vez
        verify(artistaRepository, times(1)).save(any(Artista.class));
    }

    @Test
    void obtenerPorId_Exitoso() {
        // GIVEN: Simulamos que al buscar el ID 1, el repositorio devuelve el artistaMock
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artistaMock));

        // WHEN: Ejecutamos el método del servicio
        Optional<ArtistaResponseDTO> resultado = artistaService.obtenerPorId(1L);

        // THEN: Validamos que exista y sus datos sean correctos
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdArtista());
        assertEquals("Benito Martinez", resultado.get().getNombreArtista());
    }

    @Test
    void obtenerPorId_NoEncontrado() {
        // GIVEN: Simulamos que el ID no existe en la BD
        when(artistaRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN: Consultamos el servicio
        Optional<ArtistaResponseDTO> resultado = artistaService.obtenerPorId(99L);

        // THEN: El resultado debe estar vacío
        assertFalse(resultado.isPresent());
    }
}
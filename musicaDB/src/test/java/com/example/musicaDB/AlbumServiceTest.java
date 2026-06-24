package com.example.musicaDB;

import com.example.musicaDB.dto.AlbumRequestDTO;
import com.example.musicaDB.dto.AlbumResponseDTO;
import com.example.musicaDB.model.Album;
import com.example.musicaDB.model.Artista;
import com.example.musicaDB.repository.AlbumRepository;
import com.example.musicaDB.repository.ArtistaRepository;
import com.example.musicaDB.service.AlbumService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock // Album necesita validar que el artista exista, así que también lo mockeamos
    private ArtistaRepository artistaRepository;

    @InjectMocks
    private AlbumService albumService;

    private Album albumMock;
    private Artista artistaMock;
    private AlbumRequestDTO albumRequestDTOMock;

    @BeforeEach
    void setUp() {
        // Datos simulados (Mocks)
        artistaMock = new Artista(1L, "Benito Martinez", "Bad Bunny", "Puertorriqueña", 30, true, "Reggaeton");
        albumMock = new Album(1L, "Un Verano Sin Ti", LocalDate.of(2022, 5, 6), artistaMock);
        albumRequestDTOMock = new AlbumRequestDTO("Un Verano Sin Ti", LocalDate.of(2022, 5, 6), "Bad Bunny");
    }

    @Test
    void guardarAlbum_Exitoso() {
        // GIVEN: Simulamos que al buscar al artista "Bad Bunny", la BD nos devuelve la lista con artistaMock
        when(artistaRepository.findByNombreArtisticoContainingIgnoreCase(anyString())).thenReturn(List.of(artistaMock));
        // Simulamos que al guardar el álbum, nos devuelve el albumMock
        when(albumRepository.save(any(Album.class))).thenReturn(albumMock);

        // WHEN: Ejecutamos el método del servicio
        AlbumResponseDTO resultado = albumService.guardar(albumRequestDTOMock);

        // THEN: Validamos que la respuesta contenga los datos correctos
        assertNotNull(resultado);
        assertEquals("Un Verano Sin Ti", resultado.getNombreAlbum());
        assertEquals("Bad Bunny", resultado.getNombreArtista());
        
        // Verificamos que los repositorios fueron llamados exactamente una vez
        verify(artistaRepository, times(1)).findByNombreArtisticoContainingIgnoreCase(anyString());
        verify(albumRepository, times(1)).save(any(Album.class));
    }

   
}
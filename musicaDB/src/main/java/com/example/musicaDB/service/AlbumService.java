package com.example.musicaDB.service;

import com.example.musicaDB.dto.AlbumRequestDTO;
import com.example.musicaDB.dto.AlbumResponseDTO;
import com.example.musicaDB.model.Album;
import com.example.musicaDB.model.Artista;
import com.example.musicaDB.repository.AlbumRepository;
import com.example.musicaDB.repository.ArtistaRepository; 

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
    
    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    // Método que convierte Album a AlbumResponseDTO
    private AlbumResponseDTO convertirAResponseDTO(Album album) {
        return new AlbumResponseDTO(
            album.getIdAlbum(),
            album.getNombreAlbum(),
            album.getFechaPublicasionAlbum(),
            album.getArtista().getNombreArtistico() // Mapeamos el nombre del artista al DTO de respuesta
        );
    }

    // Método que convierte AlbumRequestDTO a Album
    private Album convertirAEntidad(AlbumRequestDTO albumRequestDTO) {
        Album album = new Album();
        album.setNombreAlbum(albumRequestDTO.getNombreAlbum());
        album.setFechaPublicasionAlbum(albumRequestDTO.getFechaPublicasionAlbum());

        // Buscamos el artista en la base de datos usando el String que viene en el DTO
        Artista artista = artistaRepository.findByNombreArtisticoContainingIgnoreCase(albumRequestDTO.getArtista())
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con el nombre: " + albumRequestDTO.getArtista()));
        
        album.setArtista(artista);
        return album;
    }

    // Guardar
    public AlbumResponseDTO guardar(AlbumRequestDTO dto) {
        try {
            Album album = convertirAEntidad(dto);
            return convertirAResponseDTO(albumRepository.save(album));
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el álbum: " + e.getMessage());
        }
    }

    // Actualizar
    public AlbumResponseDTO actualizar(Long id, AlbumRequestDTO dto) {
        try {
            Album existente = albumRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Álbum no encontrado con id: " + id));

            existente.setNombreAlbum(dto.getNombreAlbum());
            existente.setFechaPublicasionAlbum(dto.getFechaPublicasionAlbum());

            // Actualizamos el artista también
            Artista artista = artistaRepository.findByNombreArtisticoContainingIgnoreCase(dto.getArtista())
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Artista no encontrado con el nombre: " + dto.getArtista()));
            
            existente.setArtista(artista);

            return convertirAResponseDTO(albumRepository.save(existente));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el álbum: " + e.getMessage());
        }
    }

    // Eliminar
    public void eliminar(Long id) {
        try {
            albumRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el álbum: " + e.getMessage());
        }
    }

    // Listar todo
    public List<AlbumResponseDTO> listarTodos() {
        try {
            return albumRepository.findAll()
                    .stream()
                    .map(this::convertirAResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los álbumes: " + e.getMessage());
        }
    }

    // Buscar por ID
    public Optional<AlbumResponseDTO> buscarPorId(Long id) {
        try {
            return albumRepository.findById(id)
                    .map(this::convertirAResponseDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el álbum por ID: " + e.getMessage());
        }
    }

    // --- Consultas Personalizadas (Sincronizadas con tu Repository) ---

    public List<AlbumResponseDTO> buscarPorNombreAlbum(String nombreAlbum) {
        try {
            return albumRepository.findByNombreAlbumContainingIgnoreCase(nombreAlbum)
                    .stream()
                    .map(this::convertirAResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el álbum por nombre: " + e.getMessage());
        }
    }

    public List<AlbumResponseDTO> buscarPorNombreDeArtista(String nombreArtista) {
        try {
            return albumRepository.buscarPorNombreDeArtista(nombreArtista)
                    .stream()
                    .map(this::convertirAResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar álbumes por artista: " + e.getMessage());
        }
    }

    public List<AlbumResponseDTO> buscarPorFechaPosteriorA(LocalDate fecha) {
        try {
            return albumRepository.buscarPorFechaPosteriorA(fecha)
                    .stream()
                    .map(this::convertirAResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar álbumes por fecha: " + e.getMessage());
        }
    }

    // --- Native Queries (Sincronizadas con tu Repository) ---

    public List<AlbumResponseDTO> buscarPorIdArtista(Long idArtista) {
        try {
            return albumRepository.buscarPorIdArtistaNative(idArtista)
                    .stream()
                    .map(this::convertirAResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar álbumes por ID de artista: " + e.getMessage());
        }
    }

    public List<AlbumResponseDTO> ordenarPorFechaPublicacionDesc() {
        try {
            return albumRepository.ordenarPorFechaPublicacionDescNative()
                    .stream()
                    .map(this::convertirAResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al ordenar álbumes por fecha: " + e.getMessage());
        }
    }
}
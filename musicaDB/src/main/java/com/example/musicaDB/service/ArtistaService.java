package com.example.musicaDB.service;

import com.example.musicaDB.model.Artista;
import com.example.musicaDB.repository.ArtistaRepository;
import com.example.musicaDB.dto.ArtistaResponseDTO;
import com.example.musicaDB.dto.ArtistaRequestDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    //metodo que convierte artista a ArtistaResponseDTO
    private ArtistaResponseDTO convertirAResponseDTO(Artista artista) {
        return new ArtistaResponseDTO(
            artista.getIdArtista(),
            artista.getNombreArtista(),
            artista.getNombreArtistico(),
            artista.getNacionalidadArtista(),
            artista.getEdad(),
            artista.getEstado(),
            artista.getGeneroMusical()
        );
    }

    //metodo que convierte ArtistaRequestDTO a Artista
    private Artista convertirAEntidad(ArtistaRequestDTO artistaRequestDTO) {
        Artista artista = new Artista();
        artista.setNombreArtista(artistaRequestDTO.getNombreArtista());
        artista.setNombreArtistico(artistaRequestDTO.getNombreArtistico());
        artista.setNacionalidadArtista(artistaRequestDTO.getNacionalidadArtista());
        artista.setEdad(artistaRequestDTO.getEdad());
        artista.setEstado(artistaRequestDTO.getEstado());
        artista.setGeneroMusical(artistaRequestDTO.getGeneroMusical());
        return artista;
    }

    //Guardar
    public ArtistaResponseDTO saveArtista(ArtistaRequestDTO artistaRequestDTO) {
        try {
            Artista artista = convertirAEntidad(artistaRequestDTO);
            return convertirAResponseDTO(artistaRepository.save(artista));
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el artista: " + e.getMessage());
        }
        
    }

    //Actualizar
    public ArtistaResponseDTO updateArtista(Long id, ArtistaRequestDTO artista) {
        try{
            Artista existe = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + id));
            existe.setNombreArtista(artista.getNombreArtista());
            existe.setNombreArtistico(artista.getNombreArtistico());
            existe.setNacionalidadArtista(artista.getNacionalidadArtista());
            existe.setEdad(artista.getEdad());
            existe.setEstado(artista.getEstado());
            existe.setGeneroMusical(artista.getGeneroMusical());
            return convertirAResponseDTO(artistaRepository.save(existe));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el artista: " + e.getMessage());
        }
    }

    //Eliminar
    public void deleteArtista(Long id) {
        try {
            artistaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el artista: " + e.getMessage());
        }

    }

    //Listar todo
    public List<ArtistaResponseDTO> listarArtistas() {
        try{
            return artistaRepository.findAll()
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los artistas: " + e.getMessage());
        }
    }

    //Buscar por ID
    public Optional<ArtistaResponseDTO> obtenerPorId(Long id) {
        try {
            return artistaRepository.findById(id)
            .map(this::convertirAResponseDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el artista por ID: " + e.getMessage());
        }
    }

    //Buscar por nombre artístico
    public List<ArtistaResponseDTO> buscarPorNombreArtistico(String nombreArtistico) {
        try {
            return artistaRepository.findByNombreArtisticoContainingIgnoreCase(nombreArtistico)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por nombre artístico: " + e.getMessage());
        }
    }

    //Buscar por género musical
    public List<ArtistaResponseDTO> buscarPorGeneroMusical(String generoMusical) {
        try {
            return artistaRepository.findByGeneroMusical(generoMusical)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por género musical: " + e.getMessage());
        }
    }

    //Buscar por nacionalidad
    public List<ArtistaResponseDTO> buscarPorNacionalidad(String nacionalidad) {
        try {
            return artistaRepository.findByNacionalidad(nacionalidad)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por nacionalidad: " + e.getMessage());
        }
    }

    //Buscar por nombre
    public List<ArtistaResponseDTO> buscarPorNombre(String nombre) {
        try {
            return artistaRepository.findByNombre(nombre)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por nombre: " + e.getMessage());
        }
    }

    //Buscar por edad
    public List<ArtistaResponseDTO> buscarPorEdad(Integer edad) {
        try {
            return artistaRepository.findByEdad(edad)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por edad: " + e.getMessage());
        }
    }

    //Buscar por estado 
    public List<ArtistaResponseDTO> buscarPorEstado(Boolean estado) {
        try {
            return artistaRepository.findByEstado(estado)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por estado: " + e.getMessage());
        }
    }

    //Ordenar por nombre artístico
    public List<ArtistaResponseDTO> ordenarPorNombreArtistico() {
        try {
            return artistaRepository.ordenarPorNombreArtistico()
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al ordenar los artistas por nombre artístico: " + e.getMessage());
        }
    }

    //Ordenar por edad ascendente
    public List<ArtistaResponseDTO> ordenarPorEdadAsc() {
        try {
            return artistaRepository.ordenarPorEdadAsc()
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al ordenar los artistas por edad ascendente: " + e.getMessage());
        }
    }

    //Ordenar por edad descendente
    public List<ArtistaResponseDTO> ordenarPorEdadDesc() {
        try {
            return artistaRepository.ordenarPorEdadDesc()
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al ordenar los artistas por edad descendente: " + e.getMessage());
        }
    }

    //Ordenar por género musical
    public List<ArtistaResponseDTO> ordenarPorGeneroMusical() {
        try {
            return artistaRepository.ordenarPorGeneroMusical()
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al ordenar los artistas por género musical: " + e.getMessage());
        }
    }
}

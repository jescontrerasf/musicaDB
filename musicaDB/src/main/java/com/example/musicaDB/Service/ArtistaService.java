package com.example.musicaDB.Service;

import com.example.musicaDB.Model.Artista;
import com.example.musicaDB.Repository.ArtistaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    //Guardar
    public Artista saveArtista(Artista artista) {
        try {
            return artistaRepository.save(artista);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el artista: " + e.getMessage());
        }
        
    }

    //Actualizar
    public Artista updateArtista(Long id, Artista artista) {
        try{
            Artista existe = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + id));
            existe.setNombreArtista(artista.getNombreArtista());
            existe.setNombreArtistico(artista.getNombreArtistico());
            existe.setNacionalidadArtista(artista.getNacionalidadArtista());
            existe.setEdad(artista.getEdad());
            existe.setEstado(artista.getEstado());
            existe.setGeneroMusical(artista.getGeneroMusical());
            return artistaRepository.save(existe);
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
    public List<Artista> listarArtistas() {
        try{
            return artistaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los artistas: " + e.getMessage());
        }
    }

    //Buscar por ID
    public Optional<Artista> obtenerPorId(Long id) {
        try {
            return artistaRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el artista por ID: " + e.getMessage());
        }
    }

    //Buscar por nombre artístico
    public List<Artista> buscarPorNombreArtistico(String nombreArtistico) {
        try {
            return artistaRepository.findByNombreArtisticoContainingIgnoreCase(nombreArtistico);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por nombre artístico: " + e.getMessage());
        }
    }

    //Buscar por género musical
    public List<Artista> buscarPorGeneroMusical(String generoMusical) {
        try {
            return artistaRepository.findByGeneroMusical(generoMusical);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por género musical: " + e.getMessage());
        }
    }

    //Buscar por nacionalidad
    public List<Artista> buscarPorNacionalidad(String nacionalidad) {
        try {
            return artistaRepository.findByNacionalidad(nacionalidad);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por nacionalidad: " + e.getMessage());
        }
    }

    //Buscar por nombre
    public List<Artista> buscarPorNombre(String nombre) {
        try {
            return artistaRepository.findByNombre(nombre);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por nombre: " + e.getMessage());
        }
    }

    //Buscar por edad
    public List<Artista> buscarPorEdad(Integer edad) {
        try {
            return artistaRepository.findByEdad(edad);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por edad: " + e.getMessage());
        }
    }

    //Buscar por estado 
    public List<Artista> buscarPorEstado(Boolean estado) {
        try {
            return artistaRepository.findByEstado(estado);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el artista por estado: " + e.getMessage());
        }
    }
}

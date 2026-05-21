package com.example.musicaDB.service;

import com.example.musicaDB.dto.MusicaRequestDTO;
import com.example.musicaDB.dto.MusicaResponseDTO;
import com.example.musicaDB.model.Musica;
import com.example.musicaDB.repository.MusicaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MusicaService {

    private final MusicaRepository musicaRepository;


    // --- Mapeos ---

    private Musica toEntity(MusicaRequestDTO dto) {
        Musica musica = new Musica();
        musica.setNombreMusica(dto.getNombreCancion());
        musica.setAlbum(dto.getAlbum().getNombreAlbum());
        musica.setArtista(dto.getArtista().getNombreArtistico());
        musica.setGeneroMusical(dto.getGeneroMusical());
        musica.setDuracion(dto.getDuracion());
        musica.setFechaPublicacion(dto.getFechaPublicacion());
        return musica;
    }

    private MusicaResponseDTO toDTO(Musica musica) {
        return new MusicaResponseDTO(
            musica.getIdMusica(),
            musica.getNombreMusica(),
            musica.getArtista(),
            musica.getAlbum(),
            musica.getGeneroMusical(),
            musica.getDuracion(),
            musica.getFechaPublicacion()
        );
    }

    // --- CRUD base ---

    public MusicaResponseDTO guardar(MusicaRequestDTO dto) {
        return toDTO(musicaRepository.save(toEntity(dto)));
    }

    public Optional<MusicaResponseDTO> buscarPorId(Long id) {
        return musicaRepository.findById(id).map(this::toDTO);
    }

    public List<MusicaResponseDTO> listarTodos() {
        return musicaRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MusicaResponseDTO actualizar(Long id, MusicaRequestDTO dto) {
        Musica existente = musicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música no encontrada con id: " + id));

        existente.setNombreMusica(dto.getNombreCancion());
        existente.setArtista(dto.getArtista().getNombreArtistico());
        existente.setAlbum(dto.getAlbum().getNombreAlbum());
        existente.setGeneroMusical(dto.getGeneroMusical());
        existente.setDuracion(dto.getDuracion());
        existente.setFechaPublicacion(dto.getFechaPublicacion());

        return toDTO(musicaRepository.save(existente));
    }

    public void eliminar(Long id) {
        musicaRepository.deleteById(id);
    }

    // --- Query Methods ---

    public List<MusicaResponseDTO> buscarPorNombreMusica(String nombreMusica) {
        return musicaRepository.findByNombreMusicaContainingIgnoreCase(nombreMusica)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MusicaResponseDTO> buscarPorGeneroMusical(String generoMusical) {
        return musicaRepository.findByGeneroMusicalIgnoreCase(generoMusical)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- JPQL ---

    public List<MusicaResponseDTO> buscarPorArtista(String artista) {
        return musicaRepository.buscarPorArtista(artista)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MusicaResponseDTO> ordenarPorDuracionDesc() {
        return musicaRepository.ordenarPorDuracionDesc()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- Native Query ---

    public List<MusicaResponseDTO> buscarPorAlbum(String album) {
        return musicaRepository.buscarPorAlbumNative(album)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MusicaResponseDTO> ordenarPorFechaPublicacionAsc() {
        return musicaRepository.ordenarPorFechaPublicacionAscNative()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
}
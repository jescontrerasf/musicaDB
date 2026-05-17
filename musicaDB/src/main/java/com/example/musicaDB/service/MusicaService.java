package com.example.musicaDB.service;

import com.example.musicaDB.model.Musica;
import com.example.musicaDB.repository.MusicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;

    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    public Musica guardar(Musica musica) {
        return musicaRepository.save(musica);
    }

    public Optional<Musica> buscarPorId(Long id) {
        return musicaRepository.findById(id);
    }

    public List<Musica> listarTodos() {
        return musicaRepository.findAll();
    }

    public Musica actualizar(Long id, Musica musica) {
        Musica existente = musicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro una cancion con el id: " + id));

        existente.setNombreMusica(musica.getNombreMusica());
        existente.setArtista(musica.getArtista());
        existente.setAlbum(musica.getAlbum());
        existente.setGeneroMusical(musica.getGeneroMusical());
        existente.setDuracion(musica.getDuracion());
        existente.setFechaPublicacion(musica.getFechaPublicacion());

        return musicaRepository.save(existente);
    }

    public void eliminar(Long id) {
        musicaRepository.deleteById(id);
    }

    // ---- Query Methods ----

    public List<Musica> buscarPorNombreMusica(String nombreMusica) {
        return musicaRepository.findByNombreMusicaContainingIgnoreCase(nombreMusica);
    }

    public List<Musica> buscarPorGeneroMusical(String generoMusical) {
        return musicaRepository.findByGeneroMusicalIgnoreCase(generoMusical);
    }

    // ---- JPQL ----

    public List<Musica> buscarPorArtista(String artista) {
        return musicaRepository.buscarPorArtista(artista);
    }

    public List<Musica> ordenarPorDuracionDesc() {
        return musicaRepository.ordenarPorDuracionDesc();
    }

    // ---- Native Query ----

    public List<Musica> buscarPorAlbum(String album) {
        return musicaRepository.buscarPorAlbumNative(album);
    }

    public List<Musica> ordenarPorFechaPublicacionAsc() {
        return musicaRepository.ordenarPorFechaPublicacionAscNative();
    }
}
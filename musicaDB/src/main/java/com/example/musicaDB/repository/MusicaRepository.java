package com.example.musicaDB.repository;

import com.example.musicaDB.model.Musica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long>{

    // Buscar música por nombre, ignorando mayúsculas/minúsculas
    List<Musica> findByNombreMusicaContainingIgnoreCase(String nombreMusica);

    // Buscar música por género musical
    List<Musica> findByGeneroMusicalIgnoreCase(String generoMusical);


    // Buscar por artista
    @Query("SELECT m FROM Musica m WHERE LOWER(m.artista) LIKE LOWER(CONCAT('%', :artista, '%'))")
    List<Musica> buscarPorArtista(@Param("artista") String artista);

    // Ordenar por duración descendente
    @Query("SELECT m FROM Musica m ORDER BY m.duracion DESC")
    List<Musica> ordenarPorDuracionDesc();


    // Buscar por álbum
    @Query(value = "SELECT * FROM musica WHERE album = :album", nativeQuery = true)
    List<Musica> buscarPorAlbumNative(@Param("album") String album);

    // Ordenar por fecha de publicación ascendente
    @Query(value = "SELECT * FROM musica ORDER BY fecha_publicacion ASC", nativeQuery = true)
    List<Musica> ordenarPorFechaPublicacionAscNative();
}

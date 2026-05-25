package com.example.musicaDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.musicaDB.model.Album;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    //QUERY METHOD: Buscar álbum por nombre, ignorando mayúsculas/minúsculas
    List<Album> findByNombreAlbumContainingIgnoreCase(String nombreAlbum);

    //Buscar álbumes buscando por el nombre del artista (Navegando la relación)
    @Query("SELECT a FROM Album a WHERE LOWER(a.artista.nombreArtista) LIKE LOWER(CONCAT('%', :nombreArtista, '%'))")
    List<Album> buscarPorNombreDeArtista(@Param("nombreArtista") String nombreArtista);

    //Buscar álbumes publicados desde una fecha específica en adelante
    @Query("SELECT a FROM Album a WHERE a.fechaPublicasionAlbum >= :fecha")
    List<Album> buscarPorFechaPosteriorA(@Param("fecha") LocalDate fecha);

    //Buscar por el ID del artista directamente en la base de datos
    @Query(value = "SELECT * FROM album WHERE id_artista = :idArtista", nativeQuery = true)
    List<Album> buscarPorIdArtistaNative(@Param("idArtista") Long idArtista);

    //Ordenar por fecha de publicación de los más nuevos a los más viejos
    @Query(value = "SELECT * FROM album ORDER BY fecha_publicasion_album DESC", nativeQuery = true)
    List<Album> ordenarPorFechaPublicacionDescNative();
}
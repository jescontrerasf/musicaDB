package com.example.musicaDB.Repository;

import com.example.musicaDB.Model.Artista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    
    //Tipo 1 QUERY METHODS
    //Buscar por nombre artístico
    List<Artista> findByNombreArtisticoContainingIgnoreCase(String nombreArtistico);

    //Buscar por genero musical
    List<Artista> findByGeneroMusical(String generoMusical);

    //Buscar por artista estado
    List<Artista> findByEstado(Boolean estado);

    //Tipo 2 JPQL
    //Buscar artista por nacionalidad
    //Artista = clase java, a.nacionalidadArtista = atributo de la clase Artista
    //:nacionalidad = parámetro nombrado, @Param lo vincula con el método
    @Query("SELECT a FROM Artista a WHERE a.nacionalidadArtista = :nacionalidad")
    List<Artista> findByNacionalidad(@Param("nacionalidad") String nacionalidad);

    //Buscar artista por nombre
    @Query("SELECT a FROM Artista a WHERE a.nombreArtista = :nombre")
    List<Artista> findByNombre(@Param("nombre") String nombre);

    //Buscar artista por edad
    @Query("SELECT a FROM Artista a WHERE a.edad = :edad")
    List<Artista> findByEdad(@Param("edad") Integer edad);
    
    //Tipo 3 Native Query
    //Ordenar artistas por nombre artístico
    @Query(
        value = "SELECT * FROM artista ORDER BY nombre_artistico ASC", 
        nativeQuery = true)
    List<Artista> ordenarPorNombreArtistico();

    //Ordenar por edad ascendente
    @Query(
        value = "SELECT * FROM artista ORDER BY edad ASC", 
        nativeQuery = true)
    List<Artista> ordenarPorEdadAsc();

    //Ordenar por edad descendente
    @Query(
        value = "SELECT * FROM artista ORDER BY edad DESC", 
        nativeQuery = true)
    List<Artista> ordenarPorEdadDesc();

    //Ordenar por genero musical
    @Query(
        value = "SELECT * FROM artista ORDER BY genero_musical ASC", 
        nativeQuery = true)
    List<Artista> ordenarPorGeneroMusical();

}

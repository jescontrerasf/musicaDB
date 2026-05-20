package com.example.musicaDB.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artista")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtista;

    @Column(name = "nombre_artista", nullable = false)
    private String nombreArtista;

    @Column(name = "nombre_artistico", nullable = false, unique = true)
    private String nombreArtistico;

    @Column(name = "nacionalidad_artista", nullable = false)
    private String nacionalidadArtista;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "genero_musical")
    private String generoMusical;

}

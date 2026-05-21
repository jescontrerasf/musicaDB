package com.example.musicaDB.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "musica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMusica;

    @Column(name = "nombre_musica", nullable = false)
    private String nombreMusica;

    @Column(name = "artista", nullable = false)
    private Artista artista;

    @Column(name = "album", nullable = true)
    private Album album;

    @Column(name = "genero_musical", nullable = false)
    private String generoMusical;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name = "fecha_publicacion", nullable = false)
    private String fechaPublicacion;
}

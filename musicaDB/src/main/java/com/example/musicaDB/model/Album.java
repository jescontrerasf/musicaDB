package com.example.musicaDB.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "album")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAlbum;

    @Column(name = "nombre_album", nullable = false)
    private String nombreAlbum;

    @PastOrPresent(message = "La fecha de publicación no puede ser en el futuro")
    @Column(name = "fecha_publicasion_album", nullable = false)
    private String fechaPublicasionAlbum;

    @ManyToOne
    @JoinColumn(name = "id_artista", nullable = false)
    private Artista artista;
}

package com.example.musicaDB.model;

import java.time.LocalDate;

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

    @NotBlank(message = "El nombre del album es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre_album", nullable = false)
    private String nombreAlbum;

    @NotNull(message = "La fecha de publicación es obligatoria")
    @PastOrPresent(message = "La fecha de publicación no puede ser en el futuro")
    @Column(name = "fecha_publicasion_album", nullable = false)
    private LocalDate fechaPublicasionAlbum;

    //@NotNull(message = "El artista asociado es obligatorio")
   // @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "id_artista", nullable = false)
    //private Artista artista;
}

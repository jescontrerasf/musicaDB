package com.example.musicaDB.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "El nombre del artista es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre_artista", nullable = false)
    private String nombreArtista;

    @NotBlank(message = "El nombre artístico es obligatorio")
    @Column(name = "nombre_artistico", nullable = false, unique = true)
    private String nombreArtistico;

    @NotBlank(message = "La nacionalidad del artista es obligatoria")
    @Column(name = "nacionalidad_artista", nullable = false)
    private String nacionalidadArtista;

    @Column(name = "edad")
    @Size(min = 1, max = 3, message = "La edad debe ser de 1 a 3 caracteres")
    @Positive(message = "La edad debe ser un número positivo")
    @NotNull(message = "La edad del artista es obligatoria")
    private Integer edad;

    @Column(name = "estado")
    @NotNull(message = "El estado del artista es obligatorio")
    private Boolean estado;

    @NotBlank(message = "El género musical es obligatorio")
    @Column(name = "genero_musical")
    private String generoMusical;

}

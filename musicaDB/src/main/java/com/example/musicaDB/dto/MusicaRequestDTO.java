package com.example.musicaDB.dto;

import com.example.musicaDB.model.Album;
import com.example.musicaDB.model.Artista;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicaRequestDTO {

    @NotBlank(message = "El nombre de la cancion no puede estar vacio")
    @Size(min = 1, max = 50, message = "Debe tener entre 1 y 50 caracteres")
    private String nombreCancion;

    @NotBlank(message = "El nombre del artista es obligatorio")
    private Artista artista;

    private Album album;

    @NotBlank(message = "La cancion debe pertenecer a un genero musical")
    private String generoMusical;

    @NotNull(message = "La cancion tiene que tener duracion")
    @Positive(message = "Debe ser un valor positivo")
    private Integer duracion;

    @NotBlank(message = "La fecha no puede estar vacia")
    private String fechaPublicacion;
}
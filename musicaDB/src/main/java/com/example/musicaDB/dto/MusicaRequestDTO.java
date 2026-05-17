package com.example.musicaDB.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicaRequestDTO {

    @NotBlank(message = "El nombre de la cancion no puede estar vacio")
    @Size(min = 1, max = 50, message = "Debe de tener entre 1 y 50 caracteres")
    private String nombreCancion;

    @NotBlank(message = "El artista nombre del artista es obligatorio")
    private String artista;

    private String album;

    @NotBlank(message = "La cancion debe de pertenecer a un genero musical")
    private String generoMusical;

    @Min(value = 1, message = "Debe de durar más de un segundo")
    @Positive(message ="Debe ser un valor positivo")
    @NotBlank(message = "La cancion tiene que tener duración")
    private Integer duracion;

    @NotBlank(message = "La fecha no puede estar vacia")
    private String fechaPublicacion;
}

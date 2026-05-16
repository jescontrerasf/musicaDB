package com.example.musicaDB.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaRequestDTO {

    @NotBlank(message = "El nombre del artista es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreArtista;

    @NotBlank(message = "El nombre artístico es obligatorio")
    private String nombreArtistico;

    @NotBlank(message = "La nacionalidad del artista es obligatoria")
    private String nacionalidadArtista;

    @Size(min = 1, max = 3, message = "La edad debe ser de 1 a 3 caracteres")
    @Positive(message = "La edad debe ser un número positivo")
    @NotNull(message = "La edad del artista es obligatoria")
    private Integer edad;

    @NotNull(message = "El estado del artista es obligatorio")
    private Boolean estado;

    @NotBlank(message = "El género musical es obligatorio")
    private String generoMusical;
}

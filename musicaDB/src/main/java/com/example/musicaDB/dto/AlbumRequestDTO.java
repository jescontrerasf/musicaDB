package com.example.musicaDB.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequestDTO {

    @NotBlank(message = "El nombre del álbum no puede estar vacío")
    @Size(min = 1, max = 50, message = "Debe tener entre 1 y 50 caracteres")
    private String nombreAlbum;

    @NotNull(message = "La fecha no puede estar vacía")
    @PastOrPresent(message = "La fecha no puede ser en el futuro")
    private LocalDate fechaPublicasionAlbum;

    @NotBlank(message = "El nombre del artista es obligatorio")
    private String artista; // Ahora es String para recibir el nombre y buscarlo
}

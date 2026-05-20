package com.example.musicaDB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaResponseDTO {
    private Long idArtista;
    private String nombreArtista;
    private String nombreArtistico;
    private String nacionalidadArtista;
    private Integer edad;
    private Boolean estado;
    private String generoMusical;
}

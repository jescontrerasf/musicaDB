package com.example.musicaDB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicaResponseDTO {

    private Long idMusica;
    private String nombreMusica;
    private String artista;
    private String album;
    private String generoMusical;
    private Integer duracion;
    private String fechaPublicacion; 
}

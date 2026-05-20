package com.example.musicaDB.dto;

//import com.example.musicaDB.model.Artista;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponseDTO {

    private Long idAlbun;
    private String nombreAlbun;
    private LocalDate fechaPublicasionAlbum;
    //private Artista artista;


}
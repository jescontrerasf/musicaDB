package com.example.musicaDB.Controller;

import com.example.musicaDB.Model.Artista;
import com.example.musicaDB.Service.ArtistaService;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
@RequiredArgsConstructor
public class ArtistaController {

    private final ArtistaService artistaService;

    //Listar todos los artistas
    @GetMapping
    public ResponseEntity<List<Artista>> listarArtistas() {
        return ResponseEntity.ok(artistaService.listarArtistas());
    }

    //Listar artista por ID
    @GetMapping("/{id}")
    public ResponseEntity<Artista> obtenerArtistaPorId(@PathVariable Long id) {
        return artistaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //Crear nuevo artista
    @PostMapping
    public ResponseEntity<Artista> crearArtista(@Valid @RequestBody Artista artista) {
        return ResponseEntity.status(201).body(artistaService.saveArtista(artista));
    }

    //Actualizar artista existente
    @PutMapping("/{id}")
    public ResponseEntity<Artista> actualizarArtista(@PathVariable Long id, @Valid @RequestBody Artista artista) {
        return artistaService.obtenerPorId(id)
            .map(existente ->{
                artista.setIdArtista(id);
                return ResponseEntity.ok(artistaService.updateArtista(id, artista));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar artista por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable Long id) {
        if(artistaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        artistaService.deleteArtista(id);
        return ResponseEntity.noContent().build();
    }

    

    
}

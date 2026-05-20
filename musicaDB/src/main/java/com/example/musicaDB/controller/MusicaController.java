package com.example.musicaDB.controller;

import com.example.musicaDB.dto.MusicaRequestDTO;
import com.example.musicaDB.dto.MusicaResponseDTO;
import com.example.musicaDB.service.MusicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musica")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    // ---- CRUD base ----

    @PostMapping
    public ResponseEntity<MusicaResponseDTO> guardar(@Valid @RequestBody MusicaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(musicaService.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponseDTO> buscarPorId(@PathVariable Long id) {
        return musicaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MusicaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(musicaService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MusicaRequestDTO dto) {
        return ResponseEntity.ok(musicaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        musicaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<MusicaResponseDTO>> buscarPorNombre(@RequestParam String nombreMusica) {
        return ResponseEntity.ok(musicaService.buscarPorNombreMusica(nombreMusica));
    }

    @GetMapping("/buscar/genero")
    public ResponseEntity<List<MusicaResponseDTO>> buscarPorGenero(@RequestParam String generoMusical) {
        return ResponseEntity.ok(musicaService.buscarPorGeneroMusical(generoMusical));
    }

    @GetMapping("/buscar/artista")
    public ResponseEntity<List<MusicaResponseDTO>> buscarPorArtista(@RequestParam String artista) {
        return ResponseEntity.ok(musicaService.buscarPorArtista(artista));
    }

    @GetMapping("/ordenar/duracion-desc")
    public ResponseEntity<List<MusicaResponseDTO>> ordenarPorDuracionDesc() {
        return ResponseEntity.ok(musicaService.ordenarPorDuracionDesc());
    }

    @GetMapping("/buscar/album")
    public ResponseEntity<List<MusicaResponseDTO>> buscarPorAlbum(@RequestParam String album) {
        return ResponseEntity.ok(musicaService.buscarPorAlbum(album));
    }

    @GetMapping("/ordenar/fecha-asc")
    public ResponseEntity<List<MusicaResponseDTO>> ordenarPorFechaPublicacion() {
        return ResponseEntity.ok(musicaService.ordenarPorFechaPublicacionAsc());
    }
}
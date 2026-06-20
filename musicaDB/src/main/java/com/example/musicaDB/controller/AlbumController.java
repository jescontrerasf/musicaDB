package com.example.musicaDB.controller;

import com.example.musicaDB.dto.AlbumRequestDTO;
import com.example.musicaDB.dto.AlbumResponseDTO;
import com.example.musicaDB.service.AlbumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/album")
@Tag(name = "Albumes", description = "Operaciones de los albumes")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // ---- CRUD base ----

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> guardar(@Valid @RequestBody AlbumRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> buscarPorId(@PathVariable Long id) {
        return albumService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Obtener todos los albumes", description = "Se obtiene una lista de los albumes")
    public ResponseEntity<List<AlbumResponseDTO>> listarTodos() {
        return ResponseEntity.ok(albumService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequestDTO dto) {
        return ResponseEntity.ok(albumService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        albumService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Consultas Personalizadas ----

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<AlbumResponseDTO>> buscarPorNombre(@RequestParam String nombreAlbum) {
        return ResponseEntity.ok(albumService.buscarPorNombreAlbum(nombreAlbum));
    }

    @GetMapping("/buscar/artista")
    public ResponseEntity<List<AlbumResponseDTO>> buscarPorNombreDeArtista(@RequestParam String nombreArtista) {
        return ResponseEntity.ok(albumService.buscarPorNombreDeArtista(nombreArtista));
    }

    @GetMapping("/buscar/fecha-posterior")
    public ResponseEntity<List<AlbumResponseDTO>> buscarPorFechaPosteriorA(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(albumService.buscarPorFechaPosteriorA(fecha));
    }

    @GetMapping("/buscar/id-artista")
    public ResponseEntity<List<AlbumResponseDTO>> buscarPorIdArtista(@RequestParam Long idArtista) {
        return ResponseEntity.ok(albumService.buscarPorIdArtista(idArtista));
    }

    @GetMapping("/ordenar/fecha-desc")
    public ResponseEntity<List<AlbumResponseDTO>> ordenarPorFechaPublicacionDesc() {
        return ResponseEntity.ok(albumService.ordenarPorFechaPublicacionDesc());
    }
}

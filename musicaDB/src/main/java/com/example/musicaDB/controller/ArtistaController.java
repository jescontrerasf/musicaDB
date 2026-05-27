package com.example.musicaDB.controller;

import com.example.musicaDB.service.ArtistaService;
import com.example.musicaDB.dto.ArtistaResponseDTO;
import com.example.musicaDB.dto.ArtistaRequestDTO;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
@RequiredArgsConstructor
public class ArtistaController {

    private final ArtistaService artistaService;

    // Listar todos los artistas
    @GetMapping
    public ResponseEntity<List<ArtistaResponseDTO>> obtenerTodosLosArtistas() {
        List<ArtistaResponseDTO> artistas = artistaService.listarArtistas();
        return ResponseEntity.ok(artistas);
    }

    // Listar artista por ID
    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> obtenerArtistaPorId(@PathVariable Long id) {
        return artistaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo artista
    @PostMapping
    public ResponseEntity<ArtistaResponseDTO> crearArtista(@Valid @RequestBody ArtistaRequestDTO artista) {
        ArtistaResponseDTO nuevo = artistaService.saveArtista(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Actualizar artista existente
    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ArtistaRequestDTO artista) {
        ArtistaResponseDTO actualizado = artistaService.updateArtista(id, artista);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar artista por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable Long id) {
        if (artistaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        artistaService.deleteArtista(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre artístico
    @GetMapping("/buscar/nombreArt")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorNombreArtistico(@RequestParam String nombreArtistico) {
        return ResponseEntity.ok(artistaService.buscarPorNombreArtistico(nombreArtistico));
    }

    // Buscar por genero musical
    @GetMapping("/buscar/genero")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorGenero(@RequestParam String genero) {
        return ResponseEntity.ok(artistaService.buscarPorGeneroMusical(genero));
    }

    // Buscar por nacionalidad
    @GetMapping("/buscar/nacionalidad")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorNacionalidad(@RequestParam String nacionalidad) {
        return ResponseEntity.ok(artistaService.buscarPorNacionalidad(nacionalidad));
    }

    // Buscar por edad
    @GetMapping("/buscar/edad")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorEdad(@RequestParam Integer edad) {
        return ResponseEntity.ok(artistaService.buscarPorEdad(edad));
    }

    // Buscar por estado
    @GetMapping("/buscar/estado")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorEstado(@RequestParam Boolean estado) {
        return ResponseEntity.ok(artistaService.buscarPorEstado(estado));
    }

    // Buscar por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(artistaService.buscarPorNombre(nombre));
    }

    // Ordenar por nombre artístico
    @GetMapping("/ordenar/nombreArt")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorNombreArtistico() {
        return ResponseEntity.ok(artistaService.ordenarPorNombreArtistico());
    }

    // Ordenar por edad ascendente
    @GetMapping("/ordenar/edadAsc")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorEdadAsc() {
        return ResponseEntity.ok(artistaService.ordenarPorEdadAsc());
    }

    // Ordenar por edad descendente
    @GetMapping("/ordenar/edadDesc")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorEdadDesc() {
        return ResponseEntity.ok(artistaService.ordenarPorEdadDesc());
    }

    // Ordenar por genero musical
    @GetMapping("/ordenar/genero")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorGeneroMusical() {
        return ResponseEntity.ok(artistaService.ordenarPorGeneroMusical());

    }
}

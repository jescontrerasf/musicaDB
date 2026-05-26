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

    //Listar todos los artistas
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosArtistas() {
        try {
            List<ArtistaResponseDTO> artistas = artistaService.listarArtistas();
            return ResponseEntity.ok(artistas);
        } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }     
    }

    //Listar artista por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerArtistaPorId(@PathVariable Long id) {
        try {
            return artistaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Crear nuevo artista
    @PostMapping
    public ResponseEntity<?> crearArtista(@Valid @RequestBody ArtistaRequestDTO artista) {
        try {
            ArtistaResponseDTO nuevo = artistaService.saveArtista(artista);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Actualizar artista existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Long id, @Valid @RequestBody ArtistaRequestDTO artista) {
        try {
            ArtistaResponseDTO actualizado = artistaService.updateArtista(id, artista);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Eliminar artista por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArtista(@PathVariable Long id) {
        try {
            if(artistaService.obtenerPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            artistaService.deleteArtista(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Buscar por nombre artístico
    @GetMapping("/buscar/nombreArt")
    public ResponseEntity<?> buscarPorNombreArtistico(@RequestParam String nombreArtistico) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorNombreArtistico(nombreArtistico));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    //Buscar por genero musical
    @GetMapping("/buscar/genero")
    public ResponseEntity<?> buscarPorGenero(@RequestParam String genero) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorGeneroMusical(genero));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Buscar por nacionalidad
    @GetMapping("/buscar/nacionalidad")
    public ResponseEntity<?> buscarPorNacionalidad(@RequestParam String nacionalidad) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorNacionalidad(nacionalidad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Buscar por edad
    @GetMapping("/buscar/edad")
    public ResponseEntity<?> buscarPorEdad(@RequestParam Integer edad) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorEdad(edad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Buscar por estado
    @GetMapping("/buscar/estado")
    public ResponseEntity<?> buscarPorEstado(@RequestParam Boolean estado) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorEstado(estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Buscar por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorNombre(nombre));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Ordenar por nombre artístico
    @GetMapping("/ordenar/nombreArt")
    public ResponseEntity<?> ordenarPorNombreArtistico() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorNombreArtistico());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Ordenar por edad ascendente
    @GetMapping("/ordenar/edadAsc")
    public ResponseEntity<?> ordenarPorEdadAsc() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorEdadAsc());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Ordenar por edad descendente
    @GetMapping("/ordenar/edadDesc")
    public ResponseEntity<?> ordenarPorEdadDesc() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorEdadDesc());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Ordenar por genero musical
    @GetMapping("/ordenar/genero")
    public ResponseEntity<?> ordenarPorGeneroMusical() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorGeneroMusical());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

package com.example.musicaDB.Controller;

import com.example.musicaDB.Service.ArtistaService;
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
    public ResponseEntity<List<ArtistaResponseDTO>> obtenerTodosLosArtistas() {
        try {
            List<ArtistaResponseDTO> artistas = artistaService.listarArtistas();
            return ResponseEntity.ok(artistas);
        } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }     
    }

    //Listar artista por ID
    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> obtenerArtistaPorId(@PathVariable Long id) {
        try {
            return artistaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Crear nuevo artista
    @PostMapping
    public ResponseEntity<ArtistaResponseDTO> crearArtista(@Valid @RequestBody ArtistaRequestDTO artista) {
        try {
            ArtistaResponseDTO nuevo = artistaService.saveArtista(artista);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Actualizar artista existente
    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody ArtistaRequestDTO artista) {
        try {
            ArtistaResponseDTO actualizado = artistaService.updateArtista(id, artista);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Eliminar artista por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable Long id) {
        try {
            if(artistaService.obtenerPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            artistaService.deleteArtista(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Buscar por nombre artístico
    @GetMapping("/buscar/nombreArt")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorNombreArtistico(@RequestParam String nombreArtistico) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorNombreArtistico(nombreArtistico));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //Buscar por genero musical
    @GetMapping("/buscar/genero")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorGenero(@RequestParam String genero) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorGeneroMusical(genero));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Buscar por nacionalidad
    @GetMapping("/buscar/nacionalidad")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorNacionalidad(@RequestParam String nacionalidad) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorNacionalidad(nacionalidad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Buscar por edad
    @GetMapping("/buscar/edad")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorEdad(@RequestParam Integer edad) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorEdad(edad));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Buscar por estado
    @GetMapping("/buscar/estado")
    public ResponseEntity<List<ArtistaResponseDTO>> buscarPorEstado(@RequestParam Boolean estado) {
        try {
            return ResponseEntity.ok(artistaService.buscarPorEstado(estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Ordenar por nombre artístico
    @GetMapping("/ordenar/nombreArt")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorNombreArtistico() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorNombreArtistico());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Ordenar por edad ascendente
    @GetMapping("/ordenar/edadAsc")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorEdadAsc() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorEdadAsc());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Ordenar por edad descendente
    @GetMapping("/ordenar/edadDesc")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorEdadDesc() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorEdadDesc());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Ordenar por genero musical
    @GetMapping("/ordenar/genero")
    public ResponseEntity<List<ArtistaResponseDTO>> ordenarPorGeneroMusical() {
        try {
            return ResponseEntity.ok(artistaService.ordenarPorGeneroMusical());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.example.musicaDB.controller;

import com.example.musicaDB.service.ArtistaService;
import com.example.musicaDB.dto.ArtistaResponseDTO;
import com.example.musicaDB.dto.ArtistaRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/artistas")
@RequiredArgsConstructor
@Tag(name = "Artistas", description = "Gestión de artistas musicales")
public class ArtistaController {

    private final ArtistaService artistaService;

    @GetMapping
    @Operation(summary = "Listar todos los artistas", description = "Retorna la lista completa de artistas registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> obtenerTodosLosArtistas() {
        List<EntityModel<ArtistaResponseDTO>> artistas = artistaService.listarArtistas()
            .stream()
            .map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas"),
                linkTo(methodOn(ArtistaController.class).eliminarArtista(a.getIdArtista())).withRel("eliminar")
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(artistas,
            linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener artista por ID", description = "Retorna un artista específico según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artista encontrado",
            content = @Content(schema = @Schema(implementation = ArtistaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Artista no encontrado", content = @Content)
    })
    public ResponseEntity<EntityModel<ArtistaResponseDTO>> obtenerArtistaPorId(
            @Parameter(description = "ID del artista", required = true) @PathVariable Long id) {
        return artistaService.obtenerPorId(id)
            .map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(id)).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas"),
                linkTo(methodOn(ArtistaController.class).eliminarArtista(id)).withRel("eliminar")
            ))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo artista", description = "Registra un nuevo artista en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Artista creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo de la petición", content = @Content)
    })
    public ResponseEntity<EntityModel<ArtistaResponseDTO>> crearArtista(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo artista", required = true)
            @Valid @RequestBody ArtistaRequestDTO artista) {
        ArtistaResponseDTO nuevo = artistaService.saveArtista(artista);
        EntityModel<ArtistaResponseDTO> model = EntityModel.of(nuevo,
            linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(nuevo.getIdArtista())).withSelfRel(),
            linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar artista", description = "Actualiza los datos de un artista existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artista actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Artista no encontrado", content = @Content)
    })
    public ResponseEntity<EntityModel<ArtistaResponseDTO>> actualizar(
            @Parameter(description = "ID del artista a actualizar", required = true) @PathVariable Long id,
            @Valid @RequestBody ArtistaRequestDTO artista) {
        ArtistaResponseDTO actualizado = artistaService.updateArtista(id, artista);
        EntityModel<ArtistaResponseDTO> model = EntityModel.of(actualizado,
            linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(id)).withSelfRel(),
            linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar artista", description = "Elimina un artista de la base de datos por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Artista eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Artista no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminarArtista(
            @Parameter(description = "ID del artista a eliminar", required = true) @PathVariable Long id) {
        if (artistaService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        artistaService.deleteArtista(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombreArt")
    @Operation(summary = "Buscar por nombre artístico", description = "Filtra artistas por su nombre artístico")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> buscarPorNombreArtistico(
            @Parameter(description = "Nombre artístico a buscar") @RequestParam String nombreArtistico) {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.buscarPorNombreArtistico(nombreArtistico)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/genero")
    @Operation(summary = "Buscar por género musical", description = "Filtra artistas por género musical")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> buscarPorGenero(
            @Parameter(description = "Género musical a buscar") @RequestParam String genero) {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.buscarPorGeneroMusical(genero)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/nacionalidad")
    @Operation(summary = "Buscar por nacionalidad", description = "Filtra artistas por su nacionalidad")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> buscarPorNacionalidad(
            @Parameter(description = "Nacionalidad a buscar") @RequestParam String nacionalidad) {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.buscarPorNacionalidad(nacionalidad)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/edad")
    @Operation(summary = "Buscar por edad", description = "Filtra artistas por su edad")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> buscarPorEdad(
            @Parameter(description = "Edad a buscar") @RequestParam Integer edad) {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.buscarPorEdad(edad)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/estado")
    @Operation(summary = "Buscar por estado", description = "Filtra artistas activos (true) o inactivos (false)")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> buscarPorEstado(
            @Parameter(description = "Estado: true = activo, false = inactivo") @RequestParam Boolean estado) {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.buscarPorEstado(estado)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/nombre")
    @Operation(summary = "Buscar por nombre", description = "Filtra artistas por su nombre real")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> buscarPorNombre(
            @Parameter(description = "Nombre real a buscar") @RequestParam String nombre) {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.buscarPorNombre(nombre)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/nombreArt")
    @Operation(summary = "Ordenar por nombre artístico", description = "Retorna artistas ordenados alfabéticamente por nombre artístico")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> ordenarPorNombreArtistico() {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.ordenarPorNombreArtistico()
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/edadAsc")
    @Operation(summary = "Ordenar por edad ascendente", description = "Retorna artistas ordenados de menor a mayor edad")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> ordenarPorEdadAsc() {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.ordenarPorEdadAsc()
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/edadDesc")
    @Operation(summary = "Ordenar por edad descendente", description = "Retorna artistas ordenados de mayor a menor edad")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> ordenarPorEdadDesc() {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.ordenarPorEdadDesc()
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/genero")
    @Operation(summary = "Ordenar por género musical", description = "Retorna artistas ordenados alfabéticamente por género musical")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<ArtistaResponseDTO>>> ordenarPorGeneroMusical() {
        List<EntityModel<ArtistaResponseDTO>> lista = artistaService.ordenarPorGeneroMusical()
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(ArtistaController.class).obtenerArtistaPorId(a.getIdArtista())).withSelfRel(),
                linkTo(methodOn(ArtistaController.class).obtenerTodosLosArtistas()).withRel("artistas")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }
}
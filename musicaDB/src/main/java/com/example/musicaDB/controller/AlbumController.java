package com.example.musicaDB.controller;

import com.example.musicaDB.dto.AlbumRequestDTO;
import com.example.musicaDB.dto.AlbumResponseDTO;
import com.example.musicaDB.service.AlbumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/album")
@Tag(name = "Albumes", description = "Gestión de álbumes musicales")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    @Operation(summary = "Crear un álbum", description = "Registra un nuevo álbum en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Álbum creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo de la petición", content = @Content)
    })
    public ResponseEntity<EntityModel<AlbumResponseDTO>> guardar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo álbum", required = true)
            @Valid @RequestBody AlbumRequestDTO dto) {
        AlbumResponseDTO nuevo = albumService.guardar(dto);
        EntityModel<AlbumResponseDTO> model = EntityModel.of(nuevo,
            linkTo(methodOn(AlbumController.class).buscarPorId(nuevo.getIdAlbum())).withSelfRel(),
            linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener álbum por ID", description = "Retorna un álbum específico según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Álbum encontrado",
            content = @Content(schema = @Schema(implementation = AlbumResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Álbum no encontrado", content = @Content)
    })
    public ResponseEntity<EntityModel<AlbumResponseDTO>> buscarPorId(
            @Parameter(description = "ID del álbum", required = true) @PathVariable Long id) {
        return albumService.buscarPorId(id)
            .map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes"),
                linkTo(methodOn(AlbumController.class).eliminar(id)).withRel("eliminar")
            ))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos los álbumes", description = "Retorna la lista completa de álbumes registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponseDTO>>> listarTodos() {
        List<EntityModel<AlbumResponseDTO>> albumes = albumService.listarTodos()
            .stream()
            .map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(a.getIdAlbum())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes"),
                linkTo(methodOn(AlbumController.class).eliminar(a.getIdAlbum())).withRel("eliminar")
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(albumes,
            linkTo(methodOn(AlbumController.class).listarTodos()).withSelfRel()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar álbum", description = "Actualiza los datos de un álbum existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Álbum actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Álbum no encontrado", content = @Content)
    })
    public ResponseEntity<EntityModel<AlbumResponseDTO>> actualizar(
            @Parameter(description = "ID del álbum a actualizar", required = true) @PathVariable Long id,
            @Valid @RequestBody AlbumRequestDTO dto) {
        AlbumResponseDTO actualizado = albumService.actualizar(id, dto);
        EntityModel<AlbumResponseDTO> model = EntityModel.of(actualizado,
            linkTo(methodOn(AlbumController.class).buscarPorId(id)).withSelfRel(),
            linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar álbum", description = "Elimina un álbum de la base de datos por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Álbum eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Álbum no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del álbum a eliminar", required = true) @PathVariable Long id) {
        albumService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    @Operation(summary = "Buscar álbum por nombre", description = "Filtra álbumes que coincidan con el nombre dado")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponseDTO>>> buscarPorNombre(
            @Parameter(description = "Nombre del álbum a buscar") @RequestParam String nombreAlbum) {
        List<EntityModel<AlbumResponseDTO>> lista = albumService.buscarPorNombreAlbum(nombreAlbum)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(a.getIdAlbum())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/artista")
    @Operation(summary = "Buscar álbumes por artista", description = "Filtra álbumes según el nombre del artista")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponseDTO>>> buscarPorNombreDeArtista(
            @Parameter(description = "Nombre del artista") @RequestParam String nombreArtista) {
        List<EntityModel<AlbumResponseDTO>> lista = albumService.buscarPorNombreDeArtista(nombreArtista)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(a.getIdAlbum())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/fecha-posterior")
    @Operation(summary = "Buscar álbumes por fecha posterior", description = "Retorna álbumes publicados después de la fecha indicada (formato: YYYY-MM-DD)")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponseDTO>>> buscarPorFechaPosteriorA(
            @Parameter(description = "Fecha de referencia (YYYY-MM-DD)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<EntityModel<AlbumResponseDTO>> lista = albumService.buscarPorFechaPosteriorA(fecha)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(a.getIdAlbum())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/id-artista")
    @Operation(summary = "Buscar álbumes por ID de artista", description = "Filtra álbumes pertenecientes a un artista según su ID")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponseDTO>>> buscarPorIdArtista(
            @Parameter(description = "ID del artista") @RequestParam Long idArtista) {
        List<EntityModel<AlbumResponseDTO>> lista = albumService.buscarPorIdArtista(idArtista)
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(a.getIdAlbum())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/fecha-desc")
    @Operation(summary = "Ordenar álbumes por fecha descendente", description = "Retorna álbumes ordenados del más reciente al más antiguo")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponseDTO>>> ordenarPorFechaPublicacionDesc() {
        List<EntityModel<AlbumResponseDTO>> lista = albumService.ordenarPorFechaPublicacionDesc()
            .stream().map(a -> EntityModel.of(a,
                linkTo(methodOn(AlbumController.class).buscarPorId(a.getIdAlbum())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).listarTodos()).withRel("albumes")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }
}
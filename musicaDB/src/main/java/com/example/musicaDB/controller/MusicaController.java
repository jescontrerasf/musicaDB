package com.example.musicaDB.controller;

import com.example.musicaDB.dto.MusicaRequestDTO;
import com.example.musicaDB.dto.MusicaResponseDTO;
import com.example.musicaDB.service.MusicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/musica")
@Tag(name = "Música", description = "Gestión de canciones musicales")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @PostMapping
    @Operation(summary = "Crear una canción", description = "Registra una nueva canción en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Canción creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo de la petición", content = @Content)
    })
    public ResponseEntity<EntityModel<MusicaResponseDTO>> guardar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la nueva canción", required = true)
            @Valid @RequestBody MusicaRequestDTO dto) {
        MusicaResponseDTO nuevo = musicaService.guardar(dto);
        EntityModel<MusicaResponseDTO> model = EntityModel.of(nuevo,
            linkTo(methodOn(MusicaController.class).buscarPorId(nuevo.getIdMusica())).withSelfRel(),
            linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener canción por ID", description = "Retorna una canción específica según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Canción encontrada",
            content = @Content(schema = @Schema(implementation = MusicaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada", content = @Content)
    })
    public ResponseEntity<EntityModel<MusicaResponseDTO>> buscarPorId(
            @Parameter(description = "ID de la canción", required = true) @PathVariable Long id) {
        return musicaService.buscarPorId(id)
            .map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones"),
                linkTo(methodOn(MusicaController.class).eliminar(id)).withRel("eliminar")
            ))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas las canciones", description = "Retorna la lista completa de canciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> listarTodos() {
        List<EntityModel<MusicaResponseDTO>> canciones = musicaService.listarTodos()
            .stream()
            .map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones"),
                linkTo(methodOn(MusicaController.class).eliminar(m.getIdMusica())).withRel("eliminar")
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(canciones,
            linkTo(methodOn(MusicaController.class).listarTodos()).withSelfRel()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar canción", description = "Actualiza los datos de una canción existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Canción actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada", content = @Content)
    })
    public ResponseEntity<EntityModel<MusicaResponseDTO>> actualizar(
            @Parameter(description = "ID de la canción a actualizar", required = true) @PathVariable Long id,
            @Valid @RequestBody MusicaRequestDTO dto) {
        MusicaResponseDTO actualizado = musicaService.actualizar(id, dto);
        EntityModel<MusicaResponseDTO> model = EntityModel.of(actualizado,
            linkTo(methodOn(MusicaController.class).buscarPorId(id)).withSelfRel(),
            linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar canción", description = "Elimina una canción de la base de datos por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Canción eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la canción a eliminar", required = true) @PathVariable Long id) {
        musicaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    @Operation(summary = "Buscar canción por nombre", description = "Filtra canciones que coincidan con el nombre dado")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> buscarPorNombre(
            @Parameter(description = "Nombre de la canción") @RequestParam String nombreMusica) {
        List<EntityModel<MusicaResponseDTO>> lista = musicaService.buscarPorNombreMusica(nombreMusica)
            .stream().map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/genero")
    @Operation(summary = "Buscar canciones por género", description = "Filtra canciones según el género musical")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> buscarPorGenero(
            @Parameter(description = "Género musical") @RequestParam String generoMusical) {
        List<EntityModel<MusicaResponseDTO>> lista = musicaService.buscarPorGeneroMusical(generoMusical)
            .stream().map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/artista")
    @Operation(summary = "Buscar canciones por artista", description = "Filtra canciones según el nombre del artista")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> buscarPorArtista(
            @Parameter(description = "Nombre del artista") @RequestParam String artista) {
        List<EntityModel<MusicaResponseDTO>> lista = musicaService.buscarPorArtista(artista)
            .stream().map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/duracion-desc")
    @Operation(summary = "Ordenar por duración descendente", description = "Retorna canciones ordenadas de mayor a menor duración")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> ordenarPorDuracionDesc() {
        List<EntityModel<MusicaResponseDTO>> lista = musicaService.ordenarPorDuracionDesc()
            .stream().map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/buscar/album")
    @Operation(summary = "Buscar canciones por álbum", description = "Filtra canciones pertenecientes a un álbum específico")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> buscarPorAlbum(
            @Parameter(description = "Nombre del álbum") @RequestParam String album) {
        List<EntityModel<MusicaResponseDTO>> lista = musicaService.buscarPorAlbum(album)
            .stream().map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/ordenar/fecha-asc")
    @Operation(summary = "Ordenar por fecha ascendente", description = "Retorna canciones ordenadas de la más antigua a la más reciente")
    @ApiResponse(responseCode = "200", description = "Lista ordenada")
    public ResponseEntity<CollectionModel<EntityModel<MusicaResponseDTO>>> ordenarPorFechaPublicacion() {
        List<EntityModel<MusicaResponseDTO>> lista = musicaService.ordenarPorFechaPublicacionAsc()
            .stream().map(m -> EntityModel.of(m,
                linkTo(methodOn(MusicaController.class).buscarPorId(m.getIdMusica())).withSelfRel(),
                linkTo(methodOn(MusicaController.class).listarTodos()).withRel("canciones")
            )).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista));
    }
}
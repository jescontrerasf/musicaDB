# MusicaDB
## Integrantes
Guillermo Miranda | Microservicio Artista |
Jesús Contreras | Microservicio Album |
Vicente Zuniga| Microservicio Musica |

## Tecnologías utilizadas
- Java 17
- Spring Boot 3
- Spring Cloud (Gateway & Netflix Eureka)
- Spring Data JPA + Hibernate
- MySQL 8
- Docker & Docker Compose
- Swagger / OpenAPI 3
- Maven & Lombok

## Listado de Microservicios Implementados
El sistema se compone de los siguientes microservicios:
1. **Eureka Server (`eureka`):** Servidor de descubrimiento de servicios. Opera en el puerto `8761`.
2. **API Gateway (`api-gateway`):** Enrutador centralizado que expone los endpoints al exterior. Opera en el puerto `8080`.
3. **MusicaDB Service (`musica-db`):** Microservicio principal que contiene la lógica de negocio y la conexión a la base de datos MySQL. Opera en el puerto `8081`.


## Requisitos previos
- Extenciones de java y Spring Boot instaladas
- MySQL corriendo en puerto `3306`

## Base de datos
Crear la base de datos en MySQL:
`CREATE DATABASE musica_DB;`

## Configuración application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/musica_DB?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

## Pasos para ejecutar
1. Clonar el repositorio
2. Entrar a la carpeta del proyecto
3. Ejecutar el proyecto
4. El proyecto corre en `http://localhost:8081`

## Endpoints disponibles

### Artista `/api/artistas`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/artistas | Listar todos |
| GET | /api/artistas/{id} | Buscar por ID |
| POST | /api/artistas | Crear artista |
| PUT | /api/artistas/{id} | Actualizar artista |
| DELETE | /api/artistas/{id} | Eliminar artista |
| GET | /api/artistas/buscar/nombre?nombre= | Buscar por nombre |
| GET | /api/artistas/buscar/genero?genero= | Buscar por género |
| GET | /api/artistas/buscar/nacionalidad?nacionalidad= | Buscar por nacionalidad |
| GET | /api/artistas/buscar/edad?edad= | Buscar por edad |
| GET | /api/artistas/buscar/estado?estado= | Buscar por estado |
| GET | /api/artistas/ordenar/nombreArt | Ordenar por nombre artístico |
| GET | /api/artistas/ordenar/edadAsc | Ordenar por edad ascendente |
| GET | /api/artistas/ordenar/edadDesc | Ordenar por edad descendente |
| GET | /api/artistas/ordenar/genero | Ordenar por género |

### Album `/api/album`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/album | Listar todos |
| GET | /api/album/{id} | Buscar por ID |
| POST | /api/album | Crear álbum |
| PUT | /api/album/{id} | Actualizar álbum |
| DELETE | /api/album/{id} | Eliminar álbum |
| GET | /api/album/buscar/nombre?nombreAlbum= | Buscar por nombre |
| GET | /api/album/buscar/artista?nombreArtista= | Buscar por artista |
| GET | /api/album/buscar/fecha-posterior?fecha= | Buscar por fecha |
| GET | /api/album/buscar/id-artista?idArtista= | Buscar por ID artista |
| GET | /api/album/ordenar/fecha-desc | Ordenar por fecha |

### Musica `/api/musica`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/musica | Listar todas |
| GET | /api/musica/{id} | Buscar por ID |
| POST | /api/musica | Crear canción |
| PUT | /api/musica/{id} | Actualizar canción |
| DELETE | /api/musica/{id} | Eliminar canción |
| GET | /api/musica/buscar/nombre?nombreMusica= | Buscar por nombre |
| GET | /api/musica/buscar/genero?generoMusical= | Buscar por género |
| GET | /api/musica/buscar/artista?artista= | Buscar por artista |
| GET | /api/musica/buscar/album?album= | Buscar por álbum |
| GET | /api/musica/ordenar/duracion-desc | Ordenar por duración |
| GET | /api/musica/ordenar/fecha-asc | Ordenar por fecha |

#### Ejemplos para la ejecucion. `Seguir orden de creacion`
## Crear artista 
{
    "nombreArtista": "Benito Martinez",
    "nombreArtistico": "Bad Bunny",
    "nacionalidadArtista": "Puertorriqueña",
    "edad": 30,
    "estado": true,
    "generoMusical": "Reggaeton"
},
{
    "nombreArtista": "Christofer Velez",
    "nombreArtistico": "Chyste MC",
    "nacionalidadArtista": "Chilena",
    "edad": 32,
    "estado": true,
    "generoMusical": "Hip-Hop"
}
{
    "nombreArtista": "Kurt Cobain",
    "nombreArtistico": "Kurt Cobain",
    "nacionalidadArtista": "Estadounidense",
    "edad": 27,
    "estado": false,
    "generoMusical": "Rock"
}
## Crear Album
{
    "nombreAlbum": "Un Verano Sin Ti",
    "fechaPublicasionAlbum": "2022-05-06",
    "artista": "Bad Bunny"
}
{
    "nombreAlbum": "Techymuv",
    "fechaPublicasionAlbum": "2016-09-11",
    "artista": "Chyste MC"
}
{
    "nombreAlbum": "Nevermind",
    "fechaPublicasionAlbum": "1991-09-24",
    "artista": "Kurt Cobain"
}
## Crear Cancion
{
    "nombreCancion": "Tití Me Preguntó",
    "artista": "Bad Bunny",
    "album": "Un Verano Sin Ti",
    "generoMusical": "Reggaeton",
    "duracion": 199,
    "fechaPublicacion": "2022-05-06"
}
{
    "nombreCancion": "Guantes de lana",
    "artista": "Chyste MC",
    "album": "Desde El Barrio",
    "generoMusical": "Hip-Hop",
    "duracion": 255,
    "fechaPublicacion": "2016-09-11"
}
{
    "nombreCancion": "Smells Like Teen Spirit",
    "artista": "Kurt Cobain",
    "album": "Nevermind",
    "generoMusical": "Rock",
    "duracion": 301,
    "fechaPublicacion": "1991-09-24"
}
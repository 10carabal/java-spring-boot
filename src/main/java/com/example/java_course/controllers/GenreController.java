package com.example.java_course.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_course.models.Book;
import com.example.java_course.models.Genre;
import com.example.java_course.models.dto.GenreDto;
import com.example.java_course.services.GenreService;


@RestController
@RequestMapping("genres")
// @RequestMapping("/api/genres")
@Validated
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // Listar todos los géneros
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genres = genreService.getAllGenres().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genres);
    }

    // Obtener género por ID
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear género
    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@Validated @RequestBody GenreDto genreDto) {
        var genre = toEntity(genreDto);
        var savedGenre = genreService.saveGenre(genre);
        return ResponseEntity.ok(toDto(savedGenre));
    }

    // Actualizar género
    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id, @Validated @RequestBody GenreDto genreDto) {
        var updatedGenre = toEntity(genreDto);
        var result = genreService.updateGenre(id, updatedGenre);
        return ResponseEntity.ok(toDto(result));
    }

    // Eliminar género
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos de mapeo entre entidad y DTO
    private GenreDto toDto(Genre genre) {

        List<String> bookNames = genre.getBooks() != null
        ? genre.getBooks().stream()
                .map(Book::getTitle) // assuming Book has getTitle()
                .collect(Collectors.toList())
        : List.of();

        GenreDto dto = new GenreDto(

            null, genre.getNameGenre(),
            genre.getDescription(),
            genre.isChildFriendly(),
            bookNames
        );
        if (genre.getIdGenre() != null) {
            dto.setIdGenre(genre.getIdGenre());
        }
        return dto;
    }

    private Genre toEntity(GenreDto dto) {
        var genre = new Genre();
        genre.setNameGenre(dto.getNameGenre());
        genre.setDescription(dto.getDescription());
        genre.setChildFriendly(dto.getIsChildFriendly());
        return genre;
    }

    // @GetMapping("/genres-map")
    // public Map<String, Object> genresMap() {
    // Genre genre = new Genre("Science Fiction", 1, true);
    // Map<String, Object> response = new HashMap<>();
    // response.put("1", genre);
    // response.put("2", new Genre("Fantasy", 2, true));
    // response.put("3", new Genre("Mystery", 3, true));
    // response.put("4", new Genre("Non-Fiction", 4, false));
    // response.put("5", new Genre("Romance", 5, true));
    // response.put("6", new Genre("Horror", 6, false));

    // return response;
    // }

    @GetMapping("description/{param}")
    public String getDescription(@PathVariable String param) {
        if (param.isEmpty()) {
            param = param.concat("arro");
            return param;
        }
        return "param not found";
    }

}

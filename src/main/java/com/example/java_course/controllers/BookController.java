package com.example.java_course.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.java_course.models.Book;
import com.example.java_course.models.Genre;
import com.example.java_course.models.dto.BookDto;
import com.example.java_course.models.dto.ParamsDto;
import com.example.java_course.models.dto.RegisterDto;
import com.example.java_course.services.BookService;
import com.example.java_course.services.GenreService;

@RestController
@RequestMapping("books")
// @RequestMapping("/api/books")
@Validated
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping("params")
    public ParamsDto getParams(@RequestParam(required = false, defaultValue = "Validating...") String message) {
        ParamsDto params = new ParamsDto();
        params.setMessage(message != null ? message : "hello world");
        return params;
    }

    @GetMapping("register")
    public RegisterDto getUserRegister(RegisterDto register) {
        return register;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(required = false) Integer rating) {
        try {
            if (rating != null && rating > 0) {
                List<BookDto> books = bookService.getAllBooksByRating(rating).stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(books);
            } else {
                List<BookDto> books = bookService.getAllBooks().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(books);
            }
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating value");
        }

    }

    // @GetMapping("rating")
    // public RatingDto getRating(HttpServletRequest request) {
    // Integer rating;
    // try {
    // rating = Integer.parseInt(request.getParameter("rating"));
    // } catch (NumberFormatException e) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating
    // value");
    // }
    // RatingDto ratingDto = new RatingDto();
    // ratingDto.setRatingBook(rating);
    // return ratingDto;
    // }

    // @GetMapping
    // public ResponseEntity<List<BookDto>> getAllBooks() {
    //     List<BookDto> books = bookService.getAllBooks().stream()
    //             .map(this::toDto)
    //             .collect(Collectors.toList());
    //     return ResponseEntity.ok(books);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Validated @RequestBody BookDto bookDto) {
        var book = toEntity(bookDto);
        var savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(toDto(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @Validated @RequestBody BookDto bookDto) {
        var updatedBook = toEntity(bookDto);
        var result = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(toDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Add this method to convert Book to BookDto
    private BookDto toDto(Book book) {
        // Assuming BookDto has a constructor or builder that accepts Book fields
        return new BookDto(
                book.getIdBook(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedDate() != null ? book.getPublishedDate().toString() : null,
                book.getCreateTime() != null ? book.getCreateTime().toString() : null,
                book.getRating(),
                book.getGenre() != null ? book.getGenre().getNameGenre() : null,
                book.getGenre() != null ? book.getGenre().getIdGenre() : null);
    }

    // Add this method to convert BookDto to Book
    private Book toEntity(BookDto bookDto) {
        // Get the current LocalDateTime
        LocalDateTime currentDateTime = LocalDateTime.now();

        var book = new Book();
        // Assuming Book has a constructor or builder that accepts BookDto fields
        book.setIdBook(bookDto.getIdBook());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublishedDate(
                bookDto.getPublishedDate() != null ? Date.valueOf(LocalDate.parse(bookDto.getPublishedDate()))
                        : null);
        book.setCreateTime(currentDateTime);
        book.setRating(bookDto.getRating());

        // Buscar el genero por id y asignarlo al libro
        Genre genre = genreService.getGenreById(bookDto.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        book.setGenre(genre);
        book.setRating(bookDto.getRating());

        return book;
    }

}

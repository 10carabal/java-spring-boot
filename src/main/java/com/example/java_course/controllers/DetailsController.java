package com.example.java_course.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.java_course.models.Book;
import com.example.java_course.models.dto.BookDto;
import com.example.java_course.repository.BookRepository;



@Controller
//@RequestMapping("/details")
public class DetailsController {
    private final BookRepository bookRepository;

    public DetailsController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @GetMapping("details")
    public String listBooks(Model model){
        List<BookDto> books = bookRepository.findAll().stream().map(this::toDto).toList();
        model.addAttribute("books", books);
        return "details";
    }

    //@ModelAttribute("books") para reutilizar estos elementos globalmente.
    private BookDto toDto(Book book) {
       return new BookDto(
            book.getIdBook(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublishedDate() != null ? book.getPublishedDate().toString() : null,
            book.getCreateTime() != null ? book.getCreateTime().toString() : null,
            book.getRating(),
            book.getGenre() != null ? book.getGenre().getNameGenre() : null,
            book.getGenre() != null ? book.getGenre().getIdGenre() : null
        );
    }

}

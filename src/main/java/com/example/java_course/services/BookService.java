package com.example.java_course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.java_course.models.Book;
import com.example.java_course.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
            .map(existingBook -> {
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setPublishedDate(updatedBook.getPublishedDate());
                existingBook.setGenre(updatedBook.getGenre());
                return bookRepository.save(existingBook);
            })
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
}

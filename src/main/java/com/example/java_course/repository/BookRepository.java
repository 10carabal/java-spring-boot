package com.example.java_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_course.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findAllByRating(int rating);

}

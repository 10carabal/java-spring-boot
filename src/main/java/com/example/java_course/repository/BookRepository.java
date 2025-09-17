package com.example.java_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_course.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}

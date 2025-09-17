package com.example.java_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_course.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

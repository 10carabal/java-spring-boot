package com.example.java_course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.java_course.models.Genre;
import com.example.java_course.repository.GenreRepository;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
        }
    }

    public Genre updateGenre(Long id, Genre updatedGenre) {
        return genreRepository.findById(id)
                .map(existingGenre -> {
                    existingGenre.setNameGenre(updatedGenre.getNameGenre());
                    existingGenre.setDescription(updatedGenre.getDescription());
                    existingGenre.setChildFriendly(updatedGenre.isChildFriendly());
                    existingGenre.setCreateTime(updatedGenre.getCreateTime());
                    return genreRepository.save(existingGenre);
                })
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

}

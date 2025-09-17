package com.example.java_course.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Genre")
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private Long idGenre;
    @Column(name = "name_genre", nullable = false, unique = true)
    private String nameGenre;
    @Column(name = "description")
    private String description;
    @Column(name = "child_friendly")
    private boolean isChildFriendly;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @OneToMany(mappedBy = "genre")
    private List<Book> books;
}

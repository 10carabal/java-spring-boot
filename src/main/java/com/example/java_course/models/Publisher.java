package com.example.java_course.models;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publishers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Publisher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publisher")
    private Long idPublisher;
    @Column(name = "name_publisher", nullable = false)
    private String namePublisher;
    @Column(name = "founded_year")
    private Date foundedYear;
    @Column(name = "website")
    private String website;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}

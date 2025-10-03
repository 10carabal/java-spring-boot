package com.example.java_course.models.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long idBook;
    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    @NotNull(message = "Author cannot be null")
    private String author;
    private String publishedDate;
    private String createTime;
    private Integer rating;
    private String genreName;
    private Long genreId;
}

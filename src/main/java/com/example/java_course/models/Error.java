package com.example.java_course.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Error {
    private String message;
    private String error;
    private int status;
    private Date date;
}

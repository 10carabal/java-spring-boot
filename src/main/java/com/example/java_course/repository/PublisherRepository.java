package com.example.java_course.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.java_course.models.Publisher;

@RepositoryRestResource(path = "publishers")
public interface PublisherRepository extends CrudRepository<Publisher, Long>{

}

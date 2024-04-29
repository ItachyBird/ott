package com.example.ott.model;

import com.example.ott.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepo extends JpaRepository<Movies, Long> {
    // You can add custom query methods if needed
}

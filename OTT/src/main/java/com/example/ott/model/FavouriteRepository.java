package com.example.ott.model;

import com.example.ott.model.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourites, Long> {
    // You can add custom query methods if needed
}

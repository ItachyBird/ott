package com.example.ott.controller;

import com.example.ott.model.User;
import com.example.ott.model.UserRepo;
import com.example.ott.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserRepo userRepository;

    @Autowired
    public FavouriteController(FavouriteService favouriteService, UserRepo userRepository) {
        this.favouriteService = favouriteService;
        this.userRepository = userRepository;
    }

    @PostMapping("/favorites/add")
    public ResponseEntity<String> addFavorite(@RequestParam String userEmail, @RequestParam Long movieId) {
        // Get user ID from the email
        Integer userId = getUserIdByEmail(userEmail); // Implement this method to get the user ID

        // Call the service to add the favorite
        favouriteService.addFavorite(userId, movieId);

        return ResponseEntity.ok("Movie added to favorites successfully.");
    }

    // Implement this method to get the user ID based on the email
    private Integer getUserIdByEmail(String userEmail) {
        // Query the database to find the user by email
        User user = userRepository.findByEmail(userEmail);

        if (user != null) {
            return user.getId(); // Assuming getId() returns the user ID
        } else {
            // Handle case when user is not found
            throw new RuntimeException("User not found with email: " + userEmail);
        }
    }
}

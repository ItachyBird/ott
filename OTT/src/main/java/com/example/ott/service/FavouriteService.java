package com.example.ott.service;

import com.example.ott.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ott.model.FavouriteRepository;
import com.example.ott.model.Favourites;
import com.example.ott.model.Movies;
import com.example.ott.model.MoviesRepo;
import com.example.ott.model.UserRepo;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepo userRepository;
    private final MoviesRepo moviesRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, UserRepo userRepository, MoviesRepo moviesRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.moviesRepository = moviesRepository;
    }

    public void addFavorite(Integer userId, Long movieId) {
        // Retrieve user by ID
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Retrieve movie by ID
        Movies movie = moviesRepository.findById(movieId)
                                       .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));

        // Create a new favorite entry
        Favourites favourite = new Favourites();
        favourite.setUser(user);
        favourite.setMovie(movie);
        favourite.setEmail(user.getEmail());
        favourite.setGenre(movie.getGenre());
        favourite.setMovieName(movie.getMovieName());
        favourite.setMovieDescription(movie.getMovieDescription());
        favourite.setPoster(movie.getPoster());

        // Save the favorite entry
        favouriteRepository.save(favourite);
    }
}

package com.moviebooking.service.impl;

import com.moviebooking.dto.request.MovieRequest;
import com.moviebooking.dto.response.MovieResponse;
import com.moviebooking.entity.Movie;
import com.moviebooking.exception.ResourceAlreadyExistsException;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest request) {

        // Check if movie title already exists
        if (movieRepository.existsByTitle(request.getTitle())) {
            throw new ResourceAlreadyExistsException(
                "Movie already exists with title: " + request.getTitle()
            );
        }

        // Build Movie entity from request
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .rating(request.getRating())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .build();

        // Save to DB
        Movie savedMovie = movieRepository.save(movie);

        return mapToMovieResponse(savedMovie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest request) {

        // Find existing movie
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Movie not found with id: " + id
                    )
                );

        // Update fields
        movie.setTitle(request.getTitle());
        movie.setGenre(request.getGenre());
        movie.setDuration(request.getDuration());
        movie.setLanguage(request.getLanguage());
        movie.setRating(request.getRating());
        movie.setDescription(request.getDescription());
        movie.setReleaseDate(request.getReleaseDate());

        // Save updated movie
        Movie updatedMovie = movieRepository.save(movie);

        return mapToMovieResponse(updatedMovie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {

        // Check movie exists
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Movie not found with id: " + id
                    )
                );

        movieRepository.delete(movie);
    }

    @Override
    public MovieResponse getMovieById(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Movie not found with id: " + id
                    )
                );

        return mapToMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre)
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguage(language)
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> searchMovies(String keyword) {
        return movieRepository.searchMovies(keyword)
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> getTopRatedMovies() {
        return movieRepository.findAllOrderByRatingDesc()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    // Helper — map Movie entity to MovieResponse
    public MovieResponse mapToMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .language(movie.getLanguage())
                .rating(movie.getRating())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .createdAt(movie.getCreatedAt())
                .build();
    }
}
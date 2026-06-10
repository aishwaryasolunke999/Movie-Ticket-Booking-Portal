package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Find movies by genre
    List<Movie> findByGenre(String genre);

    // Find movies by language
    List<Movie> findByLanguage(String language);

    // Check if movie title already exists
    Boolean existsByTitle(String title);

    // Search movies by title containing keyword (case insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    // Find movies by genre and language
    List<Movie> findByGenreAndLanguage(String genre, String language);

    // Get all movies with rating greater than given value
    List<Movie> findByRatingGreaterThanEqual(Double rating);

    // Custom JPQL query to find top rated movies
    @Query("SELECT m FROM Movie m ORDER BY m.rating DESC")
    List<Movie> findAllOrderByRatingDesc();

    // Search by title or genre
    @Query("SELECT m FROM Movie m WHERE " +
           "LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchMovies(@Param("keyword") String keyword);
}
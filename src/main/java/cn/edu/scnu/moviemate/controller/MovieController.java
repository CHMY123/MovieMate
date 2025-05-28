package cn.edu.scnu.moviemate.controller;

import cn.edu.scnu.moviemate.entity.Movie;
import cn.edu.scnu.moviemate.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetail(@PathVariable Long id) {
        Movie movie = movieService.getMovieDetail(id);
        if (movie != null) {
            // 增加播放量
            movieService.incrementViewCount(id);
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getMoviesByType(
            @PathVariable String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Movie> movies = movieService.getMoviesByType(type, page, size);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<?> getMoviesByRegion(
            @PathVariable String region,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Movie> movies = movieService.getMoviesByRegion(region, page, size);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/hot")
    public ResponseEntity<?> getHotMovies(@RequestParam(defaultValue = "10") int limit) {
        List<Movie> movies = movieService.getHotMovies(limit);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/actor/{actorId}")
    public ResponseEntity<?> getMoviesByActor(@PathVariable Long actorId) {
        List<Movie> movies = movieService.getMoviesByActor(actorId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/director/{directorId}")
    public ResponseEntity<?> getMoviesByDirector(@PathVariable Long directorId) {
        List<Movie> movies = movieService.getMoviesByDirector(directorId);
        return ResponseEntity.ok(movies);
    }
}

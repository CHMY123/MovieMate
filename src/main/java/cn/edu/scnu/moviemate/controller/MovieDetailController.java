package cn.edu.scnu.moviemate.controller;

import cn.edu.scnu.moviemate.entity.Movie;
import cn.edu.scnu.moviemate.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieDetailController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/detail")
    public String detail(@RequestParam Long id, Model model) {
        Movie movie = movieService.getMovieDetail(id);
        model.addAttribute("movie", movie);
        return "movie/detail";
    }
} 
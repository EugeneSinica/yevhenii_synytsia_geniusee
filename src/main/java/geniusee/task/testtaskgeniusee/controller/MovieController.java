package geniusee.task.testtaskgeniusee.controller;

import geniusee.task.testtaskgeniusee.dto.MovieRequest;
import geniusee.task.testtaskgeniusee.mapper.MovieRequestMapper;
import geniusee.task.testtaskgeniusee.model.Movie;
import geniusee.task.testtaskgeniusee.service.MovieService;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
  private final MovieService movieService;
  private final MovieRequestMapper movieRequestMapper;

  public MovieController(MovieService movieService, MovieRequestMapper movieRequestMapper) {
    this.movieService = movieService;
    this.movieRequestMapper = movieRequestMapper;
  }

  @PostMapping
  public Movie createMovie(@RequestBody MovieRequest movieRequest) {
    Movie movie = movieRequestMapper.toModel(movieRequest);
    return movieService.save(movie);
  }

  @GetMapping("/{id}")
  public Movie getMovie(@PathVariable Long id) {
    return movieService.get(id);
  }

  @PutMapping("/{id}")
  public Movie updateMovie(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
    Movie movie = movieRequestMapper.toModel(movieRequest);
    movie.setId(id);
    return movieService.update(movie);
  }

  @DeleteMapping("/{id}")
  public String deleteMovie(@PathVariable Long id) {
    movieService.delete(id);
    return "Movie with id " + id + " was deleted.";
  }

  @GetMapping
  public List<Movie> findAll(
      @RequestParam Map<String, String> params,
      @RequestParam(defaultValue = "20") Integer count,
      @RequestParam(defaultValue = "0") Integer page) {
    PageRequest pageRequest = PageRequest.of(page, count);
    params.remove("count");
    params.remove("page");
    return movieService.findAll(params, pageRequest);
  }
}

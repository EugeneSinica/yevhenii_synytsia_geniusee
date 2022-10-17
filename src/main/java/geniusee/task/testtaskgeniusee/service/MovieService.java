package geniusee.task.testtaskgeniusee.service;

import geniusee.task.testtaskgeniusee.model.Movie;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;

public interface MovieService {
  Movie save(Movie movie);

  Movie update(Movie movie);

  Movie get(Long id);

  void delete(Long id);

  List<Movie> findAll(Map<String, String> params, PageRequest pageRequest);
}

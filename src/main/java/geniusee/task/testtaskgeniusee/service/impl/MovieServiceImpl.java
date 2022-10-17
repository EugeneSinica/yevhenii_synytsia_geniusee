package geniusee.task.testtaskgeniusee.service.impl;

import geniusee.task.testtaskgeniusee.model.Movie;
import geniusee.task.testtaskgeniusee.repository.MovieRepository;
import geniusee.task.testtaskgeniusee.repository.specification.SpecificationManager;
import geniusee.task.testtaskgeniusee.service.MovieService;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
  private final MovieRepository movieRepository;
  private final SpecificationManager<Movie> movieSpecificationManager;

  public MovieServiceImpl(
      MovieRepository movieRepository, SpecificationManager<Movie> movieSpecificationManager) {
    this.movieRepository = movieRepository;
    this.movieSpecificationManager = movieSpecificationManager;
  }

  public Movie save(Movie movie) {
    return movieRepository.save(movie);
  }

  public Movie update(Movie movie) {
    return movieRepository.save(movie);
  }

  public Movie get(Long id) {
    return movieRepository.getReferenceById(id);
  }

  public void delete(Long id) {
    movieRepository.deleteById(id);
  }

  public List<Movie> findAll(Map<String, String> params, PageRequest pageRequest) {
    Specification<Movie> specification = null;
    for (Map.Entry<String, String> entry : params.entrySet()) {
      Specification<Movie> movieSpecification =
          movieSpecificationManager.get(entry.getKey(), entry.getValue().split(","));
      specification =
          specification == null
              ? Specification.where(movieSpecification)
              : specification.and(movieSpecification);
    }
    return movieRepository.findAll(specification, pageRequest).toList();
  }
}

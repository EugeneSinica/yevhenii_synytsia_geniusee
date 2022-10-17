package geniusee.task.testtaskgeniusee.mapper;

import geniusee.task.testtaskgeniusee.dto.MovieRequest;
import geniusee.task.testtaskgeniusee.model.Movie;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MovieRequestMapper implements RequestMapper<Movie, MovieRequest> {
  @Override
  public Movie toModel(MovieRequest dto) {
    return Movie.builder()
        .title(dto.getTitle())
        .description(dto.getDescription())
        .cinemaHallNumber(dto.getCinemaHallNumber())
        .showTime(LocalDateTime.parse(dto.getShowTime()))
        .build();
  }
}

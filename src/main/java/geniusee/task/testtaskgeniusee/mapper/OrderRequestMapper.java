package geniusee.task.testtaskgeniusee.mapper;

import geniusee.task.testtaskgeniusee.dto.OrderRequest;
import geniusee.task.testtaskgeniusee.model.Movie;
import geniusee.task.testtaskgeniusee.model.Order;
import geniusee.task.testtaskgeniusee.repository.MovieRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestMapper implements RequestMapper<Order, OrderRequest> {
  private final MovieRepository movieRepository;

  public OrderRequestMapper(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public Order toModel(OrderRequest dto) {
    List<Movie> movies =
        dto.getMovieIds().stream()
            .map(movieRepository::getReferenceById)
            .collect(Collectors.toList());
    return Order.builder()
        .movies(movies)
        .comment(dto.getComment())
        .orderTime(LocalDateTime.now())
        .build();
  }
}

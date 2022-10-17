package geniusee.task.testtaskgeniusee.repository.specification.movie;

import geniusee.task.testtaskgeniusee.model.Movie;
import geniusee.task.testtaskgeniusee.repository.specification.SpecificationProvider;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieCinemaHallNumberSpecification implements SpecificationProvider<Movie> {
  private static final String FILTER_KEY = "cinemaHallNumberIn";
  private static final String FIELD_NAME = "cinemaHallNumber";

  @Override
  public Specification<Movie> getSpecification(String[] cinemaHallNumbers) {
    return (root, query, criteriaBuilder) -> {
      CriteriaBuilder.In<Integer> predicate = criteriaBuilder.in(root.get(FIELD_NAME));
      for (String cinemaHallNumber : cinemaHallNumbers) {
        predicate.value(Integer.valueOf(cinemaHallNumber));
      }
      return criteriaBuilder.and(predicate, predicate);
    };
  }

  @Override
  public String getFilterKey() {
    return FILTER_KEY;
  }
}

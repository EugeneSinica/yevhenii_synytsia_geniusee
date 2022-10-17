package geniusee.task.testtaskgeniusee.repository.specification.movie;

import geniusee.task.testtaskgeniusee.model.Movie;
import geniusee.task.testtaskgeniusee.repository.specification.SpecificationProvider;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieTitleSpecification implements SpecificationProvider<Movie> {
  private static final String FILTER_KEY = "titleIn";
  private static final String FIELD_NAME = "title";

  @Override
  public Specification<Movie> getSpecification(String[] titles) {
    return (root, query, criteriaBuilder) -> {
      CriteriaBuilder.In<String> predicate = criteriaBuilder.in(root.get(FIELD_NAME));
      for (String title : titles) {
        predicate.value(title);
      }
      return criteriaBuilder.and(predicate, predicate);
    };
  }

  @Override
  public String getFilterKey() {
    return FILTER_KEY;
  }
}

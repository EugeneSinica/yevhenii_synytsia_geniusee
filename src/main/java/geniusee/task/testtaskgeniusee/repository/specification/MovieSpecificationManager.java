package geniusee.task.testtaskgeniusee.repository.specification;

import geniusee.task.testtaskgeniusee.model.Movie;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieSpecificationManager implements SpecificationManager<Movie> {
  private final Map<String, SpecificationProvider<Movie>> providerMap;

  public MovieSpecificationManager(List<SpecificationProvider<Movie>> movieSpecifications) {
    this.providerMap =
        movieSpecifications.stream()
            .collect(Collectors.toMap(SpecificationProvider::getFilterKey, Function.identity()));
  }

  @Override
  public Specification<Movie> get(String filterKey, String[] params) {
    if (!providerMap.containsKey(filterKey)) {
      throw new RuntimeException("Key " + filterKey + " is not supported");
    }
    return providerMap.get(filterKey).getSpecification(params);
  }
}

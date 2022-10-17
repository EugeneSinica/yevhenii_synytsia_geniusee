package geniusee.task.testtaskgeniusee.repository;

import geniusee.task.testtaskgeniusee.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository
    extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {}

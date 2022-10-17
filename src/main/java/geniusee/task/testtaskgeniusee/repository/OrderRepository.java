package geniusee.task.testtaskgeniusee.repository;

import geniusee.task.testtaskgeniusee.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}

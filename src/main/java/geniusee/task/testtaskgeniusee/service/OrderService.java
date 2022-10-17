package geniusee.task.testtaskgeniusee.service;

import geniusee.task.testtaskgeniusee.model.Order;

public interface OrderService {
  Order save(Order order);

  Order update(Order order);

  Order get(Long id);

  void delete(Long id);
}

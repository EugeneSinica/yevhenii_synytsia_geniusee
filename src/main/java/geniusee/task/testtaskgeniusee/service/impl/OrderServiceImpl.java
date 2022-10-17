package geniusee.task.testtaskgeniusee.service.impl;

import geniusee.task.testtaskgeniusee.model.Order;
import geniusee.task.testtaskgeniusee.repository.OrderRepository;
import geniusee.task.testtaskgeniusee.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;

  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order save(Order order) {
    return orderRepository.save(order);
  }

  public Order update(Order order) {
    return orderRepository.save(order);
  }

  public Order get(Long id) {
    return orderRepository.getReferenceById(id);
  }

  public void delete(Long id) {
    orderRepository.deleteById(id);
  }
}

package geniusee.task.testtaskgeniusee.controller;

import geniusee.task.testtaskgeniusee.dto.OrderRequest;
import geniusee.task.testtaskgeniusee.mapper.OrderRequestMapper;
import geniusee.task.testtaskgeniusee.model.Order;
import geniusee.task.testtaskgeniusee.service.OrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderRequestMapper orderRequestMapper;

  public OrderController(OrderService orderService, OrderRequestMapper orderRequestMapper) {
    this.orderService = orderService;
    this.orderRequestMapper = orderRequestMapper;
  }

  @PostMapping
  public Order createOrder(@RequestBody OrderRequest orderRequest) {
    return orderService.save(orderRequestMapper.toModel(orderRequest));
  }

  @GetMapping("/{id}")
  public Order getOrder(@PathVariable Long id) {
    return orderService.get(id);
  }

  @PutMapping("/{id}")
  public Order updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
    Order order = orderRequestMapper.toModel(orderRequest);
    order.setId(id);
    return orderService.update(order);
  }

  @DeleteMapping("/{id}")
  public String deleteOrder(@PathVariable Long id) {
    orderService.delete(id);
    return "Order with id " + id + " was deleted.";
  }
}

package geniusee.task.testtaskgeniusee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import geniusee.task.testtaskgeniusee.dto.OrderRequest;
import geniusee.task.testtaskgeniusee.mapper.OrderRequestMapper;
import geniusee.task.testtaskgeniusee.model.Order;
import geniusee.task.testtaskgeniusee.service.OrderService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

  @Value("classpath:json-schemas/orderRequest.json")
  private Resource orderRequestJson;

  @Value("classpath:json-schemas/orderModel.json")
  private Resource orderModelJson;

  @MockBean private OrderService orderService;

  @Autowired private MockMvc mockMvc;

  @Mock private OrderRequestMapper orderRequestMapper;

  private OrderRequest orderRequest;
  private Order orderModel;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void setUp() {
    objectMapper.findAndRegisterModules();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @BeforeEach
  void beforeEach() throws IOException {
    orderRequest =
        objectMapper.readValue(
            orderRequestJson.getInputStream().readAllBytes(), OrderRequest.class);

    orderModel =
        objectMapper.readValue(orderModelJson.getInputStream().readAllBytes(), Order.class);
  }

  @Test
  public void givenOrderRequestShouldSaveAndReturn200() throws Exception {
    when(orderRequestMapper.toModel(orderRequest)).thenReturn(orderModel);
    when(orderService.save(any())).thenReturn(orderModel);

    mockMvc
        .perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(orderModel)));
  }

  @Test
  public void givenOrderRequestShouldUpdateAndReturnModel() throws Exception {
    when(orderRequestMapper.toModel(orderRequest)).thenReturn(orderModel);
    when(orderService.update(any())).thenReturn(orderModel);

    mockMvc
        .perform(
            put("/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(orderModel)));
  }

  @Test
  public void givenIdShouldReturnCorrectOrder() throws Exception {
    when(orderService.get(1L)).thenReturn(orderModel);

    mockMvc
        .perform(get("/orders/{id}", 1L))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(orderModel)));
  }

  @Test
  public void givenEmptyOrderTableShouldReturnEmptyResponse() throws Exception {
    mockMvc.perform(get("/orders/{id}", 1L)).andExpect(content().string(""));
  }

  @Test
  public void givenIdShouldCallDeleteMethodAtLeastOnce() throws Exception {
    mockMvc.perform(delete("/orders/{id}", 1L)).andExpect(status().is2xxSuccessful());

    verify(orderService).delete(1L);
  }
}

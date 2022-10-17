package geniusee.task.testtaskgeniusee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
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
import geniusee.task.testtaskgeniusee.dto.MovieRequest;
import geniusee.task.testtaskgeniusee.mapper.MovieRequestMapper;
import geniusee.task.testtaskgeniusee.model.Movie;
import geniusee.task.testtaskgeniusee.service.MovieService;
import java.io.IOException;
import java.util.List;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

  @Value("classpath:json-schemas/movieRequest.json")
  private Resource movieRequestJson;

  @Value("classpath:json-schemas/movieModel.json")
  private Resource movieModelJson;

  @MockBean private MovieService movieService;

  @Autowired private MockMvc mockMvc;

  @Mock private MovieRequestMapper movieRequestMapper;

  private MovieRequest movieRequest;
  private Movie movieModel;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void setUp() {
    objectMapper.findAndRegisterModules();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @BeforeEach
  void beforeEach() throws IOException {
    movieRequest =
        objectMapper.readValue(
            movieRequestJson.getInputStream().readAllBytes(), MovieRequest.class);

    movieModel =
        objectMapper.readValue(movieModelJson.getInputStream().readAllBytes(), Movie.class);
  }

  @Test
  public void givenMovieRequestShouldSaveAndReturn200() throws Exception {
    when(movieRequestMapper.toModel(movieRequest)).thenReturn(movieModel);
    when(movieService.save(any())).thenReturn(movieModel);

    mockMvc
        .perform(
            post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieRequest)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(movieModel)));
  }

  @Test
  public void givenMovieRequestShouldUpdateAndReturnModel() throws Exception {
    when(movieRequestMapper.toModel(movieRequest)).thenReturn(movieModel);
    when(movieService.update(any())).thenReturn(movieModel);

    mockMvc
        .perform(
            put("/movies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieRequest)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(movieModel)));
  }

  @Test
  public void givenIdShouldReturnCorrectMovie() throws Exception {
    when(movieService.get(1L)).thenReturn(movieModel);

    mockMvc
        .perform(get("/movies/{id}", 1L))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(movieModel)));
  }

  @Test
  public void givenEmptyMovieTableShouldReturnEmptyResponse() throws Exception {
    mockMvc.perform(get("/movies/{id}", 1L)).andExpect(content().string(""));
  }

  @Test
  public void givenIdShouldCallDeleteMethodAtLeastOnce() throws Exception {
    mockMvc.perform(delete("/movies/{id}", 1L)).andExpect(status().is2xxSuccessful());

    verify(movieService).delete(1L);
  }

  @Test
  public void givenCriteriaShouldReturnListWithOneMovie() throws Exception {
    when(movieService.findAll(anyMap(), any(PageRequest.class))).thenReturn(List.of(movieModel));
    mockMvc
        .perform(get("/movies?titleIn=BestMovie2022}", 1L))
        .andExpect(status().is2xxSuccessful())
        .andExpect(
            content().string(List.of(objectMapper.writeValueAsString(movieModel)).toString()));
  }
}

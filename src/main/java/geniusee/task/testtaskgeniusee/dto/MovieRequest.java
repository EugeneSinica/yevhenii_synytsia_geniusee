package geniusee.task.testtaskgeniusee.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieRequest {
  private String title;
  private String description;
  private int cinemaHallNumber;
  private String showTime;
}

package com.hiddenartist.backend.domain.exhibition.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ExhibitionSimpleResponse extends ExhibitionResponse {

  @JsonProperty("start_date")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate startDate;

  @JsonProperty("end_date")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate endDate;

  public ExhibitionSimpleResponse(String name, String token, String image, LocalDate startDate, LocalDate endDate) {
    super(name, token, image);
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static ExhibitionSimpleResponse create(Exhibition exhibition) {
    return new ExhibitionSimpleResponse(
        exhibition.getName(),
        exhibition.getToken(),
        exhibition.getImage(),
        exhibition.getStartDate(),
        exhibition.getEndDate());
  }

}
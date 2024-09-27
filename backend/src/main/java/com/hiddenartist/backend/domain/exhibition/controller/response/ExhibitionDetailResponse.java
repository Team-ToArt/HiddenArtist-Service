package com.hiddenartist.backend.domain.exhibition.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.exhibition.persistence.ExhibitionLocation;
import com.hiddenartist.backend.domain.exhibition.persistence.ExhibitionManager;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class ExhibitionDetailResponse extends ExhibitionResponse {

  private String description;

  @JsonProperty("start_date")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate startDate;

  @JsonProperty("end_date")
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate endDate;

  @JsonProperty("open_time")
  @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private LocalTime openTime;

  @JsonProperty("close_time")
  @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private LocalTime closeTime;

  @JsonProperty("closed_days")
  private String closedDays;

  @JsonProperty("admission_fee")
  private Integer admissionFee;

  private LocationInfo location;

  private ManagerInfo manager;

  private ExhibitionDetailResponse(String name, String token, String image, String description, LocalDate startDate,
      LocalDate endDate, LocalTime openTime, LocalTime closeTime, String closedDays, Integer admissionFee,
      LocationInfo location, ManagerInfo manager) {
    super(name, token, image);
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.closedDays = closedDays;
    this.admissionFee = admissionFee;
    this.location = location;
    this.manager = manager;
  }

  public static ExhibitionDetailResponse create(Exhibition exhibition) {
    ExhibitionLocation location = exhibition.getLocation();
    LocationInfo locationInfo = new LocationInfo(location.getName(), location.getAddress(), location.getLatitude(),
        location.getLongitude());
    ExhibitionManager manager = exhibition.getManager();
    ManagerInfo managerInfo = new ManagerInfo(manager.getManagerName(), manager.getEmail(), manager.getTel());
    return new ExhibitionDetailResponse(
        exhibition.getName(),
        exhibition.getToken(),
        exhibition.getImage(),
        exhibition.getDescription(),
        exhibition.getStartDate(),
        exhibition.getEndDate(),
        exhibition.getOpenTime(),
        exhibition.getCloseTime(),
        exhibition.getClosedDays(),
        exhibition.getAdmissionFee(),
        locationInfo,
        managerInfo
    );
  }

  public record LocationInfo(
      String name,

      String address,

      Double latitude,

      Double longitude
  ) {

  }

  public record ManagerInfo(
      String name,
      String email,
      String tel
  ) {

  }
}

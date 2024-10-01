package com.hiddenartist.backend.domain.search.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentor;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import lombok.Getter;

@Getter
public class MentoringSearchResponse extends MentoringResponse {

  private MentorResponse mentor;

  private MentoringSearchResponse(String name, String token, String image, MentorResponse mentor) {
    super(name, token, image);
    this.mentor = mentor;
  }

  public static MentoringSearchResponse create(Mentoring mentoring) {
    Mentor mentor = mentoring.getMentor();
    Account account = mentor.getAccount();
    MentorResponse mentorResponse = new MentorResponse(account.getNickname(), account.getProfileImage(),
        mentor.getCareer().getDescription(), mentor.getOrganization());
    return new MentoringSearchResponse(mentoring.getName(), mentoring.getToken(), mentoring.getImage(), mentorResponse);
  }

  public record MentorResponse(
      String name,

      @JsonProperty("profile_image")
      String profileImage,

      String career,

      String organization
  ) {

  }
}
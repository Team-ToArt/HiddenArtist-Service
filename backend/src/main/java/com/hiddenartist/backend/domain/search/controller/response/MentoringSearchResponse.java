package com.hiddenartist.backend.domain.search.controller.response;

import com.hiddenartist.backend.domain.Mentoring.controller.response.MentoringResponse;
import com.hiddenartist.backend.domain.Mentoring.persistence.Mentor;
import com.hiddenartist.backend.domain.Mentoring.persistence.Mentoring;
import lombok.Getter;

@Getter
public class MentoringSearchResponse extends MentoringResponse {

  private MentorResponse mentor;

  private MentoringSearchResponse(String name, String token, MentorResponse mentor) {
    super(name, token);
    this.mentor = mentor;
  }

  public static MentoringSearchResponse create(Mentoring mentoring) {
    Mentor mentor = mentoring.getMentor();
    MentorResponse mentorResponse = new MentorResponse(mentor.getAccount().getNickname(),
        mentor.getCareer().getDescription(), mentor.getOrganization());
    return new MentoringSearchResponse(mentoring.getName(), mentoring.getToken(), mentorResponse);
  }

  public record MentorResponse(
      String name,
      String career,
      String organization
  ) {

  }
}
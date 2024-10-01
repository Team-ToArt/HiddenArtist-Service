package com.hiddenartist.backend.domain.mentoring.controller.response;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentor;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.search.controller.response.MentoringSearchResponse.MentorResponse;
import lombok.Getter;

@Getter
public class MentoringSimpleResponse extends MentoringResponse {

  private Integer amount;

  private MentorResponse mentor;

  private MentoringSimpleResponse(String name, String token, String image, Integer amount, MentorResponse mentor) {
    super(name, token, image);
    this.amount = amount;
    this.mentor = mentor;
  }

  public static MentoringSimpleResponse create(Mentoring mentoring) {
    Mentor mentor = mentoring.getMentor();
    Account account = mentor.getAccount();
    MentorResponse mentorResponse = new MentorResponse(account.getNickname(), account.getProfileImage(),
        mentor.getCareer().getDescription(), mentor.getOrganization());
    return new MentoringSimpleResponse(mentoring.getName(), mentoring.getToken(), mentoring.getImage(), mentoring.getAmount(),
        mentorResponse);
  }

}

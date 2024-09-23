package com.hiddenartist.backend.domain.Mentoring.controller.response;

import com.hiddenartist.backend.global.type.EntityToken;
import lombok.Getter;

@Getter
public abstract class MentoringResponse {

  private String name;

  private String token;

  protected MentoringResponse(String name, String token) {
    this.name = name;
    this.token = EntityToken.MENTORING.extractToken(token);
  }

}
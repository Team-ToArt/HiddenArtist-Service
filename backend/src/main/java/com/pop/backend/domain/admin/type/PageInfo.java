package com.pop.backend.domain.admin.type;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageInfo {

  DASH_BOARD("/admin/dashboard", "Admin Console"),
  LOGIN("/admin/login", "Admin Login");

  private static final String VIEW_NAME = "viewName";
  private static final String TITLE = "title";
  private final String viewName;
  private final String title;

  public static Map<String, String> getAttributes(PageInfo pageInfo) {
    Map<String, String> attributes = new HashMap<>();
    attributes.put(VIEW_NAME, pageInfo.viewName);
    attributes.put(TITLE, pageInfo.title);
    return attributes;
  }

}
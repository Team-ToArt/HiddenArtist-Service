package com.hiddenartist.backend.domain.admin.controller;

import com.hiddenartist.backend.domain.admin.type.PageInfo;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

  @GetMapping
  public String routeDashBoardPage(Model model) {
    Map<String, String> attributes = PageInfo.DASH_BOARD.getAttributes();
    model.addAllAttributes(attributes);
    return "index";
  }

  @GetMapping("/signin")
  public String routeLoginPage(Model model) {
    Map<String, String> attributes = PageInfo.LOGIN.getAttributes();
    model.addAllAttributes(attributes);
    return "index";
  }

}
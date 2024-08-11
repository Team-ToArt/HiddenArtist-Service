package com.pop.backend.domain.admin.controller;

import com.pop.backend.domain.admin.type.PageInfo;
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
    Map<String, String> attributes = PageInfo.getAttributes(PageInfo.DASH_BOARD);
    model.addAllAttributes(attributes);
    return "index";
  }

  @GetMapping("/signin")
  public String routeLoginPage(Model model) {
    Map<String, String> attributes = PageInfo.getAttributes(PageInfo.LOGIN);
    model.addAllAttributes(attributes);
    return "index";
  }

}
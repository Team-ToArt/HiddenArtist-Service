package com.hiddenartist.backend.domain.admin.controller;

import com.hiddenartist.backend.domain.admin.type.PageInfo;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

  // 작가 조회
  @GetMapping("/artists")
  public String routeArtistsPage(
      @PageableDefault(page = 1, sort = {"name", "id"}, direction = Direction.ASC) Pageable pageable, Model model
  ) {
    // 페이징 처리해서 작가 조회
    return "index";
  }

  // 작품 조회
  @GetMapping("/artworks")
  public String routeArtworksPage(
      @PageableDefault(page = 1, sort = {"name", "id"}, direction = Direction.ASC) Pageable pageable, Model model
  ) {
    // 페이징 처리해서 작품 조회
    return "index";
  }

}
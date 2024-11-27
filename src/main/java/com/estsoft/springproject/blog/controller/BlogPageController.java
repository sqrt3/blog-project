package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleViewResponse;
import com.estsoft.springproject.blog.service.BlogService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogPageController {

  private final BlogService blogService;

  public BlogPageController(BlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping("/articles")
  public String showArticles(Model model) {
    List<ArticleViewResponse> articles = blogService.findAll().stream()
        .map(ArticleViewResponse::new)
        .toList();

    model.addAttribute("articles", articles);

    return "articleList";
  }

  @GetMapping("/articles/{id}")
  public String showArticle(@PathVariable Long id, Model model) {
    Article article = blogService.findBy(id);
    model.addAttribute("article", new ArticleViewResponse(article));
    return "article";
  }

  @GetMapping("/new-article")
  public String addArticle(@RequestParam(required = false) Long id, Model model) {
    if (id == null) {
      model.addAttribute("article", new ArticleViewResponse());
    } else {
      Article article = blogService.findBy(id);
      model.addAttribute("article", new ArticleViewResponse(article));
    }
    return "newArticle";
  }
}

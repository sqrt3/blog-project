package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> writeArticle(@RequestBody AddArticleRequest request) {
        Article article = blogService.saveArticle(request);
        //log.info("{}, {}", request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }
}

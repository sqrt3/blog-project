package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.dto.AddCommentRequest;
import com.estsoft.springproject.blog.domain.dto.ArticleContent;
import com.estsoft.springproject.blog.domain.dto.CommentContent;
import com.estsoft.springproject.blog.service.BlogCommentService;
import com.estsoft.springproject.blog.service.BlogService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ExternalApiController {
    private final BlogService blogService;
    private final BlogCommentService blogCommentService;

    public ExternalApiController(BlogService blogService, BlogCommentService blogCommentService) {
        this.blogService = blogService;
        this.blogCommentService = blogCommentService;
    }

    @GetMapping("/api/external")
    public String callApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<List<ArticleContent>> resultList =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        log.info("Status Code: {}", resultList.getStatusCode());

        List<ArticleContent> articleContentList = resultList.getBody();
        if (articleContentList != null && !articleContentList.isEmpty()) {
            articleContentList.forEach(articleContent -> {
                AddArticleRequest request = new AddArticleRequest(articleContent.getTitle(), articleContent.getBody());
                blogService.saveArticle(request);
            });
        }

        url = "https://jsonplaceholder.typicode.com/comments";
        ResponseEntity<List<CommentContent>> resultList2 =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<CommentContent> commentContentList = resultList2.getBody();
        if (commentContentList != null && !commentContentList.isEmpty()) {
            commentContentList.forEach(commentContent -> {
                // postId를 articleId로 변환하여 AddCommentRequest를 생성
                AddCommentRequest request = new AddCommentRequest(commentContent.getPostId(), commentContent.getBody());
                blogCommentService.saveComment(request);
            });
        }


        return "OK";
    }
}

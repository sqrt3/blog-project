package com.estsoft.springproject.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.dto.AddCommentRequest;
import com.estsoft.springproject.blog.domain.dto.ArticleContent;
import com.estsoft.springproject.blog.domain.dto.CommentContent;
import com.estsoft.springproject.blog.service.BlogCommentService;
import com.estsoft.springproject.blog.service.BlogService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class ExternalApiControllerTest {

  @Mock
  private BlogService blogService;

  @Mock
  private BlogCommentService blogCommentService;

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private ExternalApiController externalApiController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExternalApiCalls() {
    // Given
    String expectedResponse = "OK";
    String postsUrl = "https://jsonplaceholder.typicode.com/posts";
    String commentsUrl = "https://jsonplaceholder.typicode.com/comments";

    List<ArticleContent> mockArticles = List.of(
        new ArticleContent("Title1", "Body1"),
        new ArticleContent("Title2", "Body2")
    );

    List<CommentContent> mockComments = List.of(
        new CommentContent(1L, "Comment1"),
        new CommentContent(2L, "Comment2")
    );

    ResponseEntity<List<ArticleContent>> mockArticleResponse = new ResponseEntity<>(mockArticles,
        HttpStatus.OK);
    ResponseEntity<List<CommentContent>> mockCommentResponse = new ResponseEntity<>(mockComments,
        HttpStatus.OK);

    when(restTemplate.exchange(eq(postsUrl), eq(HttpMethod.GET), isNull(), any(
        ParameterizedTypeReference.class)))
        .thenReturn(mockArticleResponse);
    when(restTemplate.exchange(eq(commentsUrl), eq(HttpMethod.GET), isNull(),
        any(ParameterizedTypeReference.class)))
        .thenReturn(mockCommentResponse);

    // When
    String actualResponse = externalApiController.callApi();

    // Then
    assertEquals(expectedResponse, actualResponse);
    verify(blogService, times(100)).saveArticle(any(AddArticleRequest.class));
    verify(blogCommentService, times(500)).saveComment(any(AddCommentRequest.class));
  }
}

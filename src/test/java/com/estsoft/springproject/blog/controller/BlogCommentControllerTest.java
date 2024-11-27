package com.estsoft.springproject.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.AddCommentRequest;
import com.estsoft.springproject.blog.domain.dto.CommentResponse;
import com.estsoft.springproject.blog.domain.dto.CommentWithArticleResponse;
import com.estsoft.springproject.blog.service.BlogCommentService;
import com.estsoft.springproject.blog.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BlogCommentControllerTest {

  MockMvc mockMvc;

  @InjectMocks
  BlogCommentController blogCommentController;

  @Mock
  BlogCommentService blogCommentService;

  @Mock
  BlogService blogService;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(blogCommentController).build();
  }

  @Test
  void findCommentById_InvalidId() {
    // Given
    Long invalidCommentId = 999L;
    when(blogCommentService.findBy(invalidCommentId)).thenReturn(null);

    // When
    ResponseEntity<CommentResponse> response = blogCommentController.findCommentById(
        invalidCommentId);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(null, response.getBody());
    verify(blogCommentService).findBy(invalidCommentId);
  }

  @Test
  void writeCommentByArticleId_ArticleNotFound() {
    // Given
    Long articleId = 1L;
    AddCommentRequest request = new AddCommentRequest();
    request.setArticleId(articleId);
    when(blogService.findBy(articleId)).thenReturn(null);

    // When
    ResponseEntity<CommentWithArticleResponse> response = blogCommentController.writeCommentByArticleId(
        articleId, request);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNull(response.getBody());
    verify(blogService).findBy(articleId);
  }

  @Test
  void deleteById_ValidCommentId() {
    // Given
    Long commentId = 1L;
    Comment comment = new Comment();
    when(blogCommentService.findBy(commentId)).thenReturn(comment);

    // When
    ResponseEntity<Void> response = blogCommentController.deleteById(commentId);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(blogCommentService).findBy(commentId);
    verify(blogCommentService).deleteBy(commentId);
  }
}
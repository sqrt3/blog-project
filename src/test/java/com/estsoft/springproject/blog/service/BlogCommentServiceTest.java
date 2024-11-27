package com.estsoft.springproject.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.AddCommentRequest;
import com.estsoft.springproject.blog.domain.dto.UpdateCommentRequest;
import com.estsoft.springproject.blog.repository.BlogCommentRepository;
import com.estsoft.springproject.blog.repository.BlogRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BlogCommentServiceTest {

  @InjectMocks
  private BlogCommentService blogCommentService;

  @Mock
  private BlogCommentRepository blogCommentRepository;

  @Mock
  private BlogRepository blogRepository;

  private Article article;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    article = new Article("title", "content");
  }

  @Test
  void findByNotExistId() {
    // Given
    Long nonExistentId = 999L;
    when(blogCommentRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    // When
    Comment result = blogCommentService.findBy(nonExistentId);

    // Then
    assertNotNull(result);
    assertEquals(null, result.getCommentId());
  }

  @Test
  void saveCommentTest() {
    // Given
    Long articleId = 1L;
    AddCommentRequest request = new AddCommentRequest();
    request.setArticleId(articleId);
    request.setBody("Test comment");

    Article mocckArticle = new Article("Test Title", "Test Content");
    Comment mockComment = new Comment(mocckArticle, "Test comment");

    when(blogRepository.findById(articleId)).thenReturn(Optional.of(mocckArticle));
    when(blogCommentRepository.save(any(Comment.class))).thenReturn(mockComment);

    // When
    Comment savedComment = blogCommentService.saveComment(request);

    // Then
    assertNotNull(savedComment);
    assertEquals("Test comment", savedComment.getBody());
    assertEquals(mocckArticle, savedComment.getArticle());
    verify(blogRepository).findById(articleId);
    verify(blogCommentRepository).save(any(Comment.class));
  }

  @Test
  void updateCommentBody() {
    // Given
    Long commentId = 1L;
    String newBody = "Updated comment body";
    UpdateCommentRequest request = new UpdateCommentRequest();
    request.setBody(newBody);

    Comment existingComment = new Comment(article, "Original comment body");
    when(blogCommentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

    // When
    Comment updatedComment = blogCommentService.update(commentId, request);

    // Then
    assertNotNull(updatedComment);
    assertEquals(newBody, updatedComment.getBody());
    verify(blogCommentRepository).findById(commentId);
  }

  @Test
  void deleteByValidId() {
    // Given
    Long commentId = 1L;

    // When
    blogCommentService.deleteBy(commentId);

    // Then
    verify(blogCommentRepository).deleteById(commentId);
  }
}
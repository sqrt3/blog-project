package com.estsoft.springproject.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.repository.BlogRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class BlogServiceTest {

  @InjectMocks
  private BlogService blogService;

  @Mock
  private BlogRepository blogRepository;

  private Article article;
  private AddArticleRequest addArticleRequest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    article = new Article("title", "content");
    addArticleRequest = new AddArticleRequest("title", "content");
  }

  @Test
  void testSaveArticle() {
    // Given
    Mockito.when(blogRepository.save(any(Article.class))).thenReturn(article);

    // When
    Article savedArticle = blogService.saveArticle(addArticleRequest);

    // Then
    assertNotNull(savedArticle);
    assertEquals("title", savedArticle.getTitle());
    assertEquals("content", savedArticle.getContent());
    Mockito.verify(blogRepository, Mockito.times(1)).save(any(Article.class));
  }

  @Test
  void testFindAllArticles() {
    // Given
    Mockito.when(blogRepository.findAll()).thenReturn(List.of(article));

    // When
    List<Article> articles = blogService.findAll();

    // Then
    assertNotNull(articles);
    assertEquals(1, articles.size());
    assertEquals("title", articles.get(0).getTitle());
    Mockito.verify(blogRepository, Mockito.times(1)).findAll();
  }

  @Test
  void testFindArticleById() {
    // Given
    Mockito.when(blogRepository.findById(anyLong())).thenReturn(Optional.of(article));

    // When
    Article foundArticle = blogService.findBy(1L);

    // Then
    assertNotNull(foundArticle);
    assertEquals("title", foundArticle.getTitle());
    assertEquals("content", foundArticle.getContent());
    Mockito.verify(blogRepository, Mockito.times(1)).findById(anyLong());
  }

  @Test
  void testFindArticleById_NotFound() {
    // Given
    Mockito.when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      blogService.findBy(1L);
    });

    // Then
    assertEquals("id: 1", exception.getMessage());
    Mockito.verify(blogRepository, Mockito.times(1)).findById(anyLong());
  }

  @Test
  void testDeleteArticleById() {
    // Given
    Mockito.doNothing().when(blogRepository).deleteById(anyLong());

    // When
    blogService.deleteBy(1L);

    // Then
    Mockito.verify(blogRepository, Mockito.times(1)).deleteById(anyLong());
  }
}
package com.estsoft.springproject.blog.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.estsoft.springproject.blog.domain.Article;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ArticleResponseTest {

  @Test
  void testArticleResponseWithNoArgsConstructor() {
    ArticleResponse articleResponse = new ArticleResponse();

    assertNotNull(articleResponse);
    assertNull(articleResponse.getId());
    assertNull(articleResponse.getTitle());
    assertNull(articleResponse.getContent());
    assertNull(articleResponse.getCreatedAt());
    assertNull(articleResponse.getUpdatedAt());
  }

  @Test
  void testsArticleToArticleResponse() {
    Long expectedId = 1L;
    String expectedTitle = "Sample Title";
    String expectedContent = "Sample Content";
    LocalDateTime expectedCreatedAt = LocalDateTime.of(2023, 10, 1, 12, 0);
    LocalDateTime expectedUpdatedAt = LocalDateTime.of(2023, 10, 2, 12, 0);

    Article article = new Article();
    article.setId(expectedId);
    article.setTitle(expectedTitle);
    article.setContent(expectedContent);
    article.setCreatedAt(expectedCreatedAt);
    article.setUpdatedAt(expectedUpdatedAt);

    ArticleResponse articleResponse = new ArticleResponse(article);

    assertNotNull(articleResponse);
    assertEquals(expectedId, articleResponse.getId());
    assertEquals(expectedTitle, articleResponse.getTitle());
    assertEquals(expectedContent, articleResponse.getContent());
    assertEquals(expectedCreatedAt, articleResponse.getCreatedAt());
    assertEquals(expectedUpdatedAt, articleResponse.getUpdatedAt());
  }

}

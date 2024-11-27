package com.estsoft.springproject.blog.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleTest2 {


  @Test
  void testArticlesUpdateMethod() {
    // Given
    Article article = new Article("Old Title", "Old Content");

    // When
    article.update("New Title", "New Content");

    // Then
    assertEquals("New Title", article.getTitle());
    assertEquals("New Content", article.getContent());
  }
}

package com.estsoft.springproject.blog.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.estsoft.springproject.blog.domain.Article;
import org.junit.jupiter.api.Test;

class ArticleContentTest {

  @Test
  void testArticleContentTest() {
    // Given
    String expectedTitle = "Sample Title";
    String expectedBody = "Sample Body";
    ArticleContent articleContent = new ArticleContent(expectedTitle, expectedBody);

    // When
    Article article = articleContent.toArticle();

    // Then
    assertEquals(expectedTitle, article.getTitle());
    assertEquals(expectedBody, article.getContent());
  }

  @Test
  void testArticleContentTestsToString() {
    // Given
    String title = "Test Title";
    String body = "Test Body";
    ArticleContent articleContent = new ArticleContent(title, body);
    String expectedString = "Content [title=" + title + ", body=" + body + "]\n";

    // When
    String result = articleContent.toString();

    // Then
    assertEquals(expectedString, result);
  }
}

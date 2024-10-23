package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleWithCommentsResponse;
import com.estsoft.springproject.blog.domain.dto.CommentResponse;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.repository.BlogCommentRepository;
import com.estsoft.springproject.blog.repository.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogCommentRepository blogCommentRepository;

    public BlogService(BlogRepository blogRepository, BlogCommentRepository blogCommentRepository) {
        this.blogRepository = blogRepository;
        this.blogCommentRepository = blogCommentRepository;
    }

    public Article saveArticle(AddArticleRequest request) {
        Article article = new Article(request.getTitle(), request.getContent());
        return blogRepository.save(article);
    }

    public List<Article> findAll() {
        List<Article> articles = blogRepository.findAll();
        return articles;
    }

    public Article findBy(Long id) {
        //return blogRepository.findById(id).orElse(new Article()); // Optional
        //return blogRepository.findById(id).orElseGet(Article::new); // Supplier
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id: " + id)); // Supplier but Throws Exception
    }

    public void deleteBy(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = findBy(id);
        article.update(request.getTitle(), request.getContent());
        return article;
    }

    public ArticleWithCommentsResponse findArticleWithComments(Long articleId) {
        Article article = blogRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));

        List<CommentResponse> comments = blogCommentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> new CommentResponse(comment.getCommentId(), articleId, comment.getBody(), comment.getCreatedAt(), article.toEntity()))
                .collect(Collectors.toList());

        return new ArticleWithCommentsResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                comments
        );
    }
}

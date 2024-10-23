package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.AddCommentRequest;
import com.estsoft.springproject.blog.domain.dto.UpdateCommentRequest;
import com.estsoft.springproject.blog.repository.BlogCommentRepository;
import com.estsoft.springproject.blog.repository.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentService {
    private final BlogCommentRepository blogCommentRepository;
    private final BlogRepository blogRepository;

    public BlogCommentService(BlogRepository blogRepository, BlogCommentRepository blogCommentRepository) {
        this.blogRepository = blogRepository;
        this.blogCommentRepository = blogCommentRepository;
    }

    public Comment findBy(Long id) {
        return blogCommentRepository.findById(id).orElse(new Comment());
    }

    public Comment saveComment(AddCommentRequest request) {
        Article article = blogRepository.findById(request.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));

        Comment comment = request.toEntity();
        comment.setArticle(article); // DB에서 조회한 Article 설정
        blogCommentRepository.save(comment);
        return comment;
    }


    @Transactional
    public Comment update(Long id, UpdateCommentRequest request) {
        Comment comment = findBy(id);
        comment.update(request.getBody());
        return comment;
    }

    public void deleteBy(Long id) {
        blogCommentRepository.deleteById(id);
    }
}

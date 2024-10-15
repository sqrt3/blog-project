package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Article saveArticle(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}

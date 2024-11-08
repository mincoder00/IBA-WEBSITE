package com.iba.springbootdeveloper.service;

import com.iba.springbootdeveloper.domain.Article;
import com.iba.springbootdeveloper.domain.Role;
import com.iba.springbootdeveloper.domain.User;
import com.iba.springbootdeveloper.dto.AddArticleRequest;
import com.iba.springbootdeveloper.dto.UpdateArticleRequest;
import com.iba.springbootdeveloper.repository.BlogRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserService userService;

    public Article save(AddArticleRequest request, String userName) {

        authorizeManager();

        String nickname = userService.getNicknameByEmail(userName);
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .author(userName)
                .nickname(nickname)
                .build();
        blogRepository.save(article);
        return article;
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAccess(article);
        blogRepository.delete(article);
    }


    public Page<Article> getList(int page, String kw, String category) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts));
        Specification<Article> spec = search(kw, category);
        return blogRepository.findAll(spec, pageable);
    }

    private Specification<Article> search(String keyword, String category) {
        return (Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(cb.or(
                        cb.like(root.get("title"), "%" + keyword + "%"),
                        cb.like(root.get("content"), "%" + keyword + "%"),
                        cb.equal(root.get("nickname"), keyword)
                ));
            }

            if (category != null && !category.isEmpty()) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        authorizeArticleAccess(article);
        article.update(request.getTitle(), request.getContent(), request.getCategory());
        return article;
    }

    private void authorizeArticleAccess(Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByEmail(userName);

        // Admins have full access to all articles
        if (user.getRole() == Role.ADMIN) {
            return; // Admins are authorized
        }

        // Managers can access their own articles
        if (user.getRole() == Role.MANAGER && article.getAuthor().equals(userName)) {
            return; // Manager is authorized for their own articles
        }

        // Throw an exception if neither condition is met
        throw new IllegalArgumentException("not authorized");
    }

    public void authorizeManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByEmail(userName);
        if (user.getRole() != Role.MANAGER && user.getRole() != Role.ADMIN ) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}
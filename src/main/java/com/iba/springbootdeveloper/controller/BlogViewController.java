package com.iba.springbootdeveloper.controller;

import com.iba.springbootdeveloper.domain.Article;
import com.iba.springbootdeveloper.dto.ArticleViewResponse;
import com.iba.springbootdeveloper.service.BlogService;
import com.iba.springbootdeveloper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    @Autowired
    private UserService userService;
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "kw", defaultValue = "") String kw,
                              @RequestParam(value = "category", required = false) String category) {
        Page<Article> paging = blogService.getList(page, kw, category);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("category", category);
        model.addAttribute("currentPage", page);

        return "articleList";
    }

    @GetMapping("/articles/category/{category}")
    public String getArticlesByCategory(@PathVariable("category") String category,
                                        Model model,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Article> paging = blogService.getList(page, kw, category);
        model.addAttribute("paging", paging);
        model.addAttribute("category", category);
        model.addAttribute("kw", kw);
        model.addAttribute("currentPage", page);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model) {
        Article article = blogService.findById(id);
        String nickname = userService.getNicknameByEmail(article.getAuthor());
        model.addAttribute("article", new ArticleViewResponse(article));
        model.addAttribute("category", article.getCategory());
        model.addAttribute("nickname", nickname);
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
            model.addAttribute("category", article.getCategory());
        }
        return "newArticle";
    }
}
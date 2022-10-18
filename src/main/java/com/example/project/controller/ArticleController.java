package com.example.project.controller;

import com.example.project.dto.ArticleForm;
import com.example.project.dto.CommentDto;
import com.example.project.entity.Article;
import com.example.project.repository.ArticleRepository;
import com.example.project.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {

    @Autowired //스프링부트가 미리 생성해놓은 객체를 가져다가 연결
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;


    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    //컨트롤러에서 form데이터를 받을때 객체로받는다 그 객체는 DTO
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
//        System.out.println(form.toString());
        log.info(form.toString());

        // 1. DTO를 entity로 변환한다.
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. DB에 저장하게하는 Repository에게 Entity를 DB안에 저장하게함.
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getID();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id : "+id);

        // 1. id로 데이터를 가져옴!! (리파지토리통해 DB에서 꺼내와서 entity로 변환)
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos",commentDtos);

        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. : 모든 Article을 가져온다
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article묶음을 뷰로전달 (뷰로전달시model사용)
        model.addAttribute("articleList",articleEntityList);

        // 3. 뷰페이지를 설정한다
        return "articles/index";
    }

    //수정데이터 가져오기
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터를 등록하기
        model.addAttribute("article",articleEntity);

        return "articles/edit";
    }

    //데이터 수정하기
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        //1. DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //2. 엔티티를 DB로 저장
        //2-1. DB에서 기존데이터를 가져온다
        Article target = articleRepository.findById(articleEntity.getID()).orElse(null);
        //2-2. 기존 데이터의 값을 갱신한다
        if(target !=null){
            articleRepository.save(articleEntity); //엔티티가 DB로 갱신
        }

        //3. 수정 결과페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getID();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("delete");
        // 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //대상을 삭제한다
        if(target !=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","delete OK");
        }
        //결과페이지로 리다이렉트
        return "redirect:/articles";
    }
}

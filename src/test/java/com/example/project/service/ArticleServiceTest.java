package com.example.project.service;

import com.example.project.dto.ArticleForm;
import com.example.project.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스트됨.
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        //예상
        Article a = new Article(1L, "가가가가", "1111"  );
        Article b = new Article(2L, "나나나나", "2222"  );
        Article c = new Article(3L, "다다다다", "3333"  );

        List<Article> expected= new ArrayList<Article>(Arrays.asList(a,b,c));
        //실제
        List<Article> articles = articleService.index();
        //결과
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공__존재하는id입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(id,"가가가가","1111");

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    void show_실패____존재하지않는_id_입력() {
        //예상
        Long id = -1L;
        Article expected = null;

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void create_성공___title___content만있는__dto입력() {

        //예상
        String title = "라라라라";
        String content="4444";
        ArticleForm dto = new ArticleForm(null,title,content);
        Article expected = new Article(4L,title,content);

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {

        //예상
        String title = "";
        String content="";
        ArticleForm dto = new ArticleForm(4L,title,content);
        Article expected = null;

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected,article);
    }

    @Test
    void update_성공__존재하는_id_와_title_content가_있는_dto() {
        //예상
        Long id = 1L;
        String title = "갸갸갸갸";
        String content = "1112";
        ArticleForm dto = new ArticleForm(id,title,content);
        Article expected = new Article(id,title,content);

        //실제
        Article article = articleService.update(id,dto);

        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    void update_실패__존재하지않는_id의_dto_입력() {
        //예상
        Long id = -1L;
        String title = "갸갸갸갸";
        String content = "2313";
        ArticleForm dto = new ArticleForm(id,title,content);
        Article expected = null;

        //실제
        Article article = articleService.update(id,dto);

        //비교
        assertEquals(expected,article);
    }

    @Test
    void delete__성공() {
        //예상
        Long id = 1L;
        Article expected = new Article(id,"가가가가","1111");

        //실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected.toString(),article.toString());


    }

    @Test
    void delete__실패() {
        //예상
        Long id = -1L;
        Article expected = null;

        //실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected,article);

    }
}
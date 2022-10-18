package com.example.project.service;

import com.example.project.dto.ArticleForm;
import com.example.project.entity.Article;
import com.example.project.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언 어노테이션 (서비스 객체를 스프링부트에 생성)
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getID()!=null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. 수정용 엔티티 작성
        Article article = dto.toEntity();
        //2. 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청처리
        if(target ==null || id != article.getID()){
            return null;
        }
        //4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        //대상찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청처리
        if(target ==null){
            return null;
        }

        //대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당메소드를 트랜잭션으로 묶는다.
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto묶음을 엔티티묶음으로변환
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
//        List<Article> articleList = new ArrayList<>();
//        for(int i=0; i<dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // entity묶음을 DB로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));

//        for(int i=0; i<articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패"));

        //결과값 반환
        return articleList;
    }
}

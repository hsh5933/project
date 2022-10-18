package com.example.project.api;

import com.example.project.dto.ArticleForm;
import com.example.project.entity.Article;
import com.example.project.repository.ArticleRepository;
import com.example.project.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController //RestApi용 컨트롤러! (데이터를 반환 JSON으로)
public class ArticleApiController {

    /*HTTP 주요메서드 5가지
     * 1. GET방식 : 리소스 조회
     * 2. POST방식 : 요청 데이터 처리, 주로 등록하는데 사용
     * 3. PUT방식 : 리소스를 대체, 해당 리소스없으면 생성한다.
     * 4. PATCH방식 : 리소스 부분 수정
     * 5. DELETE방식 : 리소스 삭제*/

    @Autowired //DI, 생성 객체를 가져와서 연결
    private ArticleService articleService;

    //GET  //모든 데이터 json출력
    // 리스트 전체출력을위해 List타입으로
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    //하나의 객체만 뽑을꺼기때문에 list빼고
    @GetMapping("/api/articles/{id}")  //단일 정보출력
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created !=null) ? ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated =  articleService.update(id, dto);
        return (updated !=null)?ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted !=null) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //트랜잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article >createdList = articleService.createArticles(dtos);
        return (createdList !=null)? ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

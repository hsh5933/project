package com.example.project.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestAPI 용 컨트롤러 JSON을 반환한다.
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello(){
        return "Hello World";
    }
}

package com.example.project.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BurgerTest {

    @Test
    public void 자바_객체를_JSON으로_변환() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients = Arrays.asList("패티","소고기패티","토마토","소스");
        Burger burger = new Burger("맥도날드 슈비버거",5500,ingredients);

        //수행
        String json = objectMapper.writeValueAsString(burger);

        //예상
        String expected = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"패티\",\"소고기패티\",\"토마토\",\"소스\"]}";

        //검증
        assertEquals(expected,json);
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
    }

    @Test
    public void JSON을_자바객체로_변환() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"패티\",\"소고기패티\",\"토마토\",\"소스\"]}";

        //수행
        Burger burger = objectMapper.readValue(json, Burger.class);


        //예상
        List<String> ingredients = Arrays.asList("패티","소고기패티","토마토","소스");
        Burger expected = new Burger("맥도날드 슈비버거",5500,ingredients);

        //검증
        assertEquals(expected.toString(),burger.toString());
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
        System.out.println(burger.toString());
    }
}
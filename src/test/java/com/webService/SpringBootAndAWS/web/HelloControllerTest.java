package com.webService.SpringBootAndAWS.web;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("hello 가 리턴된다.")
    void helloTest() throws Exception {

        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @DisplayName("helloDto 가 리턴된다.")
    void helloDtoTest() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
        * .param() -> 파라미터를 검증합니다. (String 만 검증할 수 있기 때문에 String 으로 변환해줘야 한다)
        * .jsonPath() -> $를 기준으로 필드명 명시
        *
        * */
        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}
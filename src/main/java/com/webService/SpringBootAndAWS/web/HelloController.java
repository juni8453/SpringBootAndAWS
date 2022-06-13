package com.webService.SpringBootAndAWS.web;

import com.webService.SpringBootAndAWS.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(
            @RequestParam("name") String name,
            @RequestParam("amount") int amount) {

        // 엔티티를 직접 Response 하는게 아닌, Dto 를 Response 해야 한다.
        return new HelloResponseDto(name, amount);
    }
}

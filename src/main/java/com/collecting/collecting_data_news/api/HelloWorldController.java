package com.collecting.collecting_data_news.api;

import com.sun.source.doctree.SeeTree;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/test")
    public String helloWorld() {
        return "hello";
    }
}

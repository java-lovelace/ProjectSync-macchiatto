package com.crudactivity.projectsync.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {


    @GetMapping("/")
    public String root() {
        return "forward:/index.html";
    }


    @GetMapping({
            "/projects", "/projects/{path:[\\w\\-]+}", "/projects/{path:^(?!api).*}/**",
            "/users", "/users/{path:[\\w\\-]+}", "/users/{path:^(?!api).*}/**"
    })
    public String spaRoutes() {
        return "forward:/index.html";
    }
}

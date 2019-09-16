package com.freenow.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController
{

    @RequestMapping("/")
    public String home()
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final boolean isLoggedIn = !(auth instanceof AnonymousAuthenticationToken);
        if (isLoggedIn)
        {
            return "redirect:swagger-ui.html";
        }
        return "redirect:login";
    }

}

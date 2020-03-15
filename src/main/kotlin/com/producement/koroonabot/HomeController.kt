package com.producement.koroonabot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
class HomeController {

    @GetMapping("/", "/install")
    fun install(): RedirectView {
        return RedirectView("https://slack.com/oauth/authorize?client_id=222663112231.1002854044022&scope=incoming-webhook,bot")
    }

    @GetMapping("/installed")
    fun installed(): String {
        return "KoroonaBot paigaldatud."
    }

    @GetMapping("/failed")
    fun fail(): String {
        return "KoroonaBoti paigaldamine ebaõnnestus."
    }
}

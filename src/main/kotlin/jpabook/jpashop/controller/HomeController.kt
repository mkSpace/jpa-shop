package jpabook.jpashop.controller

import org.apache.juli.logging.LogFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    private val log = LogFactory.getLog(javaClass)

    @RequestMapping("/")
    fun home(): String {
        log.info("Home controller")
        return "home"
    }
}
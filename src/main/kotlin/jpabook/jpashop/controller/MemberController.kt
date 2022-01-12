package jpabook.jpashop.controller

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(private val memberService: MemberService) {

    @GetMapping("/members/new")
    fun createForm(model: Model): String {
        model.addAttribute("memberForm", MemberForm())
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Validated form: MemberForm, result: BindingResult): String {
        if (result.hasErrors()) return "members/createMemberForm"

        val address = Address(form.city, form.street, form.zipCode)
        val member = Member(
                username = form.name ?: throw IllegalStateException("Form에서 이름이 비어서 들어옴."),
                address = address
        )

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        model.addAttribute("members", memberService.findMembers())
        return "members/membersList"
    }
}
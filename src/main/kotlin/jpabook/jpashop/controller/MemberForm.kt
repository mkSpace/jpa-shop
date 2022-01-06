package jpabook.jpashop.controller

import javax.validation.constraints.NotEmpty

class MemberForm(
        @field:NotEmpty(message = "회원 이름은 필수 입니다.") val name: String? = null,
        val city: String? = null,
        val street: String? = null,
        val zipCode: String? = null
)
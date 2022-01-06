package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun 회원가입() {
        //given
        val member = Member(username = "park")

        //when
        val savedId = memberService.join(member)

        //then
        assertEquals(member, memberRepository.findOne(savedId))
    }

    @Test
    fun 중복_회원_예외() {
        // given
        val member1 = Member(username = "park")
        val member2 = Member(username = "park")

        // when
        memberService.join(member1)

        // then
        Assertions.assertThrows(IllegalStateException::class.java) {
            memberService.join(member2)
        }
    }
}
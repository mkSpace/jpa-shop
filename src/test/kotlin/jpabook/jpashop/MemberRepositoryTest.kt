package jpabook.jpashop

import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    @Transactional
    fun testMember() {
        //given
        val member = Member(username = "memberA")

        //when
        val savedId = memberRepository.save(member)
        val findMember = savedId?.let { memberRepository.find(it) } ?: error("Find message is null")

        //then
        assertThat(findMember.id).isEqualTo(member.id)
        assertThat(findMember.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }
}
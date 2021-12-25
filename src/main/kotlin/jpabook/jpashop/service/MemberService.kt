package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {

    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id ?: throw IllegalStateException("회원이 정상적으로 생성되지 않았습니다. member: $member")
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.username)
        if (findMembers.isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    fun findMember(): List<Member> = memberRepository.findAll()

    fun findOne(id: Long): Member = memberRepository.findOne(id)
}
package jpabook.jpashop.repository

import jpabook.jpashop.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class MemberRepository(private val entityManager: EntityManager) {

    fun save(member: Member) {
        entityManager.persist(member)
    }

    fun findOne(id: Long): Member = entityManager.find(Member::class.java, id)

    fun findAll(): List<Member> = entityManager.createQuery("SELECT m FROM Member m", Member::class.java)
            .resultList

    fun findByName(name: String): List<Member> = entityManager
            .createQuery("SELECT m FROM Member m WHERE m.username = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
}
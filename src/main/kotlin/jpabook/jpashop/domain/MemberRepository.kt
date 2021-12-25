package jpabook.jpashop.domain

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun save(member: Member): Long? {
        entityManager.persist(member)
        return member.id
    }

    fun find(id: Long): Member {
        return entityManager.find(Member::class.java, id)
    }
}
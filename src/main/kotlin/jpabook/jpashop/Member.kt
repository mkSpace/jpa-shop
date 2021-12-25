package jpabook.jpashop

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Member(
        @Id @GeneratedValue val id: Long? = null,
        val username: String
)
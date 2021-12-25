package jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Member(
        @Id @GeneratedValue @Column(name = "member_id") val id: Long? = null,
        val username: String,
        @Embedded val address: Address,
        @OneToMany(mappedBy = "member") val orders: List<Order> = emptyList()
)
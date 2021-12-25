package jpabook.jpashop.domain

import javax.persistence.*

@Entity
class Member(
        @Id @GeneratedValue @Column(name = "member_id") val id: Long? = null,
        val username: String,
        @Embedded val address: Address? = null,
        @OneToMany(mappedBy = "member") var orders: List<Order> = emptyList()
)
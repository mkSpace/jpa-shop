package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
class Member(
        @Id @GeneratedValue @Column(name = "member_id") val id: Long? = null,
        @field:NotEmpty var username: String,
        @Embedded val address: Address? = null,
        @OneToMany(mappedBy = "member") @JsonIgnore var orders: List<Order> = emptyList()
)
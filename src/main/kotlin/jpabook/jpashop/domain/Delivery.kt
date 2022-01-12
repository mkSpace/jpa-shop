package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Delivery(
        @Id @GeneratedValue @Column(name = "delivery_id") val id: Long? = null,
        @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) @JsonIgnore var order: Order? = null,
        @Embedded val address: Address,
        @Enumerated(EnumType.STRING) val status: DeliveryStatus? = null
)

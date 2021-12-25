package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class OrderItem(
        @Id @GeneratedValue @Column(name = "order_item_id") val id: Long,
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "item_id") val item: Item,
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "order") var order: Order,
        val orderPrice: Int,
        val count: Int
)

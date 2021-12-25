package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*

@Entity
data class OrderItem(
        @Id @GeneratedValue @Column(name = "order_item_id") val id: Long,
        @ManyToOne @JoinColumn(name = "item_id") val item: Item,
        @ManyToOne @JoinColumn(name = "order") val order: Order,
        val orderPrice: Int,
        val count: Int
)

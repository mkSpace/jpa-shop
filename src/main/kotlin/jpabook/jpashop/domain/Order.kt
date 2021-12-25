package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "orders")
class Order(
        @Id @GeneratedValue @Column(name = "order_id")
        var id: Long? = null,
        orderMember: Member,
        @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL]) var orderItems: List<OrderItem> = emptyList(),
        orderDelivery: Delivery,
        var status: OrderStatus,
        var orderDate: LocalDateTime
) {
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "member_id")
    var member: Member = orderMember
        set(value) {
            field = value
            member.orders += this
        }

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL]) @JoinColumn(name = "delivery_id")
    var delivery: Delivery = orderDelivery
        set(value) {
            field = value
            delivery.order = this
        }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems += orderItem
        orderItem.order = this
    }
}
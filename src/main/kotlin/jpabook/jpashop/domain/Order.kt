package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "orders")
class Order(
        @Id @GeneratedValue @Column(name = "order_id") var id: Long? = null,
        orderMember: Member,
        @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL]) var orderItems: List<OrderItem> = ArrayList(),
        orderDelivery: Delivery,
        @Enumerated(EnumType.STRING) var status: OrderStatus,
        var orderDate: LocalDateTime
) {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = orderMember
        set(value) {
            field = value
            member.orders += this
        }

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery = orderDelivery
        set(value) {
            field = value
            delivery.order = this
        }

    val totalPrice: Int
        get() = orderItems.sumOf { it.totalPrice }

    companion object {
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order =  Order(
                    orderMember = member,
                    orderDelivery = delivery,
                    orderItems = orderItems.toList(),
                    status = OrderStatus.ORDERED,
                    orderDate = LocalDateTime.now()
            )
            orderItems.forEach { it.order = order }
            return order
        }
    }

    // 비즈니스 로직
    /**
     * 주문 취소
     */
    fun cancel() {
        if (delivery.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 발송완료된 상품은 취소가 불가능합니다.")
        }
        status = OrderStatus.CANCELLED
        orderItems.forEach { orderItem ->
            orderItem.cancel()
        }
    }
}
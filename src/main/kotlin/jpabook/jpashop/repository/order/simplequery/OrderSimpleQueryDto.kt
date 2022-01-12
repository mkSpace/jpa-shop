package jpabook.jpashop.repository.order.simplequery

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import lombok.Data
import java.time.LocalDateTime

@Data
data class OrderSimpleQueryDto(
        val orderId: Long?,
        val name: String?,
        val orderDate: LocalDateTime?,
        val orderStatus: OrderStatus?,
        val address: Address?
) {
    constructor(
            order: Order
    ) : this(order.id, order.member.username, order.orderDate, order.status, order.delivery.address)
}
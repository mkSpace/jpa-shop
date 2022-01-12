package jpabook.jpashop.repository.order.query

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderQueryDto @JvmOverloads constructor(
        val orderId: Long? = null,
        val name: String,
        val orderDate: LocalDateTime,
        val orderStatus: OrderStatus,
        val address: Address,
        var orderItems: List<OrderItemQueryDto>? = emptyList()
)
package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.query.OrderFlatDto
import jpabook.jpashop.repository.order.query.OrderQueryDto
import jpabook.jpashop.repository.order.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderApiController(
        private val orderRepository: OrderRepository,
        private val orderQueryRepository: OrderQueryRepository
) {

    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        val orders = orderRepository.findAllByString(OrderSearch())
        orders.forEach { order ->
            order.member.username
            order.delivery.address
            val orderItems = order.orderItems
            orderItems.forEach { order -> order.item.name }
        }
        return orders
    }

    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrderDto> {
        return orderRepository.findAllByString(OrderSearch())
                .map(::OrderDto)
    }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrderDto> {
        return orderRepository.findAllWithItems()
                .map(::OrderDto)
    }

    @GetMapping("/api/v3.1/orders")
    fun ordersV3Paging(
            @RequestParam(value = "offset", defaultValue = "0") offset: Int,
            @RequestParam(value = "limit", defaultValue = "100") limit: Int
    ): List<OrderDto> {
        return orderRepository.findAllWithMemberDelivery(offset, limit)
                .map(::OrderDto)
    }

    @GetMapping("/api/v4/orders")
    fun ordersV4(): List<OrderQueryDto> {
        return orderQueryRepository.findOrderQueryDtos()
    }

    @GetMapping("/api/v5/orders")
    fun ordersV5(): List<OrderQueryDto> {
        return orderQueryRepository.findAllByDtoOptimization()
    }

    @GetMapping("/api/v6/orders")
    fun ordersV6(): List<OrderFlatDto> {
        return orderQueryRepository.findAllByDtoFlatten()
    }

    data class OrderDto(
            val orderId: Long?,
            val name: String,
            val orderDate: LocalDateTime,
            val address: Address,
            val orderItems: List<OrderItemDto>
    ) {
        constructor(order: Order) : this(
                orderId = order.id,
                name = order.member.username,
                orderDate = order.orderDate,
                address = order.delivery.address,
                orderItems = order.orderItems.map(::OrderItemDto)
        )
    }

    data class OrderItemDto(
            val itemName: String?,
            val orderPrice: Int,
            val count: Int
    ) {
        constructor(orderItem: OrderItem) : this(
                itemName = orderItem.item.name,
                orderPrice = orderItem.orderPrice,
                count = orderItem.count
        )
    }
}
package jpabook.jpashop.api

import jpabook.jpashop.domain.Order
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * xToOne(ManyToOnde, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
class OrderSimpleApiController(
        private val orderRepository: OrderRepository,
        private val orderSimpleQueryRepository: OrderSimpleQueryRepository
) {

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> {
        return orderRepository.findAllByString(OrderSearch())
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<OrderSimpleQueryDto> {
        return orderRepository.findAllByString(OrderSearch())
                .map(::OrderSimpleQueryDto)
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<OrderSimpleQueryDto> {
        return orderRepository.findAllWithMemberDelivery()
                .map(::OrderSimpleQueryDto)
    }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrderSimpleQueryDto> {
        return orderSimpleQueryRepository.findOrderDtos()
    }
}
package jpabook.jpashop.service

import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.repository.ItemRepository
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
        private val orderRepository: OrderRepository,
        private val memberRepository: MemberRepository,
        private val itemRepository: ItemRepository
) {

    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        val member = memberRepository.findOne(memberId)
        val item = itemRepository.findOne(itemId)
        member.address ?: throw IllegalArgumentException("멤버의 주소가 비어있습니다.")
        val delivery = Delivery(address = member.address)
        val orderItem = OrderItem.createOrderItem(item, item.price, count)
        val order = Order.createOrder(member, delivery, orderItem)
        return orderRepository.save(order)
    }

    @Transactional
    fun cancelOrder(orderId: Long) {
        val order = orderRepository.findOne(orderId)
        order.cancel()
    }

//    fun findOrders(orderSearch: OrderSearch): List<Order> {
//        return orderRepository.findAll(orderSearch)
//    }
}
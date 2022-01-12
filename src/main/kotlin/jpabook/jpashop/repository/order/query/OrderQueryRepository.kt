package jpabook.jpashop.repository.order.query

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderQueryRepository(private val entityManager: EntityManager) {

    fun findOrderQueryDtos(): List<OrderQueryDto> {
        val result = findOrders()

        return result.mapNotNull { orderQueryDto ->
            orderQueryDto.orderId?.let {
                val orderItems = findOrderItems(it)
                orderQueryDto.apply {
                    this.orderItems = orderItems
                }
            }
        }
    }

    fun findAllByDtoOptimization(): List<OrderQueryDto> {
        val result = findOrders()
        val orderIds = result.mapNotNull { it.orderId }
        val orderItems = findOrderItems(orderIds)
        val orderItemMap = orderItems.groupBy { it.orderId }
        return result.map { order ->
            order.apply {
                this.orderItems = orderItemMap[order.orderId]
            }
        }
    }

    private fun findOrderItems(orderIds: List<Long>) = entityManager
            .createQuery(
                    "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                            "from OrderItem  oi " +
                            "join oi.item i " +
                            "where oi.order.id in :orderIds", OrderItemQueryDto::class.java
            )
            .setParameter("orderIds", orderIds)
            .resultList

    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return entityManager
                .createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                                "from OrderItem  oi " +
                                "join oi.item i " +
                                "where oi.order.id = :orderId", OrderItemQueryDto::class.java
                )
                .setParameter("orderId", orderId)
                .resultList
    }

    private fun findOrders(): List<OrderQueryDto> {
        return entityManager.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderQueryDto::class.java)
                .resultList
    }

    fun findAllByDtoFlatten(): List<OrderFlatDto> {
        return entityManager
                .createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.username, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                                "from Order o " +
                                "join o.member m " +
                                "join o.delivery d " +
                                "join o.orderItems oi " +
                                "join oi.item i",
                        OrderFlatDto::class.java
                ).resultList
    }
}
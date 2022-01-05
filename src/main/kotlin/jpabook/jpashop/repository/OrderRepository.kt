package jpabook.jpashop.repository

import jpabook.jpashop.domain.Order
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderRepository(private val entityManager: EntityManager) {

    fun save(order: Order): Long {
        entityManager.persist(order)
        return order.id ?: throw IllegalStateException("Order가 정상적으로 주문되지 않았습니다.")
    }

    fun findOne(id: Long): Order = entityManager.find(Order::class.java, id)

    fun findAll(orderSearch: OrderSearch): List<Order> {
        return entityManager.createQuery("SELECT 0 FROM Order o join o.member m WHERE o.status = :status and m.username like :name", Order::class.java)
                .setParameter("status", orderSearch.orderStatus)
                .setParameter("name", orderSearch.memberName)
                .setMaxResults(1000)
                .resultList
    }
}
package jpabook.jpashop.repository

import jpabook.jpashop.domain.Order
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

@Repository
class OrderRepository(private val entityManager: EntityManager) {

    fun save(order: Order): Long {
        entityManager.persist(order)
        return order.id ?: throw IllegalStateException("Order가 정상적으로 주문되지 않았습니다.")
    }

    fun findOne(id: Long): Order = entityManager.find(Order::class.java, id)

    fun findAll(orderSearch: OrderSearch): List<Order> {
        return entityManager.createQuery("SELECT 0 FROM Order o join o.member m WHERE o.status = :status and m.username like :name", Order::class.java).setParameter("status", orderSearch.orderStatus).setParameter("name", orderSearch.memberName).setMaxResults(1000).resultList
    }

    fun findAllByString(orderSearch: OrderSearch): List<Order> {
        var jpql = "select o From Order o join o.member m"
        var isFirstCondition = true

        if (orderSearch.orderStatus != null) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " o.status = :status"
        }
        if (StringUtils.hasText(orderSearch.memberName)) {
            if (isFirstCondition) {
                jpql += " where"
            } else {
                jpql += " and"
            }
            jpql += " m.username like :name"
        }

        var query: TypedQuery<Order?> = entityManager.createQuery(jpql, Order::class.java).setMaxResults(1000) //최대 1000건

        if (orderSearch.orderStatus != null) {
            query = query.setParameter("status", orderSearch.orderStatus)
        }

        if (StringUtils.hasText(orderSearch.memberName)) {
            query = query.setParameter("name", orderSearch.memberName)
        }
        return query.resultList.filterNotNull()
    }

    fun findAllWithMemberDelivery(): List<Order> {
        return entityManager.createQuery("select o from Order o " + "join fetch o.member m " + "join fetch o.delivery d", Order::class.java).resultList
    }

    fun findAllWithItems(): List<Order> {
        return entityManager
                .createQuery(
                        "select distinct o from Order o " +
                                "join fetch o.member m " +
                                "join fetch o.delivery d " +
                                "join fetch o.orderItems oi " +
                                "join fetch oi.item i", Order::class.java
                )
                .setFirstResult(0)
                .setMaxResults(100)
                .resultList
    }

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order> {
        return entityManager
                .createQuery("select o from Order o " + "join fetch o.member m " + "join fetch o.delivery d", Order::class.java)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .resultList
    }
}
package jpabook.jpashop.repository.order.simplequery

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderSimpleQueryRepository(private val entityManager: EntityManager) {
    fun findOrderDtos(): List<OrderSimpleQueryDto> {
        return entityManager.createQuery("select new jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address) from Order o " +
                "join o.member m " +
                "join o.delivery d",
                OrderSimpleQueryDto::class.java
        ).resultList
    }
}
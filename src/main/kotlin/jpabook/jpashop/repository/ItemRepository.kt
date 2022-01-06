package jpabook.jpashop.repository

import jpabook.jpashop.domain.item.Item
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ItemRepository(private val entityManager: EntityManager) {

    fun save(item: Item) {
        item.id?.let {
            entityManager.merge(item)
        } ?: entityManager.persist(item)
    }

    fun findOne(id: Long): Item = entityManager.find(Item::class.java, id)

    fun findAll(): List<Item> = entityManager.createQuery("SELECT i FROM Item i", Item::class.java).resultList
}
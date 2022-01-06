package jpabook.jpashop.service

import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {

    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    @Transactional
    fun updateItem(itemId: Long, name: String?, price: Int?, stockQuantity: Int?) {
        itemRepository.findOne(itemId).apply {
            this.name = name
            this.price = price
            this.stockQuantity = stockQuantity
        }
    }

    fun findItems(): List<Item> = itemRepository.findAll()

    fun findOne(itemId: Long): Item = itemRepository.findOne(itemId)
}
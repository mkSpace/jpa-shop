package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item(
        @Id @GeneratedValue @Column(name = "item_id") open var id: Long? = null,
        open var name: String? = null,
        open var price: Int? = null,
        open var stockQuantity: Int? = null,
        @ManyToMany(mappedBy = "items") open val categories: List<Category> = emptyList()
) {

    fun addStock(quantity: Int) {
        stockQuantity = (stockQuantity ?: 0) + quantity
    }

    fun removeStock(quantity: Int) {
        val restStock = (stockQuantity ?: 0) - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        stockQuantity = restStock
    }
}

package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item(
        @Id @GeneratedValue @Column(name = "item_id") open var id: Long?,
        open val name: String,
        open val price: Int,
        open var stockQuantity: Int,
        @ManyToMany(mappedBy = "items") open val categories: List<Category> = emptyList()
) {

    fun addStock(quantity: Int) {
        stockQuantity += quantity
    }

    fun removeStock(quantity: Int) {
        val restStock = stockQuantity - quantity
        if(restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        stockQuantity = restStock
    }
}

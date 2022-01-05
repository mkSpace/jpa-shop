package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import lombok.AccessLevel
import lombok.NoArgsConstructor
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class OrderItem(
        @Id @GeneratedValue @Column(name = "order_item_id") val id: Long? = null,
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "item_id") val item: Item,
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "order") var order: Order? = null,
        val orderPrice: Int,
        private val count: Int
) {

    val totalPrice: Int
        get() = orderPrice * count

    fun cancel() {
        item.addStock(count)
    }

    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem =  OrderItem(
                    item = item,
                    orderPrice = orderPrice,
                    count = count
            )
            item.removeStock(count)
            return orderItem
        }
    }
}

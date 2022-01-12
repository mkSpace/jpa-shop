package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "order_item")
class OrderItem(
        @Id @GeneratedValue @Column(name = "order_item_id") val id: Long? = null,
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "item_id") @JsonIgnore val item: Item,
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "order_id") @JsonIgnore var order: Order? = null,
        val orderPrice: Int,
        val count: Int
) {

    val totalPrice: Int
        get() = orderPrice * count

    fun cancel() {
        item.addStock(count)
    }

    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem(
                    item = item,
                    orderPrice = orderPrice,
                    count = count
            )
            item.removeStock(count)
            return orderItem
        }
    }
}

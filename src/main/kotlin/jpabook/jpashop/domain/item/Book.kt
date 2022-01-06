package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
data class Book(
        override var id: Long? = null,
        override var name: String? = null,
        override var price: Int? = null,
        override var stockQuantity: Int? = null,
        val author: String? = null,
        val isbn: String? = null
) : Item(id, name, price, stockQuantity)
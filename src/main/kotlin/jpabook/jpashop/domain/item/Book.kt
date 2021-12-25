package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
data class Book(
        override var id: Long?,
        override val name: String,
        override val price: Int,
        override var stockQuantity: Int,
        val author: String,
        val isbn: String
) : Item(id, name, price, stockQuantity)
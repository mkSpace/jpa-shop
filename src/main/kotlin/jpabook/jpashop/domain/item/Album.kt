package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
data class Album(
        override var id: Long?,
        override var name: String? = null,
        override var price: Int? = null,
        override var stockQuantity: Int? = null,
        val artist: String,
        val etc: String
) : Item(id, name, price, stockQuantity)
package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("M")
data class Movie(
        override var id: Long?,
        override var name: String? = null,
        override var price: Int? = null,
        override var stockQuantity: Int? = null,
        val director: String,
        val actor: String
) : Item(id, name, price, stockQuantity)
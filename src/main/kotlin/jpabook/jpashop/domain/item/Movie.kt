package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("M")
data class Movie(
        override var id: Long?,
        override val name: String,
        override val price: Int,
        override var stockQuantity: Int,
        val director: String,
        val actor: String
) : Item(id, name, price, stockQuantity)
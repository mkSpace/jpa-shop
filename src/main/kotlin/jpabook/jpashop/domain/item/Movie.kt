package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("M")
data class Movie(
        override val id: Long,
        override val name: String,
        override val price: Int,
        override val stockQuantity: Int,
        val director: String,
        val actor: String
) : Item(id, name, price, stockQuantity)
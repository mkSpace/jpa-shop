package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
data class Album(
        override val id: Long,
        override val name: String,
        override val price: Int,
        override val stockQuantity: Int,
        val artist: String,
        val etc: String
) : Item(id, name, price, stockQuantity)
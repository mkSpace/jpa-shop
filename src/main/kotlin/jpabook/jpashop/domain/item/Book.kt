package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
data class Book(
        override val id: Long,
        override val name: String,
        override val price: Int,
        override val stockQuantity: Int,
        override val categories: List<Category>,
        val author: String,
        val isbn: String
) : Item(id, name, price, stockQuantity, categories)
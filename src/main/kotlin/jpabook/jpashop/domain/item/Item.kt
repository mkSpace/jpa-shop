package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item(
        @Id @GeneratedValue @Column(name = "item_id") open val id: Long,
        open val name: String,
        open val price: Int,
        open val stockQuantity: Int,
        @ManyToMany(mappedBy = "items") open val categories: List<Category> = emptyList()
)

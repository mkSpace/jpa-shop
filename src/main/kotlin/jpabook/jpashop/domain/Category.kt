package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*

@Entity
data class Category(
        @Id @GeneratedValue @Column(name = "category_id") val id: Long,
        val name: String,
        @ManyToMany @JoinTable(
                name = "category_item",
                joinColumns = [JoinColumn(name = "category_id")],
                inverseJoinColumns = [JoinColumn(name = "item_id")]
        ) val items: List<Item> = emptyList(),
        @ManyToOne @JoinColumn(name = "parent_id") val parent: Category? = null,
        @OneToMany(mappedBy = "parent") val children: List<Category> = emptyList()
)
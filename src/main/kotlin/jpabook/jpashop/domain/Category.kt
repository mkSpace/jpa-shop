package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
class Category(
        @Id @GeneratedValue @Column(name = "category_id") val id: Long,
        val name: String,
        @ManyToMany @JoinTable(
                name = "category_item",
                joinColumns = [JoinColumn(name = "category_id")],
                inverseJoinColumns = [JoinColumn(name = "item_id")]
        ) val items: List<Item> = emptyList(),
        @ManyToOne(fetch = LAZY) @JoinColumn(name = "parent_id") var parent: Category? = null,
        @OneToMany(mappedBy = "parent") var children: List<Category> = emptyList()
) {
    fun addChildCategory(child: Category) {
        this.children += child
        child.parent = this
    }
}
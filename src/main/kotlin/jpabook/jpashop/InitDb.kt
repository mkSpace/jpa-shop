package jpabook.jpashop

import jpabook.jpashop.domain.*
import jpabook.jpashop.domain.item.Book
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitDb(private val initService: InitService) {

    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }
}

@Component
@Transactional
class InitService(private val entityManager: EntityManager) {

    fun dbInit1() {
        val member = createMember("userA", "서울", "1", "11111")

        val book1 = createBook("JPA1 Book", 10000, 200)
        val book2 = createBook(name = "JPA2 Book", price = 20000, stockQuantity = 300)

        val orderItem1 = OrderItem.createOrderItem(book1, 20000, 3)
        val orderItem2 = OrderItem.createOrderItem(book2, 40000, 4)

        createDelivery(member, orderItem1, orderItem2)
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
        val book = Book(name = name, price = price, stockQuantity = stockQuantity)
        entityManager.persist(book)
        return book
    }

    private fun createMember(name: String, city: String, street: String, zipcode: String): Member {
        val member = Member(
                username = name,
                address = Address(city, street, zipcode)
        )
        entityManager.persist(member)
        return member
    }

    fun dbInit2() {
        val member = createMember("userB", "전주", "2", "2222")
        val book1 = createBook("Spring1 Book", 10000, 200)
        val book2 = createBook("Spring2 Book", 20000, 300)

        val orderItem1 = OrderItem.createOrderItem(book1, 10000, 1)
        val orderItem2 = OrderItem.createOrderItem(book2, 20000, 1)

        createDelivery(member, orderItem1, orderItem2)
    }

    private fun createDelivery(member: Member, orderItem1: OrderItem, orderItem2: OrderItem) {
        val delivery = member.address?.let { Delivery(address = it) }
        delivery?.let {
            entityManager.persist(Order.createOrder(member, delivery, orderItem1, orderItem2))
        }
    }
}
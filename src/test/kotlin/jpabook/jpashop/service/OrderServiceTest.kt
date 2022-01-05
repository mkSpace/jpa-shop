package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
internal class OrderServiceTest {

    @Autowired private lateinit var entityManager: EntityManager
    @Autowired private lateinit var orderService: OrderService
    @Autowired private lateinit var orderRepository: OrderRepository

    @Test
    fun 상품주문() {
        val member = createMember()
        val memberId = member.id ?: fail { "Member Id가 null입니다." }
        val book = createBook("책1", 10000, 10)
        val bookId = book.id ?: fail { "Book id 가 null 입니다."}
        val orderCount = 2

        // when
        val orderId = orderService.order(memberId, bookId, orderCount)

        val order = orderRepository.findOne(orderId)

        assertEquals(OrderStatus.ORDERED, order.status, "상품 주문시 상태는 ORDER")
        assertEquals(1, order.orderItems.size, "주문한 상품 종류 수가 정확해야 한다.")
        assertEquals(10000 * orderCount, order.totalPrice, "주문 가격은 가격 * 수량이다.")
        assertEquals(8, book.stockQuantity, "주문 수량만큼 재고가 줄어야 한다.")
    }

    @Test
    fun 상품주문_재고수량초과() {
        val member = createMember()
        val item = createBook("책1", 10000, 10)

        val orderCount = 11
        val memberId = member.id ?: fail { "Member id가 null입니다." }
        val itemId = item.id ?: fail { "Item Id가 null 입니다." }
        assertThrows<NotEnoughStockException> {
            orderService.order(memberId = memberId, itemId = itemId, orderCount)
        }
    }

    @Test
    fun 주문취소() {
        val member = createMember()
        val item = createBook("책1", 10000, 10)
        val orderCount = 2
        val memberId = member.id ?: fail { "Member id is null" }
        val itemId = item.id ?: fail { "Item id is null" }
        val orderId = orderService.order(memberId, itemId, orderCount)

        //when
        orderService.cancelOrder(orderId)

        //then
        val order = orderRepository.findOne(orderId)
        assertEquals(OrderStatus.CANCELLED, order.status, "주문 취소시 상태는 CANCELLED이다.")
        assertEquals(10, item.stockQuantity, "주문 취소시 재고는 다시 증가해야한다.")
    }

    private fun createBook(bookName: String, bookPrice: Int, bookStockQuantity: Int): Item {
        val book = Book(
                name = bookName,
                price = bookPrice,
                stockQuantity = bookStockQuantity,
                author = "작가1",
                isbn = "1234"
        )
        entityManager.persist(book)
        return book
    }

    private fun createMember(): Member {
        val member = Member(
                username = "회원1",
                address = Address("서울", "강가", "123-123")
        )
        entityManager.persist(member)
        return member
    }
}
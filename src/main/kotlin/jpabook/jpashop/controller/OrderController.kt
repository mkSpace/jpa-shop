package jpabook.jpashop.controller

import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.service.ItemService
import jpabook.jpashop.service.MemberService
import jpabook.jpashop.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class OrderController(
        private val orderService: OrderService,
        private val memberService: MemberService,
        private val itemService: ItemService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/order")
    fun createForm(model: Model): String {
        val members = memberService.findMembers()
        val items = itemService.findItems()

        model.addAttribute("members", members)
        model.addAttribute("items", items)

        return "order/orderForm"
    }

    @PostMapping("/order")
    fun order(
            @RequestParam("memberId") memberId: Long,
            @RequestParam("itemId") itemId: Long,
            @RequestParam("count") count: Int
    ): String {
        orderService.order(memberId, itemId, count)
        return "redirect:/orders"
    }

    @GetMapping("/orders")
    fun orderList(@ModelAttribute("orderSearch") orderSearch: OrderSearch, model: Model): String {
        val orders = orderService.findOrders(orderSearch)
        logger.info("orders: $orders")
        model.addAttribute("orders", orders)
        return "order/ordersList"
    }

    @PostMapping("/orders/{orderId}/cancel")
    fun cancel(@PathVariable("orderId") orderId: Long): String {
        orderService.cancelOrder(orderId)
        return "redirect:/orders"
    }
}
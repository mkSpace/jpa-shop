package jpabook.jpashop.controller

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.service.ItemService
import org.apache.juli.logging.LogFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController(private val itemService: ItemService) {

    private val logger = LogFactory.getLog(javaClass)

    @GetMapping("/items/new")
    fun createForm(model: Model): String {
        model.addAttribute("form", BookForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(form: BookForm): String {
        val book = Book(
                name = form.name,
                price = form.price,
                stockQuantity = form.stockQuantity,
                author = form.author,
                isbn = form.isbn
        )
        itemService.saveItem(book)
        return "redirect:/items"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        model.addAttribute("items", itemService.findItems())
        return "/items/itemsList"
    }

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String {
        val item: Book = itemService.findOne(itemId) as? Book ?: throw IllegalArgumentException("Book casting error")
        val form = BookForm(
                id = item.id,
                name = item.name,
                price = item.price,
                stockQuantity = item.stockQuantity,
                author = item.author,
                isbn = item.isbn
        )
        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(@PathVariable("itemId") itemId: Long, @ModelAttribute("form") form: BookForm): String {
        itemService.updateItem(itemId, form.name, form.price, form.stockQuantity)
        return "redirect:/items"
    }
}
package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItem(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    /**
     * 상품 목록
     */
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/items/{id}/edit")
    public String updateItem(@PathVariable Long id, Model model) {
        Book item = (Book) itemService.findOne(id);
        BookForm form = BookForm.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .price(item.getPrice())
                                .stockQuantity(item.getStockQuantity())
                                .author(item.getAuthor())
                                .isbn(item.getIsbn())
                                .build();

        model.addAttribute("form", form);

        return "items/updateItem";
    }

    /**
     * 상품 수정
     * @ModelAttribute 는 선택사항(없어도 된다)
     */
    @PostMapping("/items/{id}/edit")
    public String update(@ModelAttribute("form") BookForm form) {
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}

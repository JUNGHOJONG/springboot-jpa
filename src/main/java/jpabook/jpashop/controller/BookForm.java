package jpabook.jpashop.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Setter
@Getter
public class BookForm {

    private Long id;

    @NotEmpty(message = "상품이름은 필수 입니다")
    private String name;

    private int price;

    private int stockQuantity;

    private String author;

    private String isbn;

    @Builder
    public BookForm(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}

package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("B")
@Entity
public class Book extends Item{

    private String author;

    private String isbn;
}

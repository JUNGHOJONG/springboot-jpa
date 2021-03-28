package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class OrderItem {

    @Column(name = "ORDER_ITEM_ID")
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @JoinColumn(name = "ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(name = "ORDERPRICE")
    private int orderPrice;

    @Column(name = "COUNT")
    private int count;
}

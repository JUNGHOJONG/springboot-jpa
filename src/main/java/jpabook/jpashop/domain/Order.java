package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Getter @Setter
@Entity
public class Order {

    @Column(name = "ORDER_ID")
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // remind check!!
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @JoinColumn(name = "DELIVERY_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    @Column(name = "ORDERDATE")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==연관관계 메서드==//
    // 양방향의 경우 유용하다 //
    // 연관관계의 주인 즉 더 중요한 대상에 연관관계 메서드를 할당하는 것이 좋다//
    public void setMember(Member member) {
        this.member = member;
        // jpa 들어가지는 않는다//
        member.getOrders().add(this);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}

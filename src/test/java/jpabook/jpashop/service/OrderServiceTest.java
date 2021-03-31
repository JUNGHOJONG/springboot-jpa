package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired private EntityManager entityManager;
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //GIVEN
        Member member = createMember("JUNG");

        Book book = createBook("a little prince", 10000, 10);

        int orderCount = 2;

        //WHEN
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //THEN
        Order order = orderRepository.findOne(orderId);

        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 주문가격 * 수량이다", book.getPrice() * orderCount, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //GIVEN
        Member member = createMember("JUNG");

        Item item = createBook("melong", 5000, 10);

        int orderCount = 12;

        //WHEN
        orderService.order(member.getId(), item.getId(), orderCount);

        //THEN
        fail("음수인 수량은 허용하지 않습니다");
    }

    @Test
    public void 주문취소() throws Exception {
        //GIVEN
        Member member = createMember("JUNG");
        Book book = createBook("yanolza", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //WHEN
        orderService.cancelOrder(orderId);

        //THEN
        Order order = orderRepository.findOne(orderId);

        assertEquals("취소 상태는 CANCEL 입니다", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문이 취소된 수 만큼 재고가 증가해야 한다", 10, book.getStockQuantity());
    }

    private Book createBook(String littlePrince, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(littlePrince);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        entityManager.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("seoul", "pangyoro 430", "12312"));
        entityManager.persist(member);
        return member;
    }

}
package shop.online.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.online.domain.Member;
import shop.online.domain.Order;
import shop.online.domain.OrderStatus;
import shop.online.domain.item.Book;
import shop.online.domain.item.Item;
import shop.online.exception.NotEnoughStockException;
import shop.online.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;

    @Test
    public void 상품주문() throws Exception{
        Member member = createMember();

        Book book = createBook("JPA",10000,10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(findOrder.getStatus())
                .as(()->"상품 주문시 상태는 Order")
                .isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size())
                .as(()->"주문한 상품 종류 수가 정확해야 한다.")
                .isEqualTo(1);
        Item findItem = itemService.findOne(book.getId());
        assertThat(findItem.getStockQuantity())
                .as( () -> "주문 수량만큼 재고가 줄어야 한다.")
                .isEqualTo(10-orderCount);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        itemService.saveItem(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("kim");
        memberService.join(member);
        return member;
    }

    @Test
    void 상품주문_재고수량초과() throws Exception{
        Member member = createMember();
        Book item = createBook("JPA", 10000, 10);
        int orderCount = 11;

        NotEnoughStockException exception = assertThrows(
                NotEnoughStockException.class,
                ()->orderService.order(member.getId(),item.getId(),orderCount)
        );
        String message = exception.getMessage();
        System.out.println("message = " + message);
    }
    @Test
    public void 주문취소(){
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus())
                .as(()->"주문이 취소된 상품은 상태가 canceled여야 한다")
                .isEqualTo(OrderStatus.CANCELED);
        assertThat(item.getStockQuantity())
                .as(()->"주문이 취소된 상품은 다시 재고가 증가해야 한다.")
                .isEqualTo(10);
    }
}
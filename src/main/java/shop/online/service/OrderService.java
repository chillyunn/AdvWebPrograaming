package shop.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.online.domain.Delivery;
import shop.online.domain.Member;
import shop.online.domain.Order;
import shop.online.domain.OrderItem;
import shop.online.domain.item.Item;
import shop.online.dto.OrderSearch;
import shop.online.repository.MemberRepository;
import shop.online.repository.OrderRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public Long order(Long memberId, Long itemId, int quantity){
        Member member = memberRepository.findOne(memberId);
        Item item = itemService.findOne(itemId);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),quantity);
        Order order = Order.createOrder(member,delivery,orderItem);
        orderRepository.save(order);
        return order.getId();
    }
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}

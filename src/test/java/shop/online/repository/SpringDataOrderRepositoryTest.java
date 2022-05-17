package shop.online.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.online.domain.Member;
import shop.online.domain.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SpringDataOrderRepositoryTest {
    @Autowired private SpringDataOrderRepository springDataOrderRepository;
    @Autowired private SpringDataMemberRepository springDataMemberRepository;
    @Test
    void 기본조회(){
        List<Order> allOrders = springDataOrderRepository.findAll();
        allOrders.stream().forEach(o-> System.out.println("o.getMember().getName() = " + o.getMember().getName()));
    }
    @Test
    void JPQL조회(){
        List<Order> allOrders = springDataOrderRepository.findWithMemberJPQL();
        allOrders.stream().forEach(o-> System.out.println("o.getMember().getName() = " + o.getMember().getName()));
    }
    @Test
    void 엔티티그래프조회(){
        List<Order> allOrders = springDataOrderRepository.findWithMemberGraph();
        allOrders.stream().forEach(o-> System.out.println("o.getMember().getName() = " + o.getMember().getName()));
    }
    @Test
    void 서브그래프조회(){
        List<Order> allOrders = springDataOrderRepository.findWithOrderItemItem();
        allOrders.stream().forEach(
                o-> System.out.println("o = " +
                        o.getOrderItems().get(0).getItem().getName()+
                        ", memberID"+o.getMember().getName())
        );
    }
    @Test
    void 동적그래프(){
        Order findOrder = springDataOrderRepository.findWithMemberItemDynamic(1L);
        System.out.println("member = " + findOrder.getMember().getName()+
                " ,item name:" + findOrder.getOrderItems().get(0).getItem().getName());
    }
    @Test
    void 멤버_후기_주문(){
        List<Member> memberWithComment = springDataMemberRepository.findMemberWithComment();
        List<Member> memberWithOrder = springDataMemberRepository.findMemberWithOrder();
        memberWithComment.stream().forEach(m-> System.out.println("m.getComments().get(0).getContent() = " +
                m.getComments().get(0).getContent()));
    }
}
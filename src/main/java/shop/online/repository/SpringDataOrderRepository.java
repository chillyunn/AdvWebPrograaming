package shop.online.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.online.domain.Order;

import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.*;

public interface SpringDataOrderRepository extends JpaRepository<Order,Long>, SpringDataOrderCustomRepository {

    @Query("SELECT o FROM Order o")
    List<Order> findWithMember();
    @Query("SELECT o FROM Order o join fetch o.member")
    List<Order> findWithMemberJPQL();

    @EntityGraph(value = "order.member.graph", type = EntityGraphType.LOAD)
    @Query("SELECT o FROM Order o")
    List<Order> findWithMemberGraph();

    @EntityGraph(value = "orderWithMember.orderItem.order.graph", type = EntityGraphType.LOAD)
    @Query("SELECT o FROM Order o")
    List<Order> findWithOrderItemItem();
}

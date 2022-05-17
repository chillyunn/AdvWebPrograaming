package shop.online.repository;

import shop.online.domain.Order;

import java.util.List;

public interface SpringDataOrderCustomRepository {
    Order findWithMemberItemDynamic(Long orderId);
}

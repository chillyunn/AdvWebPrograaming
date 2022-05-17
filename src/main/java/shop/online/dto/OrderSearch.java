package shop.online.dto;

import lombok.Getter;
import lombok.Setter;
import shop.online.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;//ORDER, CANCELED
}

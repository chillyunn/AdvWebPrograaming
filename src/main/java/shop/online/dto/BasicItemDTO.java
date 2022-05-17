package shop.online.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasicItemDTO {
    private String name;
    private int price;
}

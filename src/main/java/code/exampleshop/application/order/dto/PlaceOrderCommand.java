package code.exampleshop.application.order.dto;

// 왜 PlaceOrderCommand를 만들어야 하는가?
// -> Use Case의 입력 데이터를 캡슐화하기 위해서, 그리고 Use Case의 인터페이스를 명확하게 하기 위해서
// -> PlaceOrderCommand는 PlaceOrderUseCase의 입력 데이터를 캡슐화하는 객체로, 주문을 생성하는 데 필요한 정보를 담고 있다

import java.math.BigDecimal;
import java.util.List;

public record PlaceOrderCommand(
    List<Item> items
) {
    public record Item(String productId, int quantity, BigDecimal unitPrice) {
    }
}
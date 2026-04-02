package code.exampleshop.application.order;

import code.exampleshop.application.order.dto.PlaceOrderCommand;

public class PlaceOrderUseCase {

    // PlaceOrderCommand를 입력으로 받아서, 주문을 생성하는 로직을 수행한다
    public void placeOrder(PlaceOrderCommand command) {


         // 비즈니스 로직
        // 1. 주문 아이템 생성
        // 2. Order 생성
        // 3. Order 저장
        // 4. Payment 생성 (READY 상태)
        // 5. Payment 저장
    }
}

package code.exampleshop.application.order;

import code.exampleshop.application.order.dto.CancelOrderCommand;

public class CancelOrderUseCase {

    // 주문을 취소하는 로직을 수행한다
    public void cancelOrder(CancelOrderCommand command) {

        // 비즈니스 로직
        // 1. Order 조회
        // 2. Payment 조회
        // 3. 취소 가능 여부 검증
        // 4. Order.cancel()
        // 5. Payment.cancel()
    }
}

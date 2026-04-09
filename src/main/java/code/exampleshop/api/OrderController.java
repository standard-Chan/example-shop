package code.exampleshop.api;

import code.exampleshop.application.order.CancelOrderUseCase;
import code.exampleshop.application.order.PlaceOrderUseCase;
import code.exampleshop.application.order.dto.CancelOrderCommand;
import code.exampleshop.application.order.dto.PlaceOrderCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public OrderController(
        PlaceOrderUseCase placeOrderUseCase,
        CancelOrderUseCase cancelOrderUseCase
    ) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> placeOrder(@RequestBody PlaceOrderCommand command) {
        Long orderId = placeOrderUseCase.execute(command);
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        cancelOrderUseCase.execute(new CancelOrderCommand(orderId));
        return ResponseEntity.ok().build();
    }
}

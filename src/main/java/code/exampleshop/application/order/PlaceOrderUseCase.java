package code.exampleshop.application.order;

import code.exampleshop.application.order.dto.PlaceOrderCommand;
import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.order.OrderItem;
import code.exampleshop.domain.order.OrderRepository;
import code.exampleshop.domain.order.vo.Money;
import code.exampleshop.domain.payment.Payment;
import code.exampleshop.domain.payment.PaymentMethod;
import code.exampleshop.domain.payment.PaymentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public PlaceOrderUseCase(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Long execute(PlaceOrderCommand command) {
        List<OrderItem> items = command.items().stream()
            .map(item -> OrderItem.of(
                item.productId(),
                item.quantity(),
                Money.of(item.unitPrice())
            ))
            .toList();

        Order order = Order.create(items);
        Order savedOrder = orderRepository.save(order);

        Payment payment = Payment.ready(
            savedOrder.getId(),
            savedOrder.totalPrice(),
            PaymentMethod.CARD
        );
        paymentRepository.save(payment);

        return savedOrder.getId();
    }
}
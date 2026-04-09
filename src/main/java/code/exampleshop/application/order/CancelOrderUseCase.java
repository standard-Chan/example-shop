package code.exampleshop.application.order;

import code.exampleshop.application.order.dto.CancelOrderCommand;
import code.exampleshop.domain.common.BusinessException;
import code.exampleshop.domain.common.ErrorCode;
import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.order.OrderRepository;
import code.exampleshop.domain.order.policy.CancelOrderPolicy;
import code.exampleshop.domain.payment.Payment;
import code.exampleshop.domain.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final CancelOrderPolicy cancelOrderPolicy;

    public CancelOrderUseCase(
        OrderRepository orderRepository,
        PaymentRepository paymentRepository,
        CancelOrderPolicy cancelOrderPolicy
    ) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.cancelOrderPolicy = cancelOrderPolicy;
    }

    @Transactional
    public void execute(CancelOrderCommand command) {
        Order order = orderRepository.findById(command.orderId())
            .orElseThrow(() -> new BusinessException(
                ErrorCode.ORDER_NOT_FOUND,
                "주문을 찾을 수 없습니다."
            ));

        Payment payment = paymentRepository.findByOrderId(order.getId()).orElse(null);

        cancelOrderPolicy.validate(order, payment);
        order.cancel();

        if (payment != null) {
            payment.cancel();
        }
    }
}
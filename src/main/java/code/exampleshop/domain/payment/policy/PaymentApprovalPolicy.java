package code.exampleshop.domain.payment.policy;

import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public interface PaymentApprovalPolicy {
    void validate(Order order, Payment payment);
}
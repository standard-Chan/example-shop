package code.exampleshop.domain.order.policy;

import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.payment.Payment;

public interface CancelOrderPolicy {
    void validate(Order order, Payment payment);
}
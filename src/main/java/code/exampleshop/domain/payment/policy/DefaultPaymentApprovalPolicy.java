package code.exampleshop.domain.payment.policy;

import code.exampleshop.domain.common.BusinessException;
import code.exampleshop.domain.common.ErrorCode;
import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.order.OrderStatus;
import code.exampleshop.domain.payment.Payment;
import code.exampleshop.domain.payment.PaymentStatus;

public class DefaultPaymentApprovalPolicy implements PaymentApprovalPolicy {

    @Override
    public void validate(Order order, Payment payment) {
        if (!payment.isStatus(PaymentStatus.READY)) {
            throw new BusinessException(
                ErrorCode.PAYMENT_NOT_READY,
                "결제는 READY 상태에서만 승인 요청할 수 있습니다."
            );
        }

        if (payment.amount().isZeroOrNegative()) {
            throw new BusinessException(
                ErrorCode.INVALID_PAYMENT_AMOUNT,
                "결제 금액은 0보다 커야 합니다."
            );
        }

        if (!order.isStatus(OrderStatus.CREATED)) {
            throw new IllegalStateException("주문이 CREATED 상태일 때만 결제 승인 요청 가능합니다.");
        }

        if (!payment.amount().equals(order.totalPrice())) {
            throw new BusinessException(
                ErrorCode.ORDER_TOTAL_PRICE_MISMATCH,
                "주문 금액과 결제 금액이 일치하지 않습니다."
            );
        }
    }
}
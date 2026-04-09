package code.exampleshop.domain.order.policy;

import code.exampleshop.domain.common.BusinessException;
import code.exampleshop.domain.common.ErrorCode;
import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.order.OrderStatus;
import code.exampleshop.domain.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class DefaultCancelOrderPolicy implements CancelOrderPolicy {

    @Override
    public void validate(Order order, Payment payment) {
        if (order.isStatus(OrderStatus.CANCELLED)) {
            throw new BusinessException(
                ErrorCode.ORDER_ALREADY_CANCELLED,
                "이미 취소된 주문입니다."
            );
        }

        if (order.isStatus(OrderStatus.SHIPPED)) {
            throw new BusinessException(
                ErrorCode.ORDER_ALREADY_SHIPPED,
                "배송이 시작된 주문은 취소할 수 없습니다."
            );
        }

        if (payment != null && payment.isStatus(PaymentStatus.APPROVED)) {
            throw new BusinessException(
                ErrorCode.ORDER_CANNOT_CANCEL_AFTER_PAYMENT_APPROVED,
                "승인 완료된 결제가 있으면 주문을 직접 취소할 수 없습니다. 먼저 환불 절차가 필요합니다."
            );
        }
    }
}
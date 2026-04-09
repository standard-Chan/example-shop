package code.exampleshop.infrastructure.payment;

import code.exampleshop.domain.payment.Payment;
import code.exampleshop.domain.payment.PaymentGateway;
import code.exampleshop.domain.payment.PaymentResult;
import org.springframework.stereotype.Component;

@Component
public class FakePaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult requestApproval(Payment payment) {
        if (payment.amount().isZeroOrNegative()) {
            return PaymentResult.failure("결제 금액 오류");
        }

        return PaymentResult.success("TXN-" + System.currentTimeMillis());
    }
}

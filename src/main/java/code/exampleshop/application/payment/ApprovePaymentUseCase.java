package code.exampleshop.application.payment;

import code.exampleshop.application.payment.dto.ApprovePaymentCommand;
import code.exampleshop.domain.common.BusinessException;
import code.exampleshop.domain.common.ErrorCode;
import code.exampleshop.domain.order.Order;
import code.exampleshop.domain.order.OrderRepository;
import code.exampleshop.domain.payment.Payment;
import code.exampleshop.domain.payment.PaymentGateway;
import code.exampleshop.domain.payment.PaymentRepository;
import code.exampleshop.domain.payment.PaymentResult;
import code.exampleshop.domain.payment.policy.PaymentApprovalPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentApprovalPolicy paymentApprovalPolicy;
    private final PaymentGateway paymentGateway;

    public ApprovePaymentUseCase(
        PaymentRepository paymentRepository,
        OrderRepository orderRepository,
        PaymentApprovalPolicy paymentApprovalPolicy,
        PaymentGateway paymentGateway
    ) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentApprovalPolicy = paymentApprovalPolicy;
        this.paymentGateway = paymentGateway;
    }

    @Transactional
    public void execute(ApprovePaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId())
            .orElseThrow(() -> new BusinessException(
                ErrorCode.PAYMENT_NOT_FOUND,
                "결제를 찾을 수 없습니다."
            ));

        Order order = orderRepository.findById(payment.orderId())
            .orElseThrow(() -> new BusinessException(
                ErrorCode.ORDER_NOT_FOUND,
                "결제에 연결된 주문을 찾을 수 없습니다."
            ));

        paymentApprovalPolicy.validate(order, payment);

        payment.markApprovalRequested();

        PaymentResult result = paymentGateway.requestApproval(payment);

        payment.applyApprovalResult(result);

        if (result.isSuccess()) {
            order.markPaid();
        }
    }
}
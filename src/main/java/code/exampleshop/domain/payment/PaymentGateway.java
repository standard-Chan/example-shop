package code.exampleshop.domain.payment;

public interface PaymentGateway {
    PaymentResult requestApproval(Payment payment);
}
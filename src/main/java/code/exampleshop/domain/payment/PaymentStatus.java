package code.exampleshop.domain.payment;

public enum PaymentStatus {
    READY,
    APPROVAL_REQUESTED,
    APPROVED,
    FAILED,
    CANCELLED
}
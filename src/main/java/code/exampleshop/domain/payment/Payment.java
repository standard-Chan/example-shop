package code.exampleshop.domain.payment;

import code.exampleshop.domain.common.BaseEntity;
import code.exampleshop.domain.order.vo.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(nullable = false)
    private Long orderId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "payment_amount", nullable = false, precision = 19, scale = 2))
    })
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column
    private String transactionKey;

    @Column
    private String failureReason;

    protected Payment() {
    }

    private Payment(Long orderId, Money amount, PaymentMethod method) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.READY;
    }

    public static Payment ready(Long orderId, Money amount, PaymentMethod method) {
        return new Payment(orderId, amount, method);
    }

    public void markApprovalRequested() {
        if (status != PaymentStatus.READY) {
            throw new IllegalStateException("READY 상태에서만 승인 요청 가능");
        }
        this.status = PaymentStatus.APPROVAL_REQUESTED;
    }

    public void applyApprovalResult(PaymentResult result) {
        if (result.isSuccess()) {
            this.status = PaymentStatus.APPROVED;
            this.transactionKey = result.transactionKey();
            this.failureReason = null;
            return;
        }

        this.status = PaymentStatus.FAILED;
        this.failureReason = result.failureReason();
    }

    public void cancel() {
        if (status == PaymentStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 결제입니다.");
        }
        this.status = PaymentStatus.CANCELLED;
    }

    public boolean isStatus(PaymentStatus status) {
        return this.status == status;
    }

    public Long orderId() {
        return orderId;
    }

    public Money amount() {
        return amount;
    }

    public PaymentMethod method() {
        return method;
    }

    public PaymentStatus status() {
        return status;
    }
}
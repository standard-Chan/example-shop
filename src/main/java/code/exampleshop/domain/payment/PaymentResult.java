package code.exampleshop.domain.payment;

public class PaymentResult {

    private final boolean success;
    private final String transactionKey;
    private final String failureReason;

    private PaymentResult(boolean success, String transactionKey, String failureReason) {
        this.success = success;
        this.transactionKey = transactionKey;
        this.failureReason = failureReason;
    }

    public static PaymentResult success(String transactionKey) {
        return new PaymentResult(true, transactionKey, null);
    }

    public static PaymentResult failure(String reason) {
        return new PaymentResult(false, null, reason);
    }

    public boolean isSuccess() {
        return success;
    }

    public String transactionKey() {
        return transactionKey;
    }

    public String failureReason() {
        return failureReason;
    }
}
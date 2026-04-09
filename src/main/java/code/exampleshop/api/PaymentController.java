package code.exampleshop.api;

import code.exampleshop.application.payment.ApprovePaymentUseCase;
import code.exampleshop.application.payment.dto.ApprovePaymentCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final ApprovePaymentUseCase approvePaymentUseCase;

    public PaymentController(ApprovePaymentUseCase approvePaymentUseCase) {
        this.approvePaymentUseCase = approvePaymentUseCase;
    }

    @PostMapping("/{paymentId}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long paymentId) {
        approvePaymentUseCase.execute(new ApprovePaymentCommand(paymentId));
        return ResponseEntity.ok().build();
    }
}
package code.exampleshop.application.payment;

import code.exampleshop.application.payment.dto.ApprovePaymentCommand;

public class ApprovePaymentUseCase {

    // 결제를 승인하는 로직을 수행한다.
    public void approvePayment(ApprovePaymentCommand command) {

        // 1. Payment 조회
        // 2. Order 조회
        // 3. 승인 가능 여부 검증 (Policy)
        // 4. Payment → 승인 요청 상태 변경
        // 5. 외부 결제 API 호출 (Gateway)
        // 6. 결과 반영
        // 7. 성공 시 Order → PAID
    }
}

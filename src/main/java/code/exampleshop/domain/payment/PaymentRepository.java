package code.exampleshop.domain.payment;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    Optional<Payment> findByOrderId(Long orderId);
}
package code.exampleshop.infrastructure.payment;

import code.exampleshop.domain.payment.Payment;
import code.exampleshop.domain.payment.PaymentRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

interface SpringDataPaymentJpaRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
}

@Repository
public class JpaPaymentRepository implements PaymentRepository {

    private final SpringDataPaymentJpaRepository jpaRepository;

    public JpaPaymentRepository(SpringDataPaymentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return jpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        return jpaRepository.findByOrderId(orderId);
    }
}